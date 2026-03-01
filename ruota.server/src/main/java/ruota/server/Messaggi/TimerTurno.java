package ruota.server.Messaggi;

public class TimerTurno implements ServerMessage {
	private static String id="012";
	private int secondi;
	
	public TimerTurno(int secondi) {
		this.secondi = secondi;
	}

	public String tostring() {
		String messaggio = id+";"+secondi;
		return messaggio;
	}
}
