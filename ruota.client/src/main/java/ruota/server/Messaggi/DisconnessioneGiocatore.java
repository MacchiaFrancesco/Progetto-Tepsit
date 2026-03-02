package ruota.server.Messaggi;

public class DisconnessioneGiocatore implements ServerMessage {

    private static int id = 901;
    private int idGiocatore;
    private String nome;

    public DisconnessioneGiocatore(int idGiocatore, String nome) {
        this.idGiocatore = idGiocatore;
        this.nome = nome;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getIdGiocatore() {
        return idGiocatore;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        String messaggio = id + ";" + idGiocatore + ";" + nome;
        return messaggio;
    }
}