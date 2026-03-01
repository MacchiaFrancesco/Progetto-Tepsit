package ruota.server.Messaggi;

public class ConfermaAcquistoVocale implements ServerMessage {

	private static int id = 032;
	private int conferma;
	
	public ConfermaAcquistoVocale(int conferma) {
		this.conferma = conferma;
	}
	
	public String tostring() {
		String messaggio = id+";"+conferma;
		return messaggio;
	}
}
