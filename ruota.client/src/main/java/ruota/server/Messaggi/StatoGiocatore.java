package ruota.server.Messaggi;

public class StatoGiocatore {
	private static String id="011";
	private int nPlayer;
	private int[] salvadanaioGiocatori;
	private int[] soldiTurno;
	
	public StatoGiocatore(int nPlayer, int[] salvadanaioGiocatori, int[] soldiTurno) {
		this.nPlayer = nPlayer;
		this.salvadanaioGiocatori = salvadanaioGiocatori;
		this.soldiTurno = soldiTurno;
	}

	public String tostring() {
		String messaggio = id+";"+nPlayer;
		for (int i = 0; i < nPlayer; i++) {
			messaggio += ";"+(i+1)+";"+salvadanaioGiocatori[i]+";"+soldiTurno[i];
		}
		return messaggio;
	}
}
