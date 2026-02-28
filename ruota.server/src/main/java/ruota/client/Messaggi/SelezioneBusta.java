package ruota.client.Messaggi;

public class SelezioneBusta {

	private static int id = 104;
	private int nBusta;
	
	public SelezioneBusta(int nBusta) {
		this.nBusta = nBusta;
	}
	
	public String tostring() {
		String messaggio = id+";"+nBusta;
		return messaggio;
	}
}
