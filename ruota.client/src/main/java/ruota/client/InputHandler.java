package ruota.client;

public class InputHandler {
	public static void confermaLogin(int idGiocatore, Boolean esito) {
	}

	public static void listaGiocatori(int numeroGiocatori, String[] listaNomiGiocatori) {
	}

	public static void inizioPartita(int numeroTotaliTurni) {
	}

	public static void inizioTurno(String frase, String contesto) {
	}

	public static void statoGiocatori(int numeroGiocatori, int[] idGiocatori, int[] salvadanaioGiocatori, int[] soldiTurnoGiocatori) {
		
	}

	public static void timerTurno(int secondiRimasti) {
	}

	public static void annuncioTurno(int giocatore) {
	}

	public static void risultatoDellaRuota(int risultato) {
	}

	public static void esitoLettera(String lettera, boolean presente, int volte, String faseParziale, int soldiGuadagnati) {
	}

	public static void soluzioneCorretta(boolean esito, int soldiVinti) {
	}

	public static void annuncioFrase(String frase) {
	}

	public static void cambioTurno(int nuovoGiocatore) {
	}

	public static void fineRound(int roundCorrente, int roundTotali) {
	// da espandere: classifica per ogni giocatore
	}

	public static void inizioFaseFinale(int idGiocatoreVincitore, int soldiTotali) {
	}

	public static void inizioMinigioco(int numeroGioco, int tipoGioco) {
	}

	public static void timerMinigiochi(int secondiRimasti) {
	}

	public static void premioFinale(int importoBusta, int soldiTotaliFinali) {
	}

	public static void vincitoreFinale(int idGiocatore, String nomeGiocatore, int soldiFinali) {
	}

	public static void disconnessioneGiocatore(int idGiocatore, String nomeGiocatore) {
	}

	public static void avvisoTimeout() {
	}

	public static void bancarotta(int idGiocatore, int soldiPersi) {
	}
}
