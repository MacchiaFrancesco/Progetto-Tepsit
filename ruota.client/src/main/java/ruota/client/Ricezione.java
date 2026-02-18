package ruota.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Ricezione {

	public class Trasmissione implements Runnable{
		private CodaCircolare coda;
		private BufferedReader bufferedReader;
		private Socket socket;
		
		public Trasmissione(Socket socket, BufferedReader bufferedReader, CodaCircolare coda) {
			try {
				this.socket = socket;
				this.coda = coda;
				this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));	
			}catch (IOException e) {
				closeEverything(socket, bufferedReader);
			}
		}

		public void leggiDaSocket() throws InterruptedException{
			
			try {
				String messaggio = bufferedReader.readLine();
				coda.inserisci(messaggio);
				
			}catch (IOException e) {
				closeEverything(socket, bufferedReader);
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
}
