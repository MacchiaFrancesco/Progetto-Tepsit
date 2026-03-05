package ruota.server.Messaggi;

public class ConfermaAcquistoVocale implements ServerMessage {

    private static int id = 32;
    private Boolean conferma;

    public ConfermaAcquistoVocale(Boolean conferma) {
        this.conferma = conferma;
    }

    @Override
    public int getId() {
        return id;
    }

    public Boolean getConferma() {
        return conferma;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + conferma;
        return messaggio;
    }
}