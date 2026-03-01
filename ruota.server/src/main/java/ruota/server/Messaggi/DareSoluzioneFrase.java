package ruota.server.Messaggi;

public class DareSoluzioneFrase {

	private static int id = 041;
	private String s;
	
	public DareSoluzioneFrase(String s) {
		this.s = s;
	}
	
	public String toString() {
		String messaggio = id+";"+s;
		return messaggio;
	}
}
