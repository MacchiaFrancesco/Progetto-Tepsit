package ruota.client;
import java.net.Socket;
import java.util.Scanner;

import ruota.client.Messaggi.*;
import ruota.server.Messaggi.*;


public class Client {

	private CodaCircolare codaRicezione;
	private CodaCircolare codaTrasmissione;
	private String username;
	private int lobbyCode;
	private Socket socket;
	
	private int mioId;
	private int idGiocatoreCorrente = -1;
	
	private static String indirizzoServer = "127.0.0.1";
	private static int port = 5656;
	
	public Client (Socket socket, String username, int lobbyCode) {
		this.socket = socket;
		this.username = username;
		this.lobbyCode = lobbyCode;
		creaTrasmissioneRicezione();
	}
	
	public void creaTrasmissioneRicezione() {
		CodaCircolare codaToServer = new CodaCircolare(10);
		this.codaTrasmissione = codaToServer;
		Trasmissione trasmissione = new Trasmissione(socket, codaToServer);
		
		Thread tr = new Thread(trasmissione, "Trasmissione");
		tr.start();
		
		CodaCircolare codaFromServer = new CodaCircolare(10);
		this.codaRicezione = codaFromServer;
		Ricezione ricezione = new Ricezione(socket, codaFromServer);
		Thread ri = new Thread(ricezione, "Ricezione");
		
		ri.start();
		
	}
	
	public void inviaMessaggio(int idMsg) throws InterruptedException {
		String messaggio = null;
	
		codaTrasmissione.inserisci(messaggio); 
	}
	
	public void inviaMsg(String Msg) throws InterruptedException {
		codaTrasmissione.inserisci(Msg); 
	}
	
	public String prelevaMsg() throws InterruptedException {
		String messaggio = codaRicezione.preleva();
		return messaggio;
	}
	
	public void ciao() throws InterruptedException {
		
		inviaMsg("ciao");
		System.out.println(prelevaMsg());
	}
	
	public void testgioca() throws InterruptedException { //test gioca non serve
		ClientMessage login =  new LoginGiocatore(username, lobbyCode);
		inviaMsg(login.toString());
		
		String msg = prelevaMsg();
		ServerMessage mess = ClientParser.parse(msg);
		RisultatoRuota rR = (RisultatoRuota) mess;
		System.out.println(rR.getRisultato());
	}
	
	
	
	public void startGame(int numeroTurni) throws InterruptedException {
		ClientMessage sG = new InizioPartitaClient(numeroTurni);
		inviaMsg(sG.toString());
	}
	
	public void giraRuota() throws InterruptedException {
		ClientMessage gR = new GiraRuota();
		inviaMsg(gR.toString());
	}
	
	public void indovinaLettera(String lettera) throws InterruptedException {
		ClientMessage lI = new LetteraIndovinata("" + lettera.charAt(0));
		inviaMsg(lI.toString());
	}
	
	public void selezionaBusta(int busta) throws InterruptedException {
		ClientMessage sB = new SelezioneBusta(busta);
		inviaMsg(sB.toString());
	}
	
	public void dareSoluzioneFrase(String frase) throws InterruptedException {
		ClientMessage sF = new SoluzioneFrase(frase);
		inviaMsg(sF.toString());
	}
	
	public void passa() throws InterruptedException {
		ClientMessage p = new PassoTurno();
		inviaMsg(p.toString());
	}
	
	public void gestisciPartita() throws InterruptedException {

	    Scanner scanner = new Scanner(System.in);

	    while (socket.isConnected()) {

	        String msg = prelevaMsg();
	        ServerMessage mess = ClientParser.parse(msg);

	        switch (mess.getId()) {

	            case 1: //ConfermaLogin
	            	 ConfermaLogin cl = (ConfermaLogin) mess; 
	            	 
	            	    if (cl.isEsito()) {
	            	        System.out.println("Login effettuato con successo! ID assegnato: " + cl.getIdAss());
	            	        this.mioId = cl.getIdAss();
	            	    } else {
	            	        System.out.println("Login fallito!");
	            	    }
	                break;
	                
	            case 2: //ListaGiocatori
	            	ListaGiocatori lg = (ListaGiocatori) mess; 
	                System.out.println("Lista giocatori nella lobby (" + lg.getNPlayer() + "):");
	                
	                for (int i = 0; i < lg.getNPlayer(); i++) {
	                    System.out.println("- " + lg.getNomePlayer()[i]);
	                }
	                break;

	            case 4: //InizioPartita
	            	InizioPartita ip = (InizioPartita) mess; 
	                System.out.println("La partita sta per iniziare!");
	                System.out.println("Numero di turni previsti: " + ip.getNTurni());
	                break;
	                
	            case 10: //InizioTurno
	                InizioTurno it = (InizioTurno) mess; 
	                System.out.println("È il tuo turno!");
	                System.out.println("Frase da indovinare: " + it.getFrase());
	                System.out.println("Contesto: " + it.getContesto());

	                // menu giocatore
	                System.out.println("1 - Gira Ruota");
	                System.out.println("2 - Indovina Frase");
	                System.out.println("3 - Passa");
	                
	                int scelta = scanner.nextInt();
	                scanner.nextLine();
	                switch (scelta) {
	                    case 1:
	                        giraRuota();
	                        break;
	                    case 2:
	                        System.out.print("Frase: ");
	                        String frase = scanner.nextLine();
	                        dareSoluzioneFrase(frase);
	                        break;
	                    case 3:
	                        passa();
	                        break;
	                }
	                break;

	            case 11: //StatoGiocatore
	                StatoGiocatore sg = (StatoGiocatore) mess; 

	                System.out.println("Stato dei giocatori:");
	                for (int i = 0; i < sg.getNPlayer(); i++) {
	                    System.out.println("Giocatore ID: " + sg.getIdGiocatori()[i]
	                            + " | Salvadanaio: " + sg.getSalvadanaioGiocatori()[i]
	                            + " | Soldi turno: " + sg.getSoldiTurno()[i]);
	                }
	                break;

	            case 12: //TimerMinigiochi
	                TimerMinigiochi tm = (TimerMinigiochi) mess;
	                System.out.println("Tempo rimanente per il minigioco: " + tm.getSecondi() + " secondi");
	                break;
	                
	            case 13: //TimerTurno
	                TimerTurno tt = (TimerTurno) mess;
	                System.out.println("Tempo rimanente per il turno: " + tt.getSecondi() + " secondi");
	                break;

	            case 15: //AnnuncioTurno
	                AnnuncioTurno at = (AnnuncioTurno) mess; 
	                System.out.println("È il turno del giocatore con ID: " + at.getGiocatore());
	                idGiocatoreCorrente = at.getGiocatore();
	                break;
	                
	            case 21: // Risultato ruota
	                RisultatoRuota rR = (RisultatoRuota) mess;
	                int risultato = rR.getRisultato();
	                System.out.println("La ruota è stata girata!");

	                if (risultato == -1) {
	                    System.out.println("Passo turno, sta al giocatore successivo");
	                    
	                } else if (risultato == -2) {
	                    System.out.println("Bancarotta! Hai perso tutto! Sta al giocatore successivo");
	                   
	                } else {
	                	
	                	System.out.println("il risultato e': "+ rR.getRisultato());
	                 
	                	if (mioId == idGiocatoreCorrente) { 
		                	System.out.println("Scrivi una lettera (le vocali costano 500):");
		                    String input = scanner.nextLine();
		                    while (input.isEmpty() || !Character.isLetter(input.charAt(0))) {
		                        System.out.println("Input non valido, scrivi una lettera:");
		                        input = scanner.nextLine();
		                    }
		                    // invio la lettera al server
		                    indovinaLettera(String.valueOf(input.charAt(0)));
	                	}
	                }
	                break;
	               
	            case 32: //ConfermaAcquistoVocale
	                ConfermaAcquistoVocale cav = (ConfermaAcquistoVocale) mess;
	                if (cav.getConferma()) {
	                    System.out.println("Acquisto vocale confermato!");
	                } else {
	                    System.out.println("Acquisto vocale fallito! Sei un poveraccio!");
	                }
	                break;

	            case 33: //EsitoLettera
	                EsitoLettera el = (EsitoLettera) mess;

	                if (el.isPresente()) {
	                    System.out.println("La lettera '" + el.getLettera() + "' è presente " + el.getVolte() + " volte!");
	                    System.out.println("Frase parziale: " + el.getFraseParziale());
	                    System.out.println("Soldi guadagnati dal giocatore: " + el.getSoldiGuadagnati());
	                    
	                    if (mioId == idGiocatoreCorrente) { 
		                    System.out.println("Cosa vuoi fare adesso?");
		                    System.out.println("1 - Gira Ruota");
		            	    System.out.println("2 - Indovina Frase");
		            	    System.out.println("3 - Passa");
	                    }

	            	    scelta = scanner.nextInt();
	            	    scanner.nextLine();

	            	    switch (scelta) {
	            	        case 1:
	            	            giraRuota();
	            	            break;
	            	        case 2:
	            	        	System.out.print("Frase: ");
		                        String frase = scanner.nextLine();
		                        dareSoluzioneFrase(frase);
		                        break;
	            	        case 3:
	            	            passa();
	            	            break;
	            	    }
	                } else {
	                    System.out.println("La lettera '" + el.getLettera() + "' non è presente. Il turno passa al giocatore successivo");
	                }
	                break;

	            case 41: //DareSoluzioneFrase
	                DareSoluzioneFrase dsf = (DareSoluzioneFrase) mess;
	                System.out.println("Un giocatore ha tentato di risolvere la frase: " + dsf.getSoluzione());
	                break;
	 
	            case 42: //AnnuncioFrase
	            	 AnnuncioFrase af = (AnnuncioFrase) mess;
	            	    System.out.println("Frase da indovinare: " + af.getFrase());

	                break;
	                
	            case 43: //SoluzioneCorretta
	            	  SoluzioneCorretta sc = (SoluzioneCorretta) mess;

	            	    if (sc.isEsito()) {
	            	        System.out.println("La frase è stata indovinata correttamente!");
	            	        System.out.println("Soldi guadagnati: " + sc.getSoldi());
	            	    } else {
	            	        System.out.println("Tentativo fallito. La frase e' sbagliata");
	            	    }
	                break;

	            case 51: //CambioTurno
	                CambioTurno ct = (CambioTurno) mess;
	                System.out.println("Il turno passa al giocatore con ID: " + ct.getNuovoGiocatore());
	                break;

	            case 52: //FineRound
	            	FineRound fr = (FineRound) mess;

	                System.out.println("Round " + fr.getRound() + " di " + fr.getRoundTot() + " concluso!");
	                System.out.println("Classifica parziale:");
	                for (int i = 0; i < fr.getNumeroGiocatori(); i++) {
	                    System.out.println("Giocatore " + (i + 1) + ": " + fr.getClassifica()[i] + " punti");
	                }
	                break;

	            case 100: //InizioFaseFinale
	            	InizioFaseFinale iff = (InizioFaseFinale) mess;
	                System.out.println("La fase finale è iniziata!");
	                System.out.println("ID vincitore: " + iff.getIdVincitore());
	                System.out.println("Soldi totali del vincitore: " + iff.getSoldi());
	                break;

	            case 102: //InizioMinigioco
	            	InizioMinigioco im = (InizioMinigioco) mess;
	                System.out.println("È iniziato il minigioco numero: " + im.getNGioco());
	                break;

	            case 105: //PremioFinale
	                PremioFinale pf = (PremioFinale) mess;
	                System.out.println("Premio finale estratto: " + pf.getImportoBusta());
	                System.out.println("Soldi vinti: " + pf.getSoldiVinti());
	                break;

	            case 106: //VincitoreFinale
	            	VincitoreFinale vf = (VincitoreFinale) mess;
	                System.out.println("La partita è conclusa!");
	                System.out.println("Vincitore: " + vf.getNome() + " (ID: " + vf.getIdGiocatore() + ")");
	                System.out.println("Soldi totali vinti: " + vf.getSoldi());
	                break;

	            case 901: //DisconnessioneGiocatore
	                DisconnessioneGiocatore dg = (DisconnessioneGiocatore) mess;
	                System.out.println("Il giocatore " + dg.getNome() + " (ID: " + dg.getIdGiocatore() + ") si è disconnesso.");
	                break;

	            case 902: //AvvisoTimeOut
	            	System.out.println("Tempo scaduto!");
	                break;

	            case 903: //ControlloConnessione
	                ControlloConnessione cc = (ControlloConnessione) mess;
	                System.out.println("Messaggio di controllo connessione ricevuto. Timestamp: " + cc.getTimestamp());
	                break;

	            case 904: //Bancarotta
	            	Bancarotta b = (Bancarotta) mess;
	                System.out.println("Il giocatore con ID " + b.getIdGiocatore() + " ha perso tutti i soldi del turno: " + b.getSoldiPersi());
	                return;

	            default:
	                System.out.println("Client: ID sconosciuto: " + mess.getId());
	        }
	    }
	}
	
	
	//tartamella non si e' degnato di togliere le emoji di chat gpt
	public static void main(String[] args) throws Exception {
	    Scanner scanner = new Scanner(System.in);

	    System.out.print("Inserisci il tuo username: ");
	    String username = scanner.nextLine();

	    System.out.print("Inserisci il codice della lobby, se non esiste ne verra' creata una: ");
	    int lobbyCode = scanner.nextInt();
	    scanner.nextLine(); // pulizia buffer

	    Socket socket = new Socket(indirizzoServer, port);
	    Client client = new Client(socket, username, lobbyCode);

	    // 1️⃣ INVIO LOGIN
	    ClientMessage login = new LoginGiocatore(username, lobbyCode);
	    client.inviaMsg(login.toString());
	    System.out.println("CLIENT HA INVIATO: " + login.toString());

	    // 2️⃣ ATTESA CONFERMA LOGIN
	    String risposta = client.prelevaMsg();
	    ServerMessage mess = ClientParser.parse(risposta);

	    if (!(mess instanceof ConfermaLogin)) {
	        System.out.println("Errore: risposta non valida dal server.");
	        socket.close();
	        return;
	    }

	    ConfermaLogin conferma = (ConfermaLogin) mess;

	    if (!conferma.isEsito()) {
	        System.out.println("Login rifiutato: lobby piena o errore.");
	        socket.close();
	        return;
	    }

	    System.out.println("Login effettuato. Sei nella lobby: " + conferma.getIdAss());

	    // 3️⃣ SE SONO L'HOST (primo giocatore della lobby)
	    // Il server identifica il creatore come il client con idAss = 0
	    if (conferma.getIdAss() == 0) {
	        System.out.print("Vuoi avviare la partita? (s/n): ");
	        String scelta = scanner.nextLine();
	        if (scelta.equalsIgnoreCase("s")) {
	            System.out.print("Numero turni: ");
	            int turni = scanner.nextInt();
	            scanner.nextLine();

	            ClientMessage inizio = new InizioPartitaClient(turni);
	            client.inviaMsg(inizio.toString());
	            System.out.println("CLIENT HA INVIATO: " + inizio.toString());
	        }
	    } else {
	        System.out.println("Sei un partecipante, attendi l'avvio della partita dall'host.");
	    }

	    while (true) {
	        String msgServer = client.prelevaMsg();
	        ServerMessage sm = ClientParser.parse(msgServer);

	        if (sm.getId() == 4) { // InizioPartita
	            
	        	InizioPartitaServer ip = (InizioPartitaServer) sm;
	            System.out.println("La partita è iniziata! Turni: " + ip.getNTurni());
	            
	            break; // esci dal loop di attesa
	        } else if (sm.getId() == 2) { // ListaGiocatori (arriva mentre si aspetta in lobby)
	            
	        	ListaGiocatori lg = (ListaGiocatori) sm;
	            System.out.println("Giocatori in lobby (" + lg.getNPlayer() + "):");
	            
	            for (int i = 0; i < lg.getNPlayer(); i++) {
	                System.out.println("- " + lg.getNomePlayer()[i]);
	            }
	        }else {
	        	System.out.println("Client pre partita: Mesaggio non riconosciuto");
	        }
	        
	    }

	    client.gestisciPartita();
	}

}
