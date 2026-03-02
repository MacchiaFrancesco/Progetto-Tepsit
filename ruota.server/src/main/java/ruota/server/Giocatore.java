 package ruota.server;

public class Giocatore {

    private String username;
    private int punteggioTurno;
    private int punteggioPartita;
    //private Trasmissione trasmissione;
    private CodaCircolare codaRicezione;
    private CodaCircolare codaTrasmissione;
    
    public Giocatore(String username, CodaCircolare codaTrasmissione, CodaCircolare codaRicezione) { //Trasmissione trasmissione
        this.username = username;
        this.codaRicezione = codaRicezione;
        this.codaTrasmissione = codaTrasmissione;
        //this.trasmissione = trasmissione;
        this.punteggioPartita = 0;
        this.punteggioPartita=0;
    }

    //Username
    public String getUsername() {
        return username;
    }

    //Punteggio Turno
    public int getPunteggioTurno() {
        return punteggioTurno;
    }

    public void aggiungiPunteggioTurno(int punti) {
        this.punteggioTurno += punti;
    }

    public void resetPunteggio() {
        this.punteggioTurno = 0;
    }

    //Punteggio Partita 
    public int getPunteggioPartita() {
        return punteggioPartita;
    }

    public void aggiungiPunteggioPartita(int punti) {
        this.punteggioPartita += punti;
    }

    public void resetPunteggioPartita() {
        this.punteggioPartita = 0;
    }

    //Trasmissione
//    public Trasmissione getTrasmissione() {
//        return trasmissione;
//    }
    
    public CodaCircolare getCodaRicezione() {
        return codaRicezione;
    }
    
    public void setCodaRicezione(CodaCircolare codaRicezione) {
        this.codaRicezione = codaRicezione;
    }
    
    public CodaCircolare getCodaTrasmissione() {
    	return codaTrasmissione;
    }
    
    public void setCodaTrasmissione(CodaCircolare codaTrasmissione) {
    	this.codaTrasmissione = codaTrasmissione;
    }
}

