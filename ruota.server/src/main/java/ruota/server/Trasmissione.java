package ruota.server;

//import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
//import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Trasmissione implements Runnable{
	private CodaCircolare coda;
//	private BufferedWriter bufferedWriter;
	private Socket socket;
	PrintWriter writer;
	
	public Trasmissione(Socket socket, CodaCircolare coda) {
		try {
			this.socket = socket;
			this.coda = coda;
			OutputStream out = socket.getOutputStream(); // Ottiene l'output stream per inviare dati
			writer = new PrintWriter(out, true);  // Usa un PrintWriter per inviare RIGHE di testo al server
		}catch (IOException e) {
			closeEverything(socket);
		}
	}

	public void inviaSuSocket() throws InterruptedException{
		String messaggio = coda.preleva();
		writer.write(messaggio);
//			bufferedWriter.newLine(); //i client utilizzano readline, quindi aspetteranno una newline prima di smettere di aspettare i messaggi .write non invia un carattere newline 
//			bufferedWriter.flush();
		
	}
	
	public void closeEverything(Socket socket) {
		try {
//			if(bufferedWriter != null) {
//				System.out.println("buffer chiuso");
//				bufferedWriter.close();
//			}
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
