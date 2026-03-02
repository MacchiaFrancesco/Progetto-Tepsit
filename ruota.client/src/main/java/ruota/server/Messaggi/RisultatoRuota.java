package ruota.server.Messaggi;

public class RisultatoRuota implements ServerMessage {

    private static int id = 21;
    private int risultato;

    public RisultatoRuota(int risultato) {
        this.risultato = risultato;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getRisultato() {
        return risultato;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + risultato;
        return messaggio;
    }
}