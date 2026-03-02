package ruota.client.Messaggi;

public class InizioPartita implements ClientMessage {

    private static int id = 3;
    private int nTurni;

    public InizioPartita(int nTurni) {
        this.nTurni = nTurni;
    }

    public int getNTurni() {
        return nTurni;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + ";" + nTurni;
    }
}