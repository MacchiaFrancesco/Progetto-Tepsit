package ruota.server.Messaggi;

public class ListaGiocatori implements ServerMessage {
	private static String id="002";
	private int nPlayer;	
	private String[] nomePlayer;
	
	public ListaGiocatori(int nPlayer, String[] nomePlayer) {
		this.nPlayer=nPlayer;
		this.nomePlayer=nomePlayer;
	}
	
	public String aggAsterischi(String string, int lunghezza) {
		while (string.length() < lunghezza) {
			string+= "*";
		}
		return string;
	}
	
	public String tostring() {
		String messaggio = id;
		for (int i =0; i<nPlayer; i++) {
			messaggio+= ";"+aggAsterischi(nomePlayer[i], 20);
		}
		return messaggio;
	}
}
