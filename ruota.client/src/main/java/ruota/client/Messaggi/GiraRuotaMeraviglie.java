package ruota.client.Messaggi;

public class GiraRuotaMeraviglie implements ClientMessage {

    private static String id = "101";

    public GiraRuotaMeraviglie() {
    }

    @Override
    public int getId() {
        return Integer.parseInt(id);
    }

    public String tostring() {
        return id;
    }
}