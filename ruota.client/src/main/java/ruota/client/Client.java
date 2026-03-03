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
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Inserisci il tuo username: ");
		String username = scanner.nextLine();
		System.out.print("Inserisci il codice della lobby, se non esiste una lobby con quel codice, ne verra' creata una utilizzando il codice inserito: ");
		int lobbyCode = scanner.nextInt();
		Socket socket = new Socket(indirizzoServer, port);
		Client client = new Client(socket, username, lobbyCode);
		client.testgioca();
	}

}
