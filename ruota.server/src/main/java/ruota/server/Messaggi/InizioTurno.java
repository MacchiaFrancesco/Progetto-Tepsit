package ruota.server.Messaggi;

public class InizioTurno implements ServerMessage{
	private static String id="010";
	private String frase;
	private String contesto;
	
	public InizioTurno(String frase, String Contesto) {
		this.contesto=contesto;
		this.frase=frase;
	}

	public String tostring() {
		String messaggio = id+";"+ServerMessage.aggAsterischi(frase, 52)+";"+ServerMessage.aggAsterischi(contesto, 30);
		return messaggio;
	}
}
