package ruota.server.Messaggi;

public class InizioMinigioco {

	private static int id = 102;
	private int nGioco;
	
	public InizioMinigioco(int nGioco) {
		this.nGioco = nGioco;
	}
	
	public String tostring() {
		String messaggio = id+";"+nGioco;
		return messaggio;
	}
}
