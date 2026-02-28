package ruota.server.Messaggi;

public class AnnuncioTurno implements ServerMessage{

	private static int id = 015;
	private int giocatore;
	
	public AnnuncioTurno(int giocatore) {
		this.giocatore = giocatore;
	}
	
	public String tostring() {
		String messaggio = id+";"+giocatore;
		return messaggio;
	}
}
