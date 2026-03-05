package ruota.client.Messaggi;

public class LetteraIndovinata implements ClientMessage {

    private static int id = 30;
    private String lettera;

    public LetteraIndovinata(String lettera) {
        this.lettera = lettera;
    }

    public String getLettera() {
        return lettera;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + ";" + lettera;
    }
}