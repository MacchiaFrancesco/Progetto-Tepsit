package ruota.server.Messaggi;

public class AnnuncioFrase implements ServerMessage {

    private static int id = 42;
    private String frase;

    public AnnuncioFrase(String frase) {
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
        return id + ";" + ServerMessage.aggAsterischi(frase, 52);
    }
}