package ruota.server.Messaggi;

public class CambioTurno {

	private static int id = 051;
	private int nuovoGiocatore;
	
	public CambioTurno(int nuovoGiocatore) {
		this.nuovoGiocatore = nuovoGiocatore;
	}
	
	public String tostring() {
		String messaggio = id+";"+nuovoGiocatore;
		return messaggio;
	}
}
