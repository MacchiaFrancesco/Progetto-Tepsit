package ruota.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Ricezione implements Runnable{

		private CodaCircolare coda;
		private BufferedReader reader;
		private Socket socket;
		
		public Ricezione(Socket socket, CodaCircolare coda) {
			try {
				this.socket = socket;
				this.coda = coda;
				InputStream in = socket.getInputStream(); // Ottiene l'input stream per ricever dati
		                 
		        this.reader = new BufferedReader(new InputStreamReader(in)); // Usa un BufferedReader per poter leggere RIGHE di testo  
			}catch (IOException e) {
				closeEverything(socket, reader);
			}
		}

		public void leggiDaSocket() throws InterruptedException{
			
			try {
				String messaggio = reader.readLine();
				coda.inserisci(messaggio);
				
			}catch (IOException e) {
				closeEverything(socket, reader);
			}
			
		}
		
		public void closeEverything(Socket socket, BufferedReader bufferedReader) {
			try {
				if(bufferedReader != null) {
					bufferedReader.close();
				}
				if(socket != null) {
					socket.close();
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
		
		@Override
		public void run() {
			while(socket.isConnected()) {
				
				try {
					leggiDaSocket();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
	}

