package ruota.server.Messaggi;

public class RisultatoRuota implements ServerMessage{

	private static int id = 021;
	private int risultato;
	
	public RisultatoRuota(int risultato) {
		this.risultato = risultato;
	}
	
	public String tostring() {
		String messaggio = id+";"+risultato;
		return messaggio;
	}
	
	
}
