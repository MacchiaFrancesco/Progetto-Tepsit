package ruota.client.Messaggi;

public class LoginGiocatore implements ClientMessage {

    private static int id;
    private String nome;
    private int codice;
    
    public LoginGiocatore(String nome, int codice) {
        this.nome = nome;
        this.codice = codice;
        id = 000;
    }
    
    public String aggAsterischi(String string, int lunghezza) {
        while (string.length() < lunghezza) {
            string += "*";
        }
        return string;
    }

    public String getNome() {
        return nome;
    }

    public int getCodice() {
        return codice;
    }

    public static int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + ";" + aggAsterischi(nome, 20) + ";" + codice;
    }
}