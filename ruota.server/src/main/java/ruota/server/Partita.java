package ruota.server;

import ruota.server.Messaggi.*;
import ruota.client.Messaggi.*;

import java.util.ArrayList;

public class Partita implements Runnable {

    private Ruota ruota;
    private ArrayList<Giocatore> listaGiocatori;
    private boolean partitaFinita;
    private int nTurni;
    private String[] arrVocali = {"A", "E", "I", "O", "U"};
    private int risRuota;

    int numeroTurniCorrente = 0;
    int turnoCorrenteGiocatore = 0;
    int nGiocatori;
    Frase frase;
    Giocatore g;

    public Partita(ArrayList<Giocatore> listaGiocatori, int nTurni) {
        this.listaGiocatori = listaGiocatori;
        this.ruota = new Ruota();
        this.partitaFinita = false;
        this.nTurni = nTurni;
    }

    @Override
    public void run() {
        System.out.println("THREAD PARTITA PARTITO");

        this.nGiocatori = listaGiocatori.size();

        // Inizio partita: invio messaggio a tutti
        broadcast(new InizioPartitaServer(nTurni).toString());

        while (!partitaFinita && numeroTurniCorrente < nTurni) {

            this.frase = new Frase();

            // Annuncio turno
            this.g = listaGiocatori.get(turnoCorrenteGiocatore);
            broadcast(new AnnuncioTurno(turnoCorrenteGiocatore).toString());

            // Stato giocatori
            statoGiocatori();

            // Inizio turno: solo al giocatore corrente
            sendToPlayer(g, new InizioTurno(frase.getFraseAttuale(), frase.getTema()).toString());

            boolean turnoFinito = false;

            while (!frase.completata() && !turnoFinito) {

                ClientMessage msg = null;
                try {
//                	System.out.println("SERVER: aspetto messaggio da giocatore " + turnoCorrenteGiocatore);
                	msg = ServerParser.parse(g.getCodaRicezione().preleva());
//                	System.out.println("SERVER: ricevuto msg id=" + (msg != null ? msg.getId() : "null"));
                    if (msg == null) {
                        System.out.println("SERVER: messaggio non riconosciuto");
                        continue;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                switch (msg.getId()) {
	                case 20:
//	                	System.out.println("Partita: case 20 entrato");
	                    risRuota = ruota.giraRuota();
//	                    System.out.println("SERVER: ruota girata, risultato=" + risRuota + " giocatore corrente=" + turnoCorrenteGiocatore);
	                    if (risRuota == -1) {
	                        cambioTurnoGiocatore();
	                    } else if (risRuota == -2) {
	                        cambioTurnoGiocatore();
	                    }
	                    broadcast(new RisultatoRuota(risRuota).toString());
//	                    System.out.println("SERVER: RisultatoRuota inviato, ora aspetto input da giocatore " + turnoCorrenteGiocatore);
	                    break;
	
	                case 30:
	                    System.out.println("SERVER: ricevuto case 30 lettera");
	                    
                        if (msg instanceof LetteraIndovinata) {
                            LetteraIndovinata lI = (LetteraIndovinata) msg;
                            String l = lI.getLettera().toUpperCase();

                            boolean vocale = false;
                            for (String v : arrVocali) {
                                if (v.equals(l)) {
                                    vocale = true;
                                    break;
                                }
                            }

                            if (vocale && g.getPunteggioTurno() < 500) {
                                sendToPlayer(g, new ConfermaAcquistoVocale(0).toString());
                            } else {
                                int occorrenze = frase.controllaLettera(l.charAt(0));
                                boolean presente = occorrenze > 0;

                                int soldiGuadagnati = occorrenze * risRuota;

                                if (presente) {
                                    if (vocale) {
                                        soldiGuadagnati = 0;
                                    }
                                    g.aggiungiPunteggioTurno(soldiGuadagnati);
                                }

                                broadcast(new EsitoLettera(l, presente, occorrenze, frase.getFraseAttuale(), soldiGuadagnati).toString());
                                statoGiocatori();

                                if (!presente) {
                                    cambioTurnoGiocatore();
                                    statoGiocatori();
                                }
                            }
                        }

                        AnnuncioFrase aF = new AnnuncioFrase(frase.getFraseAttuale());
                        broadcast(aF.toString());
                        break;

                    case 40: // Soluzione frase
                        SoluzioneFrase sF = (SoluzioneFrase) msg;
                        if (frase.controllaSoluzione(sF.getFrase())) {
                            g.aggiungiPunteggioPartita(g.getPunteggioTurno());
                            for (Giocatore gi : listaGiocatori) {
                                gi.resetPunteggio();
                            }

                            broadcast(new SoluzioneCorretta(true, g.getPunteggioTurno()).toString());
                            broadcast(new AnnuncioFrase(frase.getFraseOriginale()).toString());

                            numeroTurniCorrente++;
                            turnoFinito = true;
                        } else {
                            cambioTurnoGiocatore();
                        }
                        break;

                    case 50: // Passo turno
                        cambioTurnoGiocatore();
                        break;

                    default:
                        System.out.println("SERVER: messaggio sconosciuto id=" + msg.getId());
                        break;
                }//fine switch
            }//fine turno
        }//fine partita

        int idGiocatoreVincente = giocatoreConPiuSoldi();
        Giocatore g6 = listaGiocatori.get(idGiocatoreVincente);
        broadcast(new VincitoreFinale(idGiocatoreVincente, g6.getUsername(), g6.getPunteggioPartita()).toString());
    }

    private void cambioTurnoGiocatore() {
        turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % nGiocatori;
        
        System.out.println("SERVER: cambio turno -> giocatore " + turnoCorrenteGiocatore);
        
//        pulisciCoda(listaGiocatori.get(turnoCorrenteGiocatore));
        
        broadcast(new CambioTurno(turnoCorrenteGiocatore).toString());
        
        statoGiocatori();
        
        g = listaGiocatori.get(turnoCorrenteGiocatore);
        sendToPlayer(g, new InizioTurno(frase.getFraseAttuale(), frase.getTema()).toString());
    }

    private void statoGiocatori() {
        int[] idG = new int[nGiocatori];
        int[] sP = new int[nGiocatori];
        int[] sT = new int[nGiocatori];

        for (int i = 0; i < nGiocatori; i++) {
            Giocatore g4 = listaGiocatori.get(i);
            idG[i] = i;
            sP[i] = g4.getPunteggioPartita();
            sT[i] = g4.getPunteggioTurno();
        }

        broadcast(new StatoGiocatore(nGiocatori, idG, sP, sT).toString());
    }

    private int giocatoreConPiuSoldi() {
        int idGiocatore = -1;
        int temp = -1;
        for (int i = 0; i < nGiocatori; i++) {
            Giocatore g5 = listaGiocatori.get(i);
            if (g5.getPunteggioPartita() > temp) {
                temp = g5.getPunteggioPartita();
                idGiocatore = i;
            }
        }
        return idGiocatore;
    }

    private void sendToPlayer(Giocatore g, String messaggio) {
        try {
            g.getCodaTrasmissione().inserisci(messaggio);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void pulisciCoda(Giocatore g) {
        g.getCodaRicezione().clear();
    }

    private void broadcast(String messaggio) {
        for (Giocatore g : listaGiocatori) {
            sendToPlayer(g, messaggio);
        }
    }

    public void setListaGiocatori(ArrayList<Giocatore> listaGiocatori) {
        this.listaGiocatori = listaGiocatori;
    }
}