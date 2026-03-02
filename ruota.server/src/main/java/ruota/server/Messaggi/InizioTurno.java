package ruota.server.Messaggi;

public class InizioTurno implements ServerMessage {

    private static String id = "10";
    private String frase;
    private String contesto;

    public InizioTurno(String frase, String contesto) {
        this.contesto = contesto;
        this.frase = frase;
    }

    @Override
    public int getId() {
        return Integer.parseInt(id);
    }

    public String getFrase() {
        return frase;
    }

    public String getContesto() {
        return contesto;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + ServerMessage.aggAsterischi(frase, 52) + ";" + ServerMessage.aggAsterischi(contesto, 30);
        return messaggio;
    }
}