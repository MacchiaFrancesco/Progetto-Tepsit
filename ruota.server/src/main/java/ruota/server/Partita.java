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

    public Partita(ArrayList<Giocatore> listaGiocatori, int nTurni) {
        this.listaGiocatori = listaGiocatori;
        this.ruota = new Ruota();
        this.partitaFinita = false;
        this.nTurni = nTurni;
    }

    @Override
    public void run() {
        System.out.println("THREAD PARTITA PARTITO");

        int numeroTurniCorrente = 0;
        int turnoCorrenteGiocatore = 0;
        int nGiocatori = listaGiocatori.size();

        // Inizio partita: invio messaggio a tutti
        broadcast(new InizioPartitaServer(nTurni).toString());

        while (!partitaFinita && numeroTurniCorrente < nTurni) {

            Frase frase = new Frase();

            // Annuncio turno
            Giocatore g = listaGiocatori.get(turnoCorrenteGiocatore);
            broadcast(new AnnuncioTurno(turnoCorrenteGiocatore).toString());

            
            
            // Inizio turno
            sendToPlayer(g, new InizioTurno(frase.getFraseAttuale(), frase.getTema()).toString());

            boolean turnoFinito = false;
            
            
            while (!frase.completata() && !turnoFinito) {
            	
            	// Stato giocatori
                
                int[] idG = new int[nGiocatori];
                int[] sP = new int[nGiocatori];
                int[] sT = new int[nGiocatori];

                for (int i = 0; i < nGiocatori; i++) {
                    Giocatore g4 = listaGiocatori.get(i);
                    idG[i] = i;
                    sP[i] = g4.getPunteggioPartita();
                    sT[i] = g4.getPunteggioTurno();
                }
                
                ClientMessage msg = null;
                try {
//                    System.out.println("SERVER aspetta input dal giocatore " + turnoCorrenteGiocatore);
                    msg = ServerParser.parse(g.getCodaRicezione().preleva());

                    if (msg == null) {
                        System.out.println("SERVER: messaggio non riconosciuto");
                        continue;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                switch (msg.getId()) {
                    case 20: // Girare la ruota
                    	
                    	
                        risRuota = ruota.giraRuota();
                        if (risRuota == -1) { // Passa turno
                            turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % nGiocatori;
                            pulisciCoda(listaGiocatori.get(turnoCorrenteGiocatore));
                        
                        } else if (risRuota == -2) { // Bancarotta
                            g.resetPunteggioPartita();
                            g.resetPunteggio();
                            
                            turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % nGiocatori;
                            pulisciCoda(listaGiocatori.get(turnoCorrenteGiocatore));
                        }
                        broadcast(new RisultatoRuota(risRuota).toString());
                        break;

                    case 30: // Lettera indovinata
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

                                if (presente) {
                                    g.aggiungiPunteggioTurno(occorrenze * risRuota);
                                }

                                broadcast(new EsitoLettera(l, presente, occorrenze, frase.getFraseAttuale(),
                                        occorrenze * risRuota).toString());

                                if (!presente) {
                                    turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % nGiocatori;
                                    pulisciCoda(listaGiocatori.get(turnoCorrenteGiocatore));
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
                            broadcast(new SoluzioneCorretta(false, g.getPunteggioTurno()).toString());
                            turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % nGiocatori;
                            pulisciCoda(listaGiocatori.get(turnoCorrenteGiocatore));
                        }
                        break;

                    case 50: // Passo turno
                        turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % nGiocatori;
                        pulisciCoda(listaGiocatori.get(turnoCorrenteGiocatore));
                        break;

                    default:
                        System.out.println("SERVER: messaggio sconosciuto id=" + msg.getId());
                        break;
                }
            } 
        } 
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
    
    public void setListaGiocatori(ArrayList<Giocatore> listaGiocatori){
    	this.listaGiocatori=listaGiocatori;
    }
}