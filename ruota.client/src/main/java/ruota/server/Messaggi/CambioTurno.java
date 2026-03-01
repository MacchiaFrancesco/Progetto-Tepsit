package ruota.server.Messaggi;

public class CambioTurno implements ServerMessage {

    private static int id = 051;
    private int nuovoGiocatore;
    
    public CambioTurno(int nuovoGiocatore) {
        this.nuovoGiocatore = nuovoGiocatore;
    }

    public int getNuovoGiocatore() {
        return nuovoGiocatore;
    }

    @Override
    public String toString() {
        return id + ";" + nuovoGiocatore;
    }
}