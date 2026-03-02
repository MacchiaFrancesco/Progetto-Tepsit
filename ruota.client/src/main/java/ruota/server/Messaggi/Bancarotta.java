package ruota.server.Messaggi;

public class Bancarotta implements ServerMessage {

    private static int id = 904;
    private int idGiocatore;
    private int soldiPersi;

    public Bancarotta(int idGiocatore, int soldiPersi) {
        this.idGiocatore = idGiocatore;
        this.soldiPersi = soldiPersi;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getIdGiocatore() {
        return idGiocatore;
    }

    public int getSoldiPersi() {
        return soldiPersi;
    }

    @Override
    public String toString() {
        return id + ";" + idGiocatore + ";" + soldiPersi;
    }
}