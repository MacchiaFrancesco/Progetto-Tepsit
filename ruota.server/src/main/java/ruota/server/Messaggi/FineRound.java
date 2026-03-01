package ruota.server.Messaggi;

public class FineRound implements ServerMessage {

	private static int id = 052;
	private int round;
	private int roundTot;
	private int numeroGiocatori;
	private int[] classifica;

	public FineRound(int round, int roundTot, int numeroGiocatori, int[]classifica) {
		this.round = round;
		this.roundTot = roundTot;
		this.classifica = classifica;
		this.numeroGiocatori = numeroGiocatori;
	}

	public int getRound() {
		return round;
	}

	public int getRoundTot() {
		return roundTot;
	}

	public int getNumeroGiocatori() {
		return numeroGiocatori;
	}

	public int[] getClassifica() {
		return classifica;
	}

	public String tostring() {
		String messaggio = id+";"+round+";"+roundTot+";"+numeroGiocatori;
		for (int i = 0; i < classifica.length; i++) {
			messaggio += ";"+(i+1)+";"+classifica[i];
		}
		return messaggio;
	}
}