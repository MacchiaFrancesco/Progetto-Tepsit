package ruota.server.Messaggi;

public class AnnuncioFrase implements ServerMessage{

	private static int id = 042;
	private String frase;
	
	public AnnuncioFrase(String frase) {
		this.frase = frase;
	}
	
	
	
	public String tostring() {
		String messaggio = id+";"+ServerMessage.aggAsterischi(frase, 52);
		return messaggio;
	}
}
