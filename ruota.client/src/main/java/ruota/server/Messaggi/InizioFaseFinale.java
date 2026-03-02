package ruota.server.Messaggi;

public class InizioFaseFinale implements ServerMessage {

    private static int id = 100;
    private int idVincitore;
    private int soldi;

    public InizioFaseFinale(int idVincitore, int soldi) {
        this.idVincitore = idVincitore;
        this.soldi = soldi;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getIdVincitore() {
        return idVincitore;
    }

    public int getSoldi() {
        return soldi;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + idVincitore + ";" + soldi;
        return messaggio;
    }
}