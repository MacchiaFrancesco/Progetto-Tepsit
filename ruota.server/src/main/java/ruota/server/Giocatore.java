package ruota.server;

public class Giocatore {

    private String username;
    private int punteggio;
    private Trasmissione trasmissione;
    private InputHandler inputHandler;
    private CodaCircolare codaRicezione;
    
    public Giocatore(String username, Trasmissione trasmissione, InputHandler inputHandler) {
        this.username = username;
        this.trasmissione = trasmissione;
        this.inputHandler = inputHandler;
        this.punteggio = 0;
    }

    //Username
    public String getUsername() {
        return username;
    }

    //Punteggio
    public int getPunteggio() {
        return punteggio;
    }

    public void aggiungiPunteggio(int punti) {
        this.punteggio += punti;
    }

    public void resetPunteggio() {
        this.punteggio = 0;
    }

    //Trasmissione
    public Trasmissione getTrasmissione() {
        return trasmissione;
    }

    //Input
    public InputHandler getInputHandler() {
        return inputHandler;
    }
    
    public CodaCircolare getCodaRicezione() {
        return codaRicezione;
    }
    
    public void setCodaRicezione(CodaCircolare codaRicezione) {
        this.codaRicezione = codaRicezione;
    }
}

