package ruota.client.Messaggi;

public class InizioPartitaClient implements ClientMessage {

    private static String id = "3";
    private int nTurni;

    public InizioPartitaClient(int nTurni) {
        this.nTurni = nTurni;
    }

    public InizioPartitaClient() {
    }

    @Override
    public int getId() {
        return Integer.parseInt(id);
    }

    public String toString() {
        String messaggio = Integer.parseInt(id) + ";" + nTurni;
        return messaggio;
    }

    public int getNTurni() {
        return nTurni;
    }
}