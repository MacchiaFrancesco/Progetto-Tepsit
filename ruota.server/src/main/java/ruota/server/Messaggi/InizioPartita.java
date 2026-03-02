package ruota.server.Messaggi;

public class InizioPartita implements ServerMessage {

    private static String id = "4";
    private int nTurni;

    public InizioPartita(int nTurni) {
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