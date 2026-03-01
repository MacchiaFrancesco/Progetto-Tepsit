package ruota.server.Messaggi;

public class DisconnessioneGiocatore implements ServerMessage{

	private static int id = 901;
	private int idGiocatore;
	private String nome;

	public DisconnessioneGiocatore(int idGiocatore, String nome) {
		this.idGiocatore = idGiocatore;
		this.nome = nome;
	}

	public int getIdGiocatore() {
		return idGiocatore;
	}

	public String getNome() {
		return nome;
	}

	public String tostring() {
		String messaggio = id+";"+idGiocatore+";"+nome;
		return messaggio;
	}
}