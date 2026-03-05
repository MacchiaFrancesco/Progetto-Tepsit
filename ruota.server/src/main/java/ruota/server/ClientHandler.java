package ruota.server;

import ruota.client.Messaggi.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import ruota.server.Messaggi.*;

public class ClientHandler implements Runnable {

    public static ArrayList<ArrayList<ClientHandler>> clientHandlers = new ArrayList<>();
    public static final Object lock = new Object(); // serve anche a ControlloConnessione

    private Socket socket;
    private String clientUsername;
    private int idClient;
    private CodaCircolare codaToClient;
    private CodaCircolare codaFromClient;
    private Partita partita;
    private String codiceLobby;
    private int minGiocatori = 1;

    public ClientHandler(Socket socket, CodaCircolare codaToClient, CodaCircolare codaFromClient) {
        try {
            this.socket = socket;
            this.codaToClient = codaToClient;
            this.codaFromClient = codaFromClient;

            ClientMessage msg = ServerParser.parse(codaFromClient.preleva());
            if (msg instanceof LoginGiocatore) {
                LoginGiocatore login = (LoginGiocatore) msg;
                this.clientUsername = login.getNome();
                this.codiceLobby = String.valueOf(login.getCodice());
            }

            synchronized(lock) {
                int lobbyIndex = trovaLobby(codiceLobby);

                if (lobbyIndex == -1) {
                    ArrayList<ClientHandler> nuovaLobby = new ArrayList<>();
                    nuovaLobby.add(this);
                    clientHandlers.add(nuovaLobby);
                    idClient = 0; // primo giocatore della lobby = host
                } else {
                    clientHandlers.get(lobbyIndex).add(this);
                    idClient = clientHandlers.get(lobbyIndex).size() - 1; // indice dentro la lobby
                }
            }

            // Conferma login
            ConfermaLogin cL = new ConfermaLogin(idClient, true);
            codaToClient.inserisci(cL.toString());
            

        } catch (InterruptedException e) {
            closeEverything(socket);
        }
    }

    private int trovaLobby(String codice) {
        for (int i = 0; i < clientHandlers.size(); i++) {
            ArrayList<ClientHandler> lobby = clientHandlers.get(i);
            if (lobby.get(0).codiceLobby.equals(codice)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
            	 // fatto da -> Tartamella <- se la partita è iniziata NON leggere più dalla coda :D
                if (partita != null) {
                    Thread.sleep(50);
                    continue;
                }
                
                String raw = codaFromClient.preleva();
                System.out.println("SERVER HA RICEVUTO: " + raw);

                ClientMessage mg = ServerParser.parse(raw);

                if (mg instanceof InizioPartitaClient) {
                    InizioPartitaClient iPC = (InizioPartitaClient) mg;

                    synchronized (lock) {
                        int lobbyIndex = trovaLobby(codiceLobby);
                        if (lobbyIndex == -1) continue;

                        ArrayList<ClientHandler> lobby = clientHandlers.get(lobbyIndex);

                        boolean host = lobby.get(0) == this;
                        boolean abbastanzaGiocatori = lobby.size() >= minGiocatori;

                        if (host && abbastanzaGiocatori) {
                            avviaPartitaDaLobby(lobbyIndex, iPC.getNTurni());
                        }
                    }
                }

            } catch (InterruptedException e) {
                closeEverything(socket);
                break;
            }
        }
    }

    public void avviaPartitaDaLobby(int lobbyId, int nTurni) throws InterruptedException {
        synchronized (lock) {
            ArrayList<ClientHandler> lobby = clientHandlers.get(lobbyId);

            ArrayList<Giocatore> giocatori = new ArrayList<>();
            for (ClientHandler ch : lobby) {
                giocatori.add(new Giocatore(ch.clientUsername, ch.codaFromClient, ch.codaToClient));
                ch.setPartita(null); // oppure crea la partita qui
            }

            Partita p = new Partita(giocatori, nTurni);

            for (ClientHandler ch : lobby) {
                ch.setPartita(p);
            }

            clientHandlers.remove(lobbyId);
            
            new Thread(p).start();
            System.out.println("PARTITA "+ lobbyId +" AVVIATA con " + lobby.size() + " giocatori");
        }
    }

    public void setPartita(Partita p) { this.partita = p; }

    public Socket getSocket() { return socket; }

    public CodaCircolare getCodaToClient() { return codaToClient; }

    public int getIdClient() { return idClient; }

    public void broadcastMessage(String messageToSend) {
        synchronized(lock) {
            for (ArrayList<ClientHandler> lobby : clientHandlers) {
                for (ClientHandler ch : lobby) {
                    if (ch != this) {
                        try { ch.codaToClient.inserisci(messageToSend); }
                        catch (InterruptedException e) { closeEverything(socket); }
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
//        broadcastMessage("Server: " + clientUsername + " si e' disconnesso dalla partita");
    }

    public void closeEverything(Socket socket) {
        removeClientHandler();
        try { if (socket != null) socket.close(); } catch (IOException e) { e.printStackTrace(); }
    }

    public void handlerToClient(ArrayList<Giocatore> listaGiocatori) {
        if (partita != null) partita.setListaGiocatori(listaGiocatori);
        else System.err.println("Errore: partita non inizializzata");
    }

    public void uniscitiLobby(int lobbyId) throws InterruptedException {
        synchronized(lock) {
            if (lobbyId >= 0 && lobbyId < clientHandlers.size()) {
                clientHandlers.get(lobbyId).add(this);
                idClient = lobbyId;
//                broadcastMessage("Server: " + clientUsername + " si è unito alla lobby " + idClient);
            } else {
//                codaToClient.inserisci("Lobby non esistente");
            }
        }
    }
}