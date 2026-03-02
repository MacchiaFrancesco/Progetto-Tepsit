package ruota.client.Messaggi;

public interface ClientMessage {

    public int getId();

    public static String aggAsterischi(String string, int lunghezza) {
        while (string.length() < lunghezza) {
            string += "*";
        }
        return string;
    }
}