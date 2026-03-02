package ruota.client.Messaggi;

public class SelezioneBusta implements ClientMessage {

    private static int id = 104;
    private int nBusta;

    public SelezioneBusta(int nBusta) {
        this.nBusta = nBusta;
    }

    public int getNBusta() {
        return nBusta;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + ";" + nBusta;
    }
}