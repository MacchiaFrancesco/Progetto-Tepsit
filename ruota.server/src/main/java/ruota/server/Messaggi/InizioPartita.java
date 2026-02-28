package ruota.server.Messaggi;

public class InizioPartita {
	private static String id="004";
	private String nTurni;
	
	public InizioPartita(String nTurni) {
		this.nTurni=nTurni;
	}
	
	public String tostring() {
		String messaggio = id+";"+nTurni;
		return messaggio;
	}

}
