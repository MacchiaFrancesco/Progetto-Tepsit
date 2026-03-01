package ruota.client.Messaggi;

public class LetteraIndovinata implements ClientMessage{

	private static int id = 032;
	private String lettera;
	
	public LetteraIndovinata(String lettera) {
		this.lettera = lettera;
	}
	
	public String tostring() {
		String messaggio = id+";"+lettera;
		return messaggio;
	}
}
