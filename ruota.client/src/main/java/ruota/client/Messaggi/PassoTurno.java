package ruota.client.Messaggi;

public class PassoTurno implements ClientMessage {

    private static String id = "50";

    public PassoTurno() {
    }

    @Override
    public int getId() {
        return Integer.parseInt(id);
    }

    @Override
    public String toString() {
       
        return "50";
    }
}