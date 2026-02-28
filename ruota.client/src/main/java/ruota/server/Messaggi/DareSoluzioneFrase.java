package ruota.server.Messaggi;

public class DareSoluzioneFrase {

	private static int id = 041;
	private int esito;
	private int soldi;
	
	public DareSoluzioneFrase(int esito, int soldi) {
		this.esito = esito;
		this.soldi = soldi;
	}
	
	public String toString() {
		String messaggio = id+";"+esito+";"+soldi;
		return messaggio;
	}
}
