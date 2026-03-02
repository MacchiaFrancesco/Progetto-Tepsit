package ruota.server;
import ruota.client.Messaggi.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import ruota.server.Messaggi.*;
import ruota.client.Messaggi.*;

public class ClientHandler implements Runnable{
	public static ArrayList<ArrayList<ClientHandler>> clientHandlers = new ArrayList<>(); //Una struttura globale condivisa che contiene tutte le lobby del server, ognuna con i propri client. È il “database temporaneo” dei giocatori attivi.
	private Socket socket;
	static final Object lock = new Object(); //serve altrimenti non è thread-safe
	private String clientUsername;
	private int idClient; //valorizzato in base a se il client crea o si unisce ad una lobby
	private CodaCircolare codaToClient;
	private CodaCircolare codaFromClient;
	private Partita partita;
	private String codiceLobby;
	//private BufferedReader bufferedReader;
	//private BufferedWriter bufferedWriter;
	//HELP ME MACCHIARDO
	
	public ClientHandler(Socket socket, CodaCircolare codaToClient, CodaCircolare codaFromClient) {
	    try {
	        this.socket = socket;
	        this.codaToClient = codaToClient;
	        this.codaFromClient = codaFromClient;
	        this.clientUsername = codaFromClient.preleva();
	        
	        ClientMessage msg = ServerParser.parse(codaFromClient.preleva());
			if (msg instanceof LoginGiocatore) { //usare instanceof per evitare class cast exception 
			    LoginGiocatore login = (LoginGiocatore) msg;
			    this.clientUsername = login.getNome();
			    this.codiceLobby = String.valueOf(login.getCodice());
			}

	        synchronized(lock) {
	            int lobbyIndex = trovaLobby(codiceLobby);

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
	        codaToClient.inserisci(cL.toString());

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
	
	public void handlerToClient(ArrayList<Giocatore> listaGiocatori) {
	    if (partita != null) {
	        partita.setListaGiocatori(listaGiocatori);  
	    } else {
	        System.err.println("Errore: partita non inizializzata in ClientHandler");
	    }
	}
	
	public void setPartita(Partita p) {
	    this.partita = p;
	}
	
	
	public void avviaPartitaDaLobby(int lobbyId, int nTurni) { //<---------little lenzi controlla
	    synchronized(lock) {
	        ArrayList<ClientHandler> lobby = clientHandlers.get(lobbyId);
	        
	        // Costruisci la lista Giocatori dalla lobby
	        ArrayList<Giocatore> giocatori = new ArrayList<>();
	        for (ClientHandler ch : lobby) {
	            giocatori.add(new Giocatore(ch.clientUsername, ch.codaFromClient, ch.codaToClient));
	        }
	        
	        // Crea la Partita con i giocatori
	        Partita p = new Partita(giocatori, nTurni);
	        
	        // Assegna la partita a tutti i ClientHandler della lobby
	        for (ClientHandler ch : lobby) {
	            ch.setPartita(p);
	        }
	        
	        // Rimuovi la lobby dall'array globale (non serve più)
	        clientHandlers.remove(lobbyId);
	        
	        // Avvia la partita in un thread separato
	        new Thread(p).start();
	    }
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
	public void run() {
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
