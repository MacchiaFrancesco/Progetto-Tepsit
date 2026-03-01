package ruota.server;
import ruota.server.Messaggi.*;
import ruota.client.Messaggi.*;

import java.util.ArrayList;

public class Partita implements Runnable {

    private Ruota ruota;
    private Frase frase;
    private ArrayList<Giocatore> listaGiocatori;
    private boolean partitaFinita;
    private CodaCircolare cRic;
    private CodaCircolare cTra;

    public Partita(ArrayList<Giocatore> listaGiocatori, CodaCircolare cRic, CodaCircolare cTra) {
        this.listaGiocatori = listaGiocatori;
        this.ruota = new Ruota();
        this.frase = new Frase(); // legge le frasi da file e maschera automaticamente
        this.partitaFinita = false;
        this.cRic=cRic;
        this.cTra=cTra;
    }

    @Override
    public void run() {

        // Mostra la frase mascherata all’inizio della partita
    	InizioPartitaClient iPC = new InizioPartitaClient();
    	InizioPartitaServer iPS= new InizioPartitaServer (iPC.getNTurni());
        broadcast(iPS.tostring());
    	
        AnnuncioFrase aF=new AnnuncioFrase(frase.getFraseAttuale());
        broadcast(aF.tostring());

        int indiceTurno = 0;

        while (!partitaFinita) {

            Giocatore giocatoreCorrente = listaGiocatori.get(indiceTurno);
            AnnuncioTurno aT=new AnnuncioTurno(indiceTurno);
            sendToPlayer(giocatoreCorrente, aT.tostring());

            int valoreRuota = ruota.giraRuota();
            RisultatoRuota rR=new RisultatoRuota(valoreRuota);
            broadcast(rR.tostring());

            // BANCAROTTA
            if (valoreRuota == -1) {
                Bancarotta b=new Bancarotta (indiceTurno, giocatoreCorrente.getPunteggio());
                giocatoreCorrente.resetPunteggio();
                broadcast(b.tostring());
                indiceTurno = (indiceTurno + 1) % listaGiocatori.size();
                continue;
            }

            // PERDI TURNO, RAGAZZACCIO!
            if (valoreRuota == -2 || valoreRuota == 0) {
            	AvvisoTimeOut aTO=new AvvisoTimeOut();
                sendToPlayer(giocatoreCorrente, aTO.tostring());
                indiceTurno = (indiceTurno + 1) % listaGiocatori.size();
                continue;
            }

            // Chiede input al giocatore
            sendToPlayer(giocatoreCorrente, "Scrivi il tuo comando (una LETTERA o la FRASE):");

            String input = null;
            try {
                input = giocatoreCorrente.getCodaRicezione().preleva(); // blocca fino a ricevere input
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (input == null || input.trim().isEmpty()) {
                indiceTurno = (indiceTurno + 1) % listaGiocatori.size();
                continue;
            }

            input = input.trim().toUpperCase();

            // Se il giocatore prova una lettera
            if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                char lettera = input.charAt(0);
                int occorrenze = frase.controllaLettera(lettera);

                if (occorrenze > 0) {
                    int punti = valoreRuota * occorrenze;
                    giocatoreCorrente.aggiungiPunteggio(punti);

                    broadcast("Lettera trovata! Occorrenze: " + occorrenze);
                    broadcast("Frase attuale: " + frase.getFraseAttuale());

                    if (frase.completata()) {
                        broadcast("Frase completata da " + giocatoreCorrente.getUsername() + "!");
                        broadcast("Frase completa: " + frase.getFraseOriginale());
                        partitaFinita = true;
                    }

                } else {
                    sendToPlayer(giocatoreCorrente, "Lettera non presente.");
                    indiceTurno = (indiceTurno + 1) % listaGiocatori.size();
                }

            // Se il giocatore prova a indovinare la frase completa
            } else if (input.length() > 1) {
                if (frase.controllaSoluzione(input)) {
                    broadcast("Frase indovinata da " + giocatoreCorrente.getUsername() + "!");
                    broadcast("Frase completa: " + frase.getFraseOriginale());
                    partitaFinita = true;
                } else {
                    sendToPlayer(giocatoreCorrente, "Soluzione errata!");
                    indiceTurno = (indiceTurno + 1) % listaGiocatori.size();
                }

            } else {
                sendToPlayer(giocatoreCorrente, "Comando non valido!");
                indiceTurno = (indiceTurno + 1) % listaGiocatori.size();
            }
        }

        // Mostra classifica finale
        broadcast("=== PARTITA TERMINATA ===");
        mostraClassifica();
    }

    // Invia un messaggio a un singolo giocatore
    private void sendToPlayer(Giocatore g, String messaggio) {
        try {
            cTra.inserisci(messaggio);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Invia un messaggio a tutti i giocatori
    private void broadcast(String messaggio) {
        for (Giocatore g : listaGiocatori) {
            sendToPlayer(g, messaggio);
        }
    }

    // Mostra la classifica finale
    private void mostraClassifica() {
        broadcast("=== CLASSIFICA FINALE ===");
        for (Giocatore g : listaGiocatori) {
            broadcast(g.getUsername() + " - " + g.getPunteggio());
        }
    }
}