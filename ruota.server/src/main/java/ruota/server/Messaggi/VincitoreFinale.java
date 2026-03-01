package ruota.server.Messaggi;

public class VincitoreFinale implements ServerMessage {

	private static int id = 106;
	private int idGiocatore;
	private String nome;
	private int soldi;

	public VincitoreFinale(int idGiocatore, String nome, int soldi) {
		this.idGiocatore = idGiocatore;
		this.nome = nome;
		this.soldi = soldi;
	}

	public int getIdGiocatore() {
		return idGiocatore;
	}

	public String getNome() {
		return nome;
	}

	public int getSoldi() {
		return soldi;
	}

	public String tostring() {
		String messaggio = id+";"+idGiocatore+";"+nome+";"+soldi;
		return messaggio;
	}
}