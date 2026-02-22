package ruota.server.Messaggi;

public class ConfermaLogin {
	private static String id="001";	
	private String idAss;
	private boolean esito;
	
	public ConfermaLogin(String IdAss, boolean esito) {
		this.idAss=idAss;
		this.esito=esito;
	}
	
}
