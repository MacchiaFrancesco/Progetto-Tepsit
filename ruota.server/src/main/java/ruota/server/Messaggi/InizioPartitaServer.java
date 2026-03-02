package ruota.server.Messaggi;

public class InizioPartitaServer implements ServerMessage {

    private static String id = "004";
    private int nTurni;

    public InizioPartitaServer(int nTurni) {
        this.nTurni = nTurni;
    }

    @Override
    public int getId() {
        return Integer.parseInt(id);
    }

    public int getNTurni() {
        return nTurni;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + nTurni;
        return messaggio;
    }
}