package ruota.server;

import java.util.ArrayList;

public class Partita implements Runnable{

	private Ruota ruota;
	private Frase frase;
	private ArrayList<ClientHandler> listaGiocatori; //tutte le cose relative ai giocatori sono passate dal ClientHandler (socket, reader, writer, username, id)
	
	public Partita(ArrayList<ClientHandler> listaGiocatori) {
		this.listaGiocatori = listaGiocatori;
		this.ruota = new Ruota(); //ogni partita ha la propria istanza di ruota e di frase
		this.frase = new Frase();
	}
	@Override
	public void run() {
		// il vero succo del progetto
		
	}

}
