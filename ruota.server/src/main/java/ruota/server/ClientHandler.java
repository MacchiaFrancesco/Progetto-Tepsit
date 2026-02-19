package ruota.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
	// l'arraylist client handler va wrappata in una arraylist di int (sarebbero gli id delle lobby), l'id viene generato per ogni nuova 'cella' e mandata al primo giocatore cosi la invia agli altri (una matrice)
	
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>(); //raggruppa tutti i client cosi' possiamo inviare messaggi a chi e' necessario, statico cosi' appartiene alla classe e non viene istanziato per ogni oggetto
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String clientUsername;
	private int idClient; //valorizzato dinamicamente in base a se il client crea o si unisce ad una lobby
	
	public ClientHandler(Socket socket) {
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.clientUsername = bufferedReader.readLine();
			clientHandlers.add(this);
			broadcastMessage("Server: " + clientUsername + " si e' unito alla partita!");
		} catch (IOException e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
	}
	
	@Override
	public void run() { //qua andrebbe messo il message parser, se il giocatore invia messaggio di creare una lobby crea un nuovo 'array', altrimenti se si vuole aggiungere ad una lobby lo aggiunge ad un array gia' esistente (probabilmente conviene fare una matrice di array list) a quel punto se il giocatore 1 di quella lobby invia il messaggio di inizio il server prende tutti i giocatori di quell'array e li invia alla classe Partita. ora per provare metto che c'e' una sola lobby, poi implementiamo. 
		String messageFromClient;
		
		while (socket.isConnected()) {
			try { // che palle ste try catch rendono illeggibile il codice
				messageFromClient = bufferedReader.readLine();
				broadcastMessage(messageFromClient);
				
			} catch (IOException e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
				break; //senno loop infinito di closeEverything()
			}		
		}
	}
	
	public void broadcastMessage(String messageToSend) {
		for (ClientHandler clientHandler : clientHandlers) { //scorre tutti i client
			try {
				if (!clientHandler.clientUsername.equals(clientUsername)) {
					clientHandler.bufferedWriter.write(messageToSend);
					clientHandler.bufferedWriter.newLine(); //i client utilizzano readline, quindi aspetteranno una newline prima di smettere di aspettare i messaggi .write non invia un carattere newline 
					clientHandler.bufferedWriter.flush();
				}
			} catch (IOException e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
			}
		}
	}
	
	public void removeClientHandler() {
		clientHandlers.remove(this);
		broadcastMessage("Server: " + clientUsername + " si e' disconnesso dalla partita");
	}
	
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		removeClientHandler();
		try {
			if (bufferedReader != null) { //evitare errore Null Pointer
				bufferedReader.close();
			}
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

}
