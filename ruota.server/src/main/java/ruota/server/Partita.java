//package ruota.server;
//import ruota.server.Messaggi.*;
//import ruota.client.Messaggi.*;
//
//import java.util.ArrayList;
//
//public class Partita implements Runnable {
//
//    private Ruota ruota;
//    private Frase frase;
//    private ArrayList<Giocatore> listaGiocatori;
//    private boolean partitaFinita;
////    private CodaCircolare cRic;
////    private CodaCircolare cTra;
//    private int nTurni;
//    private String[] arrVocali = {"A", "E", "I", "O", "U"};
//    
//    private int risRuota = 0;
//    
//    
//  
//   
//
//    public Partita(ArrayList<Giocatore> listaGiocatori,  int nTurni) { //CodaCircolare cRic, CodaCircolare cTra,
//        this.listaGiocatori = listaGiocatori;
//        this.ruota = new Ruota();
//        this.partitaFinita = false;
////        this.cRic=cRic;
////        this.cTra=cTra;
//        this.nTurni = nTurni;
//        
//    }
//
//
//    
//    public void run(){
//    	System.out.println("THREAD PARTITA PARTITO");
//    	int numeroTurniCorrente = 0;
//        int turnoCorrenteGiocatore = 0;
//        int nGiocatori = listaGiocatori.size();
//        
//    	InizioPartitaServer iPS= new InizioPartitaServer (nTurni);
//        broadcast(iPS.toString());
//        
//        
////        AnnuncioFrase aF=new AnnuncioFrase(frase.getFraseAttuale());
////        broadcast(aF.toString());
//
//        while (!partitaFinita && numeroTurniCorrente < nTurni) {
//        	Frase frase = new Frase();
//        	
//        	AnnuncioTurno aT = new AnnuncioTurno(turnoCorrenteGiocatore);
//    		broadcast(aT.toString());
//    		
//        	
//        	
//        	int idG[] = new int[listaGiocatori.size()];
//        	for (int i = 0; i < listaGiocatori.size(); i++) {
//        		idG[i] = i;
//        	}
//        	int sP[] = new int[listaGiocatori.size()];
//        	int sT[] = new int[listaGiocatori.size()];
//        	int i = 0;
//        	for (Giocatore g1 : listaGiocatori) {
//                sP[i] = g1.getPunteggioPartita();
//                sT[i] = g1.getPunteggioTurno();
//                i++;
//            }
//        	i=0;
//        	StatoGiocatore sG = new StatoGiocatore(nGiocatori, idG, sP, sT);
//        	broadcast(sG.toString());
//        	
//        	Giocatore g4 = listaGiocatori.get(turnoCorrenteGiocatore);
//        	InizioTurno iT = new InizioTurno(frase.getFraseAttuale(), frase.getTema());
//        	sendToPlayer(g4, iT.toString());
//        	
//        	while(!frase.completata()) {
//        		Giocatore g = listaGiocatori.get(turnoCorrenteGiocatore);
//            	
//        		ClientMessage msg = null;
//				try {
//					System.out.println("SERVER aspetta input dal giocatore " + turnoCorrenteGiocatore);
//					msg = ServerParser.parse(g.getCodaRicezione().preleva());
//					if (msg == null) {
//					    System.out.println("SERVER: messaggio non riconosciuto");
//					    continue;
//					}	
//					System.out.println("MSG ID: " + msg.getId());
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
////				System.out.println("messaggio" + msg.getId());
//					
//        		switch (msg.getId()) {
//
//
//	                case 20: // Girare la ruota
////	                	System.out.println("case entratp " );
//	                    risRuota = ruota.giraRuota();
//	                    if (risRuota == -1) { //passaturno
//	                    	turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % listaGiocatori.size();
//	                    }else if (risRuota == -2) { //bancarotta
//	                    	//Giocatore gi = listaGiocatori.get(turnoCorrenteGiocatore); // prende l'istanze del giocatore corrente
//	                    	g.resetPunteggioPartita();
//	                    	g.resetPunteggio();
//	                    	turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % listaGiocatori.size();
//	                    }
////	                    System.out.println("ruota girata" + risRuota);
//	                    RisultatoRuota rR = new RisultatoRuota(risRuota);
//	                    broadcast(rR.toString()); 
////	                    System.out.println("ruota inviata " + risRuota);
//	                  
//	                    break;
//	
//	                case 30: // Lettera indovinata
//	                	if (msg instanceof LetteraIndovinata) { //usare instanceof per evitare class cast exception 
//	                		LetteraIndovinata lI = (LetteraIndovinata) msg;
//	                		//Giocatore g = listaGiocatori.get(turnoCorrenteGiocatore);  prende l'istanze del giocatore corrente
//	                		String l = lI.getLettera();
//	                		
//	                		for(int z = 0; z < arrVocali.length; z++) {
//	                			
//	                			if (l.equals(arrVocali[z])) {
//	                				
//	                				if (g.getPunteggioTurno() < 500) {
//	                					ConfermaAcquistoVocale cAV =  new ConfermaAcquistoVocale(0);
//	                					sendToPlayer(g, cAV.toString());
//	                					int occorrenze = frase.controllaLettera(l.charAt(0)); //da string a char
//	        	                		boolean o = occorrenze != 0; // se occorrenze e' diverso da 0 ottieni true, altrimenti false
//	        	                		if(o) { 
//	        	                			//lettera presente
//	        	                			
//	        	                			g.aggiungiPunteggioTurno(risRuota*occorrenze);
//	        	                			
//	        	                			EsitoLettera eL = new EsitoLettera(l, o, occorrenze, frase.getFraseAttuale(), risRuota*occorrenze);
//	        	                			broadcast(eL.toString());
//	        	                			
//	        	                		}else {
//	        	                			//lettera non presente (cambio turno giocatore)
//	        	                			
//	        	                			EsitoLettera eL = new EsitoLettera(l, o, occorrenze, frase.getFraseAttuale(), 0);
//	        	                			broadcast(eL.toString());
//	        	                			turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % listaGiocatori.size(); //passa al giocatore successivo
//	        	                		}
//	                				}else {
//	                					int occorrenze = frase.controllaLettera(l.charAt(0)); //da string a char
//	        	                		boolean o = occorrenze != 0; // se occorrenze e' diverso da 0 ottieni true, altrimenti false
//	        	                		if(o) { 
//	        	                			//lettera presente
//	        	                			
//	        	                			g.aggiungiPunteggioTurno(risRuota*occorrenze);
//	        	                			
//	        	                			EsitoLettera eL = new EsitoLettera(l, o, occorrenze, frase.getFraseAttuale(), risRuota*occorrenze);
//	        	                			broadcast(eL.toString());
//	        	                			
//	        	                		}else {
//	        	                			//lettera non presente (cambio turno giocatore)
//	        	                			
//	        	                			EsitoLettera eL = new EsitoLettera(l, o, occorrenze, frase.getFraseAttuale(), 0);
//	        	                			broadcast(eL.toString());
//	        	                			turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % listaGiocatori.size(); //passa al giocatore successivo
//	        	                		}
//	                				}
//	                				
//	                			}
//	                		}
//	                		
//	        			}
//	                    break;
//	
//	                case 40: // Dare soluzione frase
//	                	SoluzioneFrase sF = (SoluzioneFrase) msg;
//	                	if(frase.controllaSoluzione(sF.getFrase())) { //frase giusta
//	                		SoluzioneCorretta sC = new SoluzioneCorretta(true, g.getPunteggioTurno());
//	                		broadcast(sC.toString());
//	                		
//	                		g.aggiungiPunteggioPartita(g.getPunteggioTurno()); //azzera i soldi del turno a tutti i giocatori
//	                		for (Giocatore gi : listaGiocatori) {
//	                            gi.resetPunteggio();
//	                        }
//	                		
//	                		AnnuncioFrase aF3 = new AnnuncioFrase(frase.getFraseOriginale()); //manda frase completa a tutti
//	                		broadcast(aF3.toString());
//	                		
//	                		numeroTurniCorrente++;
//	                	}else { //frase sbagliata
//	                		
//	                		SoluzioneCorretta sC = new SoluzioneCorretta(false, g.getPunteggioTurno());
//	                		broadcast(sC.toString());
//	                		
//	                		turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % listaGiocatori.size();
//	                	}
//	                    break;
//	
//	                case 50: // Passo turno
//	                	turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % listaGiocatori.size();
//	                	
//	                	//minigioco++
//	                    break;
//	
//	                case 903: // Controllo connessione
//	                    // TODO: rispondere con ControlloConnessione
//	                    break;
//	
//	                    
//	                // bisogna fare la ruota delle meraviglie
//	                    
//	                    
//	                default:
//	                    System.out.println("TARTAMELLA NON LAVORA");
//	                    break;
//        		}
//        		
//        	}
//            
//        }
//    }   
//    
//    /// FINE RUN
//     
//    
//    // Invia un messaggio a un singolo giocatore
//    private void sendToPlayer(Giocatore g, String messaggio) {
//        try {
//            g.getCodaTrasmissione().inserisci(messaggio);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Invia un messaggio a tutti i giocatori
//    private void broadcast(String messaggio) {
//        for (Giocatore g : listaGiocatori) {
//            sendToPlayer(g, messaggio);
//        }
//    }
//
//    public void setListaGiocatori(ArrayList<Giocatore> listaGiocatori){
//    	this.listaGiocatori=listaGiocatori;
//    }
//    
//    
//}
//
//

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
            
            // Inizio turno
            sendToPlayer(g, new InizioTurno(frase.getFraseAttuale(), frase.getTema()).toString());

            boolean turnoFinito = false;
            while (!frase.completata() && !turnoFinito) {
                ClientMessage msg = null;
                try {
                    System.out.println("SERVER aspetta input dal giocatore " + turnoCorrenteGiocatore);
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
                            turnoFinito = true;
                        } else if (risRuota == -2) { // Bancarotta
                            g.resetPunteggioPartita();
                            g.resetPunteggio();
                            turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % nGiocatori;
                            turnoFinito = true;
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
                                    turnoFinito = true;
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
                            turnoFinito = true;
                        }
                        break;

                    case 50: // Passo turno
                        turnoCorrenteGiocatore = (turnoCorrenteGiocatore + 1) % nGiocatori;
                        turnoFinito = true;
                        break;

                    default:
                        System.out.println("SERVER: messaggio sconosciuto id=" + msg.getId());
                        break;
                }
            } // fine while turno
        } // fine while partita
    } // fine run

  

    private void sendToPlayer(Giocatore g, String messaggio) {
        try {
            g.getCodaTrasmissione().inserisci(messaggio);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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