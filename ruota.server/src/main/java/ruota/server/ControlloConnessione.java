package ruota.server;

import java.util.ArrayList;

public class ControlloConnessione implements Runnable {
	
	private final long checkIntervalMillis;

    public ControlloConnessione(long checkIntervalMillis) {
        this.checkIntervalMillis = checkIntervalMillis; // es. 5000 = 5 secondi
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(checkIntervalMillis);

                synchronized (ClientHandler.lock) {
                    ArrayList<ArrayList<ClientHandler>> lobbies = ClientHandler.clientHandlers;

                    for (int i = 0; i < lobbies.size(); i++) {
                        ArrayList<ClientHandler> lobby = lobbies.get(i);

                        if (lobby.isEmpty()) continue;

                        ClientHandler host = lobby.get(0);

                        // Usa il getter invece di accedere direttamente al campo privato
                        if (!host.getSocket().isConnected()) {
                            for (int j = 1; j < lobby.size(); j++) {
                                ClientHandler ch = lobby.get(j);
                                try {
                                    ch.getCodaToClient().inserisci(
                                        "Server: l'host della lobby " + i + " si è disconnesso!"
                                    );
                                } catch (InterruptedException e) {
                                    ch.closeEverything(ch.getSocket());
                                }
                            }

                            // Rimuovi la lobby
                            lobbies.remove(i);
                            i--; // aggiusta l’indice dopo la rimozione
                        }
                    }
                }

            } catch (InterruptedException e) {
                break; // il monitor può essere interrotto esternamente
            }
        }
    }
}
