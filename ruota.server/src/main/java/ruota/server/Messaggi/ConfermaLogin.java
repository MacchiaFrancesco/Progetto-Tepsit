package ruota.server.Messaggi;

public class ConfermaLogin implements ServerMessage {

    private static String id = "1";
    private int idAss;
    private boolean esito;

    public ConfermaLogin(int idAss, boolean esito) {
        this.idAss = idAss;
        this.esito = esito;
    }

    @Override
    public int getId() {
        return Integer.parseInt(id);
    }

    public int getIdAss() {
        return idAss;
    }

    public boolean isEsito() {
        return esito;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + idAss + ";" + esito;
        return messaggio;
    }
}