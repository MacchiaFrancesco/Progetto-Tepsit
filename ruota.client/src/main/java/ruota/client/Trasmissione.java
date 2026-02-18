package ruota.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Trasmissione implements Runnable{
	private CodaCircolare coda;
	private BufferedWriter bufferedWriter;
	private Socket socket;
	
	public Trasmissione(Socket socket, BufferedWriter bufferedWriter, CodaCircolare coda) {
		try {
			this.socket = socket;
			this.coda = coda;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));	
		}catch (IOException e) {
			closeEverything(socket, bufferedWriter);
		}
	}

	public void inviaSuSocket() throws InterruptedException{
		String messaggio = coda.preleva();
		try {
			bufferedWriter.write(messaggio);
			bufferedWriter.newLine(); //i client utilizzano readline, quindi aspetteranno una newline prima di smettere di aspettare i messaggi .write non invia un carattere newline 
			bufferedWriter.flush();
		}catch (IOException e) {
			closeEverything(socket, bufferedWriter);
		}
		
	}
	
	public void closeEverything(Socket socket, BufferedWriter bufferedWriter) {
		try {
			if(bufferedWriter != null) {
				bufferedWriter.close();
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
				inviaSuSocket();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
}
