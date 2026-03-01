package ruota.client.Messaggi;

public class InizioPartita implements ClientMessage{
	private static int id=003;
	private int nTurni;
	
	public InizioPartita(int nTurni) {
		this.nTurni=nTurni;
	}

	public String tostring() {
		String messaggio = id+";"+nTurni;
		return messaggio;
	}
}
