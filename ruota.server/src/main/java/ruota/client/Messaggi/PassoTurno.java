package ruota.client.Messaggi;

public class PassoTurno implements ClientMessage{

	private static String id = "050";
	
	public PassoTurno() {	
	}
	
	public String tostring() {
		String messaggio = id;
		return messaggio;
	}
}
