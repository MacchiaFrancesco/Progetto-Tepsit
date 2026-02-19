package ruota.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket serverSocket;  //ascolta per connessioni in arrivo e client e crea un oggetto Socket per comunicare con loro
	
	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public void startServer() {
		try {
			while(!serverSocket.isClosed()) { //server rimane attivo finche' la socket non si chiude
				
				Socket socket = serverSocket.accept(); //se client si connette ritorna un oggetto Socket
				System.out.println("Un nuovo giocatore si e' connesso!");
				
				ClientHandler clientHandler = new ClientHandler(socket);
				Thread thread = new Thread(clientHandler);
				thread.start();
			}
		} catch(IOException e) {
			closeServerSocket();
		}
	}
	
	public void closeServerSocket() {
		//se la socket e' null e proviamo a chiuderla riceviamo un errore Null Pointer quindi lo gestiamo
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException{
		
		ServerSocket serverSocket = new ServerSocket(1234);
		Server server = new Server(serverSocket);
		server.startServer();
	}
}

