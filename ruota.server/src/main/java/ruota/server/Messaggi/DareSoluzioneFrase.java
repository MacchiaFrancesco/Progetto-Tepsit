package ruota.server.Messaggi;

public class DareSoluzioneFrase implements ServerMessage {

    private static int id = 41;
    private String s;

    public DareSoluzioneFrase(String s) {
        this.s = s;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getSoluzione() {
        return s;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + s;
        return messaggio;
    }
}