package ruota.server.Messaggi;

public class PremioFinale implements ServerMessage {

    private static int id = 105;
    private int importoBusta;
    private int soldiVinti;

    public PremioFinale(int importoBusta, int soldiVinti) {
        this.soldiVinti = soldiVinti;
        this.importoBusta = importoBusta;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getImportoBusta() {
        return importoBusta;
    }

    public int getSoldiVinti() {
        return soldiVinti;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + importoBusta + ";" + soldiVinti;
        return messaggio;
    }
}