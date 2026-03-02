package ruota.server.Messaggi;

public class ControlloConnessione implements ServerMessage {

    private static int id = 903;
    private int timestamp;

    public ControlloConnessione(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + timestamp;
        return messaggio;
    }
}