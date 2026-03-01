package ruota.server;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import ruota.server.Messaggi.ConfermaLogin;

public class ClientHandler implements Runnable{
	public static ArrayList<ArrayList<ClientHandler>> clientHandlers = new ArrayList<>(); //Una struttura globale condivisa che contiene tutte le lobby del server, ognuna con i propri client. È il “database temporaneo” dei giocatori attivi.
	private Socket socket;
	static final Object lock = new Object(); //serve altrimenti non è thread-safe
	private String clientUsername;
	private int idClient; //valorizzato in base a se il client crea o si unisce ad una lobby
	private CodaCircolare codaToClient;
	private CodaCircolare codaFromClient;
	private String codiceLobby;
	//private BufferedReader bufferedReader;
	//private BufferedWriter bufferedWriter;
	
	public ClientHandler(Socket socket, CodaCircolare codaToClient, CodaCircolare codaFromClient) {
	    try {
	        this.socket = socket;
	        this.codaToClient = codaToClient;
	        this.codaFromClient = codaFromClient;
	        this.clientUsername = codaFromClient.preleva();
	        String codeLobby = codaFromClient.preleva(); // riceve il codice lobby
	        
	        ServerParser.parse(codaFromClient.preleva());

	        synchronized(lock) {
	            int lobbyIndex = trovaLobby(codeLobby);

	            if (lobbyIndex == -1) {
	                // codice non trovato, crea nuova lobby
	                ArrayList<ClientHandler> nuovaLobby = new ArrayList<>();
	                nuovaLobby.add(this);
	                clientHandlers.add(nuovaLobby);
	                idClient = clientHandlers.size() - 1;
	                
	            } else {
	                // codice trovato, unisciti alla lobby esistente
	                clientHandlers.get(lobbyIndex).add(this);
	                idClient = lobbyIndex;
	                
	            }
	        }

	        ConfermaLogin cL = new ConfermaLogin(idClient, true);
	        codaToClient.inserisci(cL.tostring());
	        this.codiceLobby = codeLobby;

	    } catch (InterruptedException e) {
	        closeEverything(socket);
	    }
	}
	
	private int trovaLobby(String codice) {
	    for (int i = 0; i < clientHandlers.size(); i++) {
	        ClientHandler primo = clientHandlers.get(i).get(0);
	        if (primo.codiceLobby.equals(codice)) {
	            return i;
	        }
	    }
	    return -1; // non trovata
	}
	
	public Socket getSocket() {
	    return socket;
	}

	public CodaCircolare getCodaToClient() {
	    return codaToClient;
	}

	public int getIdClient() {
	    return idClient;
	}
	
	// Metodo per far entrare un client in una lobby esistente
    public void uniscitiLobby(int lobbyId) throws InterruptedException {
        synchronized(lock) {
            if (lobbyId >= 0 && lobbyId < clientHandlers.size()) {
                clientHandlers.get(lobbyId).add(this);
                idClient = lobbyId;

                broadcastMessage("Server: " + clientUsername + " si è unito alla lobby " + idClient);
            } else {
                codaToClient.inserisci("Lobby non esistente");
            }
        }
    }
    
    
    
    
	@Override
	public void run() { //qua andrebbe messo il message parser, se il giocatore invia messaggio di creare una lobby crea un nuovo 'array', altrimenti se si vuole aggiungere ad una lobby lo aggiunge ad un array gia' esistente (probabilmente conviene fare una matrice di array list) a quel punto se il giocatore 1 di quella lobby invia il messaggio di inizio il server prende tutti i giocatori di quell'array e li invia alla classe Partita. ora per provare metto che c'e' una sola lobby, poi implementiamo. 
		String messageFromClient;
		
		while (socket.isConnected()) {
			try { 
				messageFromClient = codaFromClient.preleva();
				
				broadcastMessage("Little Lenzi");
				
			} catch (InterruptedException e) {
				closeEverything(socket);
				break; //senno loop infinito di closeEverything()
			}		
		}
	}
	
	public void broadcastMessage(String messageToSend) {
		//scorre tutti i client della lobby    
		synchronized(lock) {
	        for (ArrayList<ClientHandler> lobby : clientHandlers) {
	            for (ClientHandler clientHandler : lobby) {
	                if (!clientHandler.clientUsername.equals(clientUsername)) {
	                    try {
	                        clientHandler.codaToClient.inserisci(messageToSend);
	                    } catch (InterruptedException e) {
	                        closeEverything(socket);
	                    }
	                }
	            }
	        }
		}
	}
	
	public void removeClientHandler() {
		synchronized(lock) {
			clientHandlers.get(idClient).remove(this);

	        if (clientHandlers.get(idClient).isEmpty()) {
	            clientHandlers.remove(idClient);
	        }
		}
         broadcastMessage("Server: " + clientUsername + " si e' disconnesso dalla partita");
    }
	
	public void closeEverything(Socket socket) {
		removeClientHandler();
		try {
//			if (bufferedReader != null) { //evitare errore Null Pointer
//				bufferedReader.close();
//			}
//			if(bufferedWriter != null) {
//				bufferedWriter.close();
//			}
			if(socket != null) {
				socket.close();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}

}
