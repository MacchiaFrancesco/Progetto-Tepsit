package ruota.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private CodaCircolare codaRicezione;
	private CodaCircolare codaTrasmissione;
	private String username;
	
	public Client (Socket socket, String username) {
	
	}
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
