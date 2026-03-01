package ruota.server.Messaggi;

public class SoluzioneCorretta implements ServerMessage{

	private static int id = 041;
	private boolean esito;
	private int soldi;

	public SoluzioneCorretta(boolean esito, int soldi) {
		this.esito = esito;
		this.soldi = soldi;
	}

	public boolean isEsito() {
		return esito;
	}

	public int getSoldi() {
		return soldi;
	}

	public String toString() {
		String messaggio = id+";"+esito+";"+soldi;
		return messaggio;
	}
}