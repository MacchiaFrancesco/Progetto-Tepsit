package ruota.client;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import ruota.client.Messaggi.*;
import ruota.server.Messaggi.*;


public class Client {

	private CodaCircolare codaRicezione;
	private CodaCircolare codaTrasmissione;
	private String username;
	private int lobbyCode;
	private Socket socket;
	
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
	        }
	    } else {
	        System.out.println("Sei un partecipante, attendi l'avvio della partita dall'host.");
	    }

	    // 4️⃣ CICLO DI RICEZIONE MESSAGGI DAL SERVER (opzionale)
	    while (true) {
	        String msgServer = client.prelevaMsg();
	        ServerMessage sm = ClientParser.parse(msgServer);

	        if (sm instanceof InizioPartitaServer) {
	            System.out.println("La partita è iniziata!");
	            // qui puoi iniziare la logica del gioco
	        } else if (sm instanceof ListaGiocatori) {
	            // gestisci lista giocatori
	        } else if (sm instanceof RisultatoRuota) {
	            // gestisci risultato ruota
	        }
	        // aggiungi altri messaggi server se vuoi
	    }
	}

}
