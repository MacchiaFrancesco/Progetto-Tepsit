package ruota.client.Messaggi;

public class InizioPartitaClient implements ClientMessage{
	private static String id="003";
	private int nTurni;
	
	public InizioPartitaClient(int nTurni) {
		this.nTurni=nTurni;
	}
	
	public InizioPartitaClient() {
	}

	public String tostring() {
		String messaggio = id+";"+nTurni;
		return messaggio;
	}
	
	public int getNTurni() {
		return nTurni;
	}
}
