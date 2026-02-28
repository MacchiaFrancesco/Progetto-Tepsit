package ruota.server.Messaggi;

public class EsitoLettera implements ServerMessage{

	private static int id = 033;
	private char lettera;
	private int presente;
	private int volte;
	private String fraseParziale;
	private int soldiGuadagnati;
	
	public EsitoLettera(char lettera, int presente, int volte, String fraseParziale, int soldiGuadagnati) {
		this.lettera = lettera;
		this.presente = presente;
		this.volte = volte;
		this.fraseParziale = fraseParziale;
		this.soldiGuadagnati = soldiGuadagnati;
	}
	
	public String toString() {
		String messaggio = id+";"+lettera+";"+presente+";"+volte+";"+fraseParziale+";"+soldiGuadagnati;
		return messaggio;
	}
}
