package ruota.server.Messaggi;

public class Bancarotta {

	private static int id = 904;
	private int idGiocatore;
	private int soldiPersi;
	
	public Bancarotta(int idGiocatore, int soldiPersi) {
		this.idGiocatore = idGiocatore;
		this.soldiPersi = soldiPersi;
	}
	
	public String tostring() {
		String messaggio = id+";"+idGiocatore+";"+soldiPersi;
		return messaggio;
	}
}
