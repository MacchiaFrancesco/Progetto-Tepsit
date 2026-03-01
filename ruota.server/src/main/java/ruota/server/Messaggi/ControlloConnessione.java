package ruota.server.Messaggi;

public class ControlloConnessione implements ServerMessage {

	private static int id = 903;
	private int timestamp;
	
	public ControlloConnessione(int timestamp) {
		this.timestamp = timestamp;
	}
	
	public String tostring() {
		String messaggio = id+";"+timestamp;
		return messaggio;
	}
}
