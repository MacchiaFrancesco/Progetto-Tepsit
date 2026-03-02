package ruota.server.Messaggi;

public class AnnuncioTurno implements ServerMessage {

    private static int id = 15;
    private int giocatore;

    public AnnuncioTurno(int giocatore) {
        this.giocatore = giocatore;
    }

    public int getGiocatore() {
        return giocatore;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + ";" + giocatore;
    }
}