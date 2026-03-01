package ruota.server.Messaggi;

public class InizioPartita implements ServerMessage {
	private static String id="004";
	private int nTurni;
	
	public InizioPartita(int nTurni) {
		this.nTurni=nTurni;
	}
	
	public String tostring() {
		String messaggio = id+";"+nTurni;
		return messaggio;
	}

}
