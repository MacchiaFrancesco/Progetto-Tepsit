package ruota.server.Messaggi;

public class EsitoLettera implements ServerMessage {

    private static int id = 33;
    private String lettera;
    private boolean presente;
    private int volte;
    private String fraseParziale;
    private int soldiGuadagnati;

    public EsitoLettera(String lettera, boolean presente, int volte, String fraseParziale, int soldiGuadagnati) {
        this.lettera = lettera;
        this.presente = presente;
        this.volte = volte;
        this.fraseParziale = fraseParziale;
        this.soldiGuadagnati = soldiGuadagnati;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getLettera() {
        return lettera;
    }

    public boolean isPresente() {
        return presente;
    }

    public int getVolte() {
        return volte;
    }

    public String getFraseParziale() {
        return fraseParziale;
    }

    public int getSoldiGuadagnati() {
        return soldiGuadagnati;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + lettera + ";" + presente + ";" + volte + ";" + fraseParziale + ";" + soldiGuadagnati;
        return messaggio;
    }
}