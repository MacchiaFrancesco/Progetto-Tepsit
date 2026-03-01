package ruota.server.Messaggi;

public class ConfermaLogin implements ServerMessage{
	private static String id="001";	
	private int idAss;
	private boolean esito;
	
	public ConfermaLogin(int idAss, boolean esito) {
		this.idAss=idAss;
		this.esito=esito;
	}
	
	public String tostring() {
		String messaggio = id+";"+idAss+";"+esito;
		return messaggio;
	}
}
