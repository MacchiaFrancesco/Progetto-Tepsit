package ruota.server.Messaggi;

public class InizioFaseFinale  implements ServerMessage{

	private static int id = 100;
	private int idVincitore;
	private int soldi;
	
	public InizioFaseFinale(int idVincitore, int soldi) {
		this.idVincitore = idVincitore;
		this.soldi = soldi;
	}
	
	public String tostring() {
		String messaggio = id+";"+idVincitore+";"+soldi;
		return messaggio;
	}
}
