package ruota.server.Messaggi;

public class InizioMinigioco implements ServerMessage {

    private static int id = 102;
    private int nGioco;

    public InizioMinigioco(int nGioco) {
        this.nGioco = nGioco;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getNGioco() {
        return nGioco;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + nGioco;
        return messaggio;
    }
}