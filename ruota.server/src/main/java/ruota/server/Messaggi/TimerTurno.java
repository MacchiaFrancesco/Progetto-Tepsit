package ruota.server.Messaggi;

public class TimerTurno implements ServerMessage {

    private static String id = "13";
    private int secondi;

    public TimerTurno(int secondi) {
        this.secondi = secondi;
    }

    @Override
    public int getId() {
        return Integer.parseInt(id);
    }

    public int getSecondi() {
        return secondi;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + secondi;
        return messaggio;
    }
}