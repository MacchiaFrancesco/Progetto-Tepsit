package ruota.server.Messaggi;

public class DisconnessioneGiocatore {

	private static int id = 901;
	private int idGiocatore;
	private String nome;
	
	public DisconnessioneGiocatore(int idGiocatore, String nome) {
		this.idGiocatore = idGiocatore;
		this.nome = nome;
	}
	
	public String tostring() {
		String messaggio = id+";"+idGiocatore+";"+nome;
		return messaggio;
	}
}
