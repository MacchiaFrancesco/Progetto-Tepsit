package ruota.server.Messaggi;

public class TimerMinigiochi implements ServerMessage {

    private static String id = "12";
    private int secondi;

    public TimerMinigiochi(int secondi) {
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