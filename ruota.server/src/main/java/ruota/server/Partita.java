package ruota.server;
import ruota.server.Messaggi.*;
import ruota.client.Messaggi.*;

import java.util.ArrayList;

public class Partita implements Runnable {

    private Ruota ruota;
    private Frase frase;
    private ArrayList<Giocatore> listaGiocatori;
    private boolean partitaFinita;
//    private CodaCircolare cRic;
//    private CodaCircolare cTra;
    private int nTurni;
    private String[] arrVocali = {"A", "E", "I", "O", "U"};
    
    private int risRuota = 0;
    
    
  
   

    public Partita(ArrayList<Giocatore> listaGiocatori,  int nTurni) { //CodaCircolare cRic, CodaCircolare cTra,
        this.listaGiocatori = listaGiocatori;
        this.ruota = new Ruota();
        this.partitaFinita = false;
//        this.cRic=cRic;
//        this.cTra=cTra;
        this.nTurni = nTurni;
        
    }


    
    public void run(){
    	int numeroTurniCorrente = 0;
        int turnoCorrenteGiocatore = 0;
        int nGiocatori = listaGiocatori.size();
        
    	InizioPartitaServer iPS= new InizioPartitaServer (nTurni);
        broadcast(iPS.toString());
        
        
        AnnuncioFrase aF=new AnnuncioFrase(frase.getFraseAttuale());
        broadcast(aF.toString());

        while (!partitaFinita && numeroTurniCorrente < nTurni) {
        	Frase frase = new Frase();
        	InizioTurno iT = new InizioTurno(frase.getFraseAttuale(), frase.getTema());
        	broadcast(iT.toString());
        	
        	int idG[] = new int[listaGiocatori.size()];
        	for (int i = 0; i < listaGiocatori.size(); i++) {
        		idG[i] = i;
        	}
        	int sP[] = new int[listaGiocatori.size()];
        	int sT[] = new int[listaGiocatori.size()];
        	int i = 0;
        	for (Giocatore g : listaGiocatori) {
                sP[i] = g.getPunteggioPartita();
                sT[i] = g.getPunteggioTurno();
                i++;
            }
        	i=0;
        	StatoGiocatore sG = new StatoGiocatore(nGiocatori, idG, sP, sT);
        	broadcast(sG.toString());
        	
        	AnnuncioTurno aT = new AnnuncioTurno(turnoCorrenteGiocatore);
    		broadcast(aT.toString());
    		Giocatore g = listaGiocatori.get(turnoCorrenteGiocatore); 
        	while(!frase.completata()) {
        		ClientMessage msg = null;
				try {
					msg = ServerParser.parse(g.getCodaRicezione().preleva());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
        		switch (msg.getId()) {


	                case 20: // Girare la ruota
	                    risRuota = ruota.giraRuota();
	                    if (risRuota == -1) { //passaturno
	                    	turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % 3;
	                    }else if (risRuota == -2) { //bancarotta
	                    	//Giocatore gi = listaGiocatori.get(turnoCorrenteGiocatore); // prende l'istanze del giocatore corrente
	                    	g.resetPunteggioPartita();
	                    	g.resetPunteggio();
	                    	turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % 3;
	                    }
	                    
	                    RisultatoRuota rR = new RisultatoRuota(risRuota);
	                    broadcast(rR.toString()); 
	                  
	                    break;
	
	                case 30: // Lettera indovinata
	                	if (msg instanceof LetteraIndovinata) { //usare instanceof per evitare class cast exception 
	                		LetteraIndovinata lI = (LetteraIndovinata) msg;
	                		//Giocatore g = listaGiocatori.get(turnoCorrenteGiocatore);  prende l'istanze del giocatore corrente
	                		String l = lI.getLettera();
	                		
	                		for(int z = 0; z < arrVocali.length; z++) {
	                			
	                			if (l.equals(arrVocali[i])) {
	                				
	                				if (g.getPunteggioTurno() < 500) {
	                					ConfermaAcquistoVocale cAV =  new ConfermaAcquistoVocale(0);
	                					sendToPlayer(g, cAV.toString());
	                				}else {
	                					break;
	                				}
	                				
	                			}
	                		}
	                		int occorrenze = frase.controllaLettera(l.charAt(0)); //da string a char
	                		boolean o = occorrenze != 0; // se occorrenze e' diverso da 0 ottieni true, altrimenti false
	                		if(o) { 
	                			//lettera presente
	                			
	                			g.aggiungiPunteggioTurno(risRuota*occorrenze);
	                			
	                			EsitoLettera eL = new EsitoLettera(l, o, occorrenze, frase.getFraseAttuale(), risRuota*occorrenze);
	                			broadcast(eL.toString());
	                			
	                			
	                			
	                		}else {
	                			//lettera non presente (cambio turno giocatore)
	                			
	                			EsitoLettera eL = new EsitoLettera(l, o, occorrenze, frase.getFraseAttuale(), 0);
	                			broadcast(eL.toString());
	                			turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % 3; //passa al giocatore successivo
	                		}
	        			}
	                    break;
	
	                case 40: // Dare soluzione frase
	                	SoluzioneFrase sF = (SoluzioneFrase) msg;
	                	if(frase.controllaSoluzione(sF.getFrase())) { //frase giusta
	                		SoluzioneCorretta sC = new SoluzioneCorretta(true, g.getPunteggioTurno());
	                		broadcast(sC.toString());
	                		
	                		g.aggiungiPunteggioPartita(g.getPunteggioTurno()); //azzera i soldi del turno a tutti i giocatori
	                		for (Giocatore gi : listaGiocatori) {
	                            gi.resetPunteggio();
	                        }
	                		
	                		AnnuncioFrase aF3 = new AnnuncioFrase(frase.getFraseOriginale()); //manda frase completa a tutti
	                		broadcast(aF3.toString());
	                		
	                		
	                	}else { //frase sbagliata
	                		
	                		SoluzioneCorretta sC = new SoluzioneCorretta(false, g.getPunteggioTurno());
	                		broadcast(sC.toString());
	                		
	                		turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % 3;
	                	}
	                    break;
	
	                case 50: // Passo turno
	                	// turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % 3;
	                	
	                	//minigioco++
	                    break;
	
	                case 903: // Controllo connessione
	                    // TODO: rispondere con ControlloConnessione
	                    break;
	
	                    
	                // bisogna fare la ruota delle meraviglie
	                    
	                    
	                default:
	                    System.out.println("TARTAMELLA NON LAVORA");
	                    break;
        		}
        		
        	}
            
        }
    }

    
    
    
    /// FINE RUN
    
    
    
    // Invia un messaggio a un singolo giocatore
    private void sendToPlayer(Giocatore g, String messaggio) {
        try {
            g.getCodaTrasmissione().inserisci(messaggio);
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

    public void setListaGiocatori(ArrayList<Giocatore> listaGiocatori){
    	this.listaGiocatori=listaGiocatori;
    }
    
    // Mostra la classifica finale
    private void mostraClassifica() {
        broadcast("=== CLASSIFICA FINALE ===");
        for (Giocatore g : listaGiocatori) {
            broadcast(g.getUsername() + " - " + g.getPunteggioTurno());
        }
    }
}


//@Override
//public void run() {
//
//  // Mostra la frase mascherata all’inizio della partita
//	InizioPartitaClient iPC = new InizioPartitaClient();
//	InizioPartitaServer iPS= new InizioPartitaServer (iPC.getNTurni());
//  broadcast(iPS.tostring());
//	
//  AnnuncioFrase aF=new AnnuncioFrase(frase.getFraseAttuale());
//  broadcast(aF.toString());
//
//  int indiceTurno = 0;
//
//  while (!partitaFinita) {
//
//      Giocatore giocatoreCorrente = listaGiocatori.get(indiceTurno);
//      AnnuncioTurno aT=new AnnuncioTurno(indiceTurno);
//      sendToPlayer(giocatoreCorrente, aT.toString());
//
//      int valoreRuota = ruota.giraRuota();
//      RisultatoRuota rR=new RisultatoRuota(valoreRuota);
//      broadcast(rR.tostring());
//
//      // BANCAROTTA
//      if (valoreRuota == -1) {
//          Bancarotta b=new Bancarotta (indiceTurno, giocatoreCorrente.getPunteggioTurno());
//          giocatoreCorrente.resetPunteggio();
//          broadcast(b.toString());
//          indiceTurno = (indiceTurno + 1) % listaGiocatori.size();
//          continue;
//      }
//
//      // PERDI TURNO, RAGAZZACCIO!
//      if (valoreRuota == -2 || valoreRuota == 0) {
//      	AvvisoTimeOut aTO=new AvvisoTimeOut();
//          sendToPlayer(giocatoreCorrente, aTO.tostring());
//          indiceTurno = (indiceTurno + 1) % listaGiocatori.size();
//          continue;
//      }
//
//      // Chiede input al giocatore
//      
//      sendToPlayer(giocatoreCorrente, "Scrivi il tuo comando (una LETTERA o la FRASE):");
//
//      String input = null;
//      try {
//          input = giocatoreCorrente.getCodaRicezione().preleva(); // blocca fino a ricevere input
//      } catch (InterruptedException e) {
//          e.printStackTrace();
//      }
//
//      if (input == null || input.trim().isEmpty()) {
//          indiceTurno = (indiceTurno + 1) % listaGiocatori.size();
//          continue;
//      }
//
//      input = input.trim().toUpperCase();
//      int punti = 0;
//
//      // Se il giocatore prova una lettera
//      if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
//          char lettera = input.charAt(0);
//          int occorrenze = frase.controllaLettera(lettera);
//          
//
//          if (occorrenze > 0) {
//              punti = valoreRuota * occorrenze;
//              giocatoreCorrente.aggiungiPunteggioTurno(punti);
//              
//              EsitoLettera eL=new EsitoLettera (input, true, occorrenze, frase.getFraseAttuale(), punti);
//              broadcast(eL.toString());
//
//          } else {
//              EsitoLettera eL=new EsitoLettera (input, false, occorrenze, frase.getFraseAttuale(), 0);
//              sendToPlayer(giocatoreCorrente, eL.toString());
//              indiceTurno = (indiceTurno + 1) % listaGiocatori.size();
//          }
//
//      // Se il giocatore prova a indovinare la frase completa
//      } else if (input.length() > 1) {
//          if (frase.controllaSoluzione(input)) {
//          	giocatoreCorrente.aggiungiPunteggioPartita(punti);
//          	SoluzioneCorretta sC=new SoluzioneCorretta(true, giocatoreCorrente.getPunteggioPartita()); //<--- QUESTO QUI!!!
//          	DareSoluzioneFrase dSF=new DareSoluzioneFrase(frase.getFraseOriginale());
//              broadcast("Frase indovinata da " + giocatoreCorrente.getUsername() + "!");
//              broadcast(dSF.toString());
//              partitaFinita = true;
//          } else {
//          	SoluzioneCorretta sC=new SoluzioneCorretta(false, giocatoreCorrente.getPunteggioPartita());                	
//              sendToPlayer(giocatoreCorrente, sC.toString());
//              indiceTurno = (indiceTurno + 1) % listaGiocatori.size();
//          }
//
//      } else {
//      	SoluzioneCorretta sC=new SoluzioneCorretta(false, giocatoreCorrente.getPunteggioPartita());   
//          sendToPlayer(giocatoreCorrente, sC.toString());
//          indiceTurno = (indiceTurno + 1) % listaGiocatori.size();
//      }
//  }
//
//  // Mostra classifica finale
//  broadcast("=== PARTITA TERMINATA ===");
//  mostraClassifica();
//}