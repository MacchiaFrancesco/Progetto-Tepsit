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
		System.out.println("Server avviato.");
		System.out.println("Server in ascolto su "+ serverSocket.getLocalSocketAddress());
		System.out.println("Server in ascolto su "+ serverSocket.getInetAddress()+ ":" + serverSocket.getLocalPort());
		
		
		try {
			while(!serverSocket.isClosed()) { //server rimane attivo finche' la socket non si chiude
				
				Socket socket = serverSocket.accept(); //se client si connette ritorna un oggetto Socket
				System.out.println("Un nuovo giocatore si e' connesso!");
				
				CodaCircolare codaToClient = new CodaCircolare(10);
				Trasmissione trasmissione = new Trasmissione(socket, codaToClient);
				Thread tr = new Thread(trasmissione, "Trasmissione");
				tr.start();
				
				CodaCircolare codaFromClient = new CodaCircolare(10);
				Ricezione ricezione = new Ricezione(socket, codaFromClient);
				Thread ri = new Thread(ricezione, "Ricezione");
				ri.start();
				
				ClientHandler clientHandler = new ClientHandler(socket, codaToClient, codaFromClient);
				Thread client = new Thread(clientHandler);
				client.start();
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
		
		ServerSocket serverSocket = new ServerSocket(5656);
		Server server = new Server(serverSocket);
		server.startServer();
	}
}

