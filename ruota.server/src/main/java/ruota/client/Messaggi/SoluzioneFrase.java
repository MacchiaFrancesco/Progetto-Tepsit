package ruota.client.Messaggi;

public class SoluzioneFrase implements ClientMessage {

    private static int id = 40;
    private String frase;

    public SoluzioneFrase(String frase) {
        this.frase = frase;
    }

    public String getFrase() {
        return frase;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + ";" + ClientMessage.aggAsterischi(frase, 52);
    }
}