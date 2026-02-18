package ruota.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	//private Socket socket;
	//private BufferedReader bufferedReader;
	//private BufferedWriter bufferedWriter;
	private CodaCircolare codaRicezione;
	private CodaCircolare codaTrasmissione;
	private String username;
	
	public Client (Socket socket, String username) {
		//try {
			
			//this.socket = socket;
			//this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			//this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//this.username = username;
			
		//}catch (IOException e) {
		//	closeEverything(socket, bufferedReader, bufferedWriter);
		//}
	}
	
	//public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		//try {
		//	if (bufferedReader != null) { //evitare errore Null Pointer
		//		bufferedReader.close();
		//	}
		//	if(bufferedWriter != null) {
		//		bufferedWriter.close();
		//	}
		//	if(socket != null) {
		//		socket.close();
		//	}
		//} catch (IOException e){
		//	e.printStackTrace();
		//}
	//}
	
	public void assortimentoMessaggiTrasmissione(int idMsg) throws InterruptedException {
		String messaggio = null;
		//tutti i vari metodi per ogni messaggio ritornano una stringa
		
		codaTrasmissione.inserisci(messaggio); //ora che ci penso forse non serve che i client abbiano socket, buffwriter e buffreader se poi facciamo la coda circolare per i messaggi, basta che il thread di trasmissione li abbia no?
	}
	
	public void assortimentoMessaggiRicezione() throws InterruptedException {
		String messaggio = codaRicezione.preleva();
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

	}

}
