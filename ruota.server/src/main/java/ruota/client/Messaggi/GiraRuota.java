package ruota.client.Messaggi;

public class GiraRuota implements ClientMessage {

    private static String id = "020";

    public GiraRuota() {
    }

    @Override
    public int getId() {
        return 20;
    }

    public String toString() {
        return id;
    }
}