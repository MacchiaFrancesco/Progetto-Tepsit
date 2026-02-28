package ruota.server.Messaggi;

public interface ServerMessage {

	public static String aggAsterischi(String string, int lunghezza) {
		while (string.length() < lunghezza) {
			string+= "*";
		}
		return string;
	}
}
