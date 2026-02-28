package ruota.client.Messaggi;

public class GiraRuota implements ClientMessage{

	private static String id = "020";
	
	public GiraRuota() {
		
	}
	
	public String toString() {
		String messaggio = id;
		return messaggio;
	}
}
