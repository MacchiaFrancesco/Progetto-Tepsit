package ruota.server.Messaggi;

public class InizioPartitaServer {
	private static String id="004";
	private int nTurni;
	
	public InizioPartitaServer(int nTurni) {
		this.nTurni=nTurni;
	}
	
	public String tostring() {
		String messaggio = id+";"+nTurni;
		return messaggio;
	}

}
