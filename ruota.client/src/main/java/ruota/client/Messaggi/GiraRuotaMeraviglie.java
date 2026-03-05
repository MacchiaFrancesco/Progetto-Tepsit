package ruota.client.Messaggi;

public class GiraRuotaMeraviglie implements ClientMessage {

    private static String id = "101";

    public GiraRuotaMeraviglie() {
    }

    @Override
    public int getId() {
        return Integer.parseInt(id);
    }

    @Override
    public String toString() {
        return id;
    }
}