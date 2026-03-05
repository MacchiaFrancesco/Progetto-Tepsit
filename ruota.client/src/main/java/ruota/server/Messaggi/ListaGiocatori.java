package ruota.server.Messaggi;

public class ListaGiocatori implements ServerMessage {

    private static String id = "2";
    private int nPlayer;
    private String[] nomePlayer;

    public ListaGiocatori(int nPlayer, String[] nomePlayer) {
        this.nPlayer = nPlayer;
        this.nomePlayer = nomePlayer;
    }

    @Override
    public int getId() {
        return Integer.parseInt(id);
    }

    public int getNPlayer() {
        return nPlayer;
    }

    public String[] getNomePlayer() {
        return nomePlayer;
    }

    @Override
    public String toString() {
        String messaggio = id;
        for (int i = 0; i < nPlayer; i++) {
            messaggio += ";" + nomePlayer[i];
        }
        return messaggio;
    }
}