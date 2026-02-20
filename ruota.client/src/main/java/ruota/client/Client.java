package ruota.client;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	private CodaCircolare codaRicezione;
	private CodaCircolare codaTrasmissione;
	private String username;
	private Socket socket;
	
	private static String indirizzoServer = "127.0.0.1";
	private static int port = 5656;
	
	public Client (Socket socket, String username) {
		this.socket = socket;
		this.username = username;
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
	
	public void inviaMessaggio(String Msg) throws InterruptedException {
		codaTrasmissione.inserisci(Msg); 
	}
	
	public String prelevaMessaggio() throws InterruptedException {
		String messaggio = codaRicezione.preleva();
		return messaggio;
	}
	
	public void ciao() throws InterruptedException {
		
		inviaMessaggio("ciao");
		System.out.println(prelevaMessaggio());
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Inserisci il tuo username: ");
		String username = scanner.nextLine();
		Socket socket = new Socket(indirizzoServer, port);
		Client client = new Client(socket, username);
		client.ciao();
		scanner.close();
	}

}
