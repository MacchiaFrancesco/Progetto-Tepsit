package ruota.server.Messaggi;

public class SoluzioneCorretta implements ServerMessage {

    private static int id = 41;
    private boolean esito;
    private int soldi;

    public SoluzioneCorretta(boolean esito, int soldi) {
        this.esito = esito;
        this.soldi = soldi;
    }

    @Override
    public int getId() {
        return id;
    }

    public boolean isEsito() {
        return esito;
    }

    public int getSoldi() {
        return soldi;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + esito + ";" + soldi;
        return messaggio;
    }
}