package ruota.server.Messaggi;

public class AvvisoTimeOut implements ServerMessage {

    private static int id = 902;

    public AvvisoTimeOut() {
    }

    @Override
    public int getId() {
        return id;
    }

    public String tostring() {
        return String.valueOf(id);
    }
}