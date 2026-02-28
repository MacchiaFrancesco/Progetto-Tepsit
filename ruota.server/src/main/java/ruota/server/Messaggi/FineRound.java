package ruota.server.Messaggi;

public class FineRound {

	private static int id = 052;
	private int round;
	private int roundTot;
	private int[] classifica;
	
	public FineRound(int round, int roundTot, int[]classifica) {
		this.round = round;
		this.roundTot = roundTot;
		this.classifica = classifica;
	}
	
	public String tostring() {
		String messaggio = id+";"+round+";"+roundTot;
		for (int i = 0; i < classifica.length; i++) {
			messaggio += ";"+classifica[i];
		}
		return messaggio;
	}
}
