package ruota.server.Messaggi;

public class ConfermaAcquistoVocale implements ServerMessage {

    private static int id = 32;
    private int conferma;

    public ConfermaAcquistoVocale(int conferma) {
        this.conferma = conferma;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getConferma() {
        return conferma;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + conferma;
        return messaggio;
    }
}