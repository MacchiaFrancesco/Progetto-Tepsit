package ruota.server;
import ruota.client.Messaggi.*;

public class ServerParser {

    public static ClientMessage parse(String s) {
        String[] ps = s.split(";");
        ClientMessage messaggioDaClient = null;

        switch(Integer.parseInt(ps[0])) {
            case 000:
                messaggioDaClient = new LoginGiocatore(ps[1], Integer.parseInt(ps[2]));
                break;

            case 003:
                messaggioDaClient = new InizioPartita(Integer.parseInt(ps[1]));
                break;

            case 020:
                messaggioDaClient = new GiraRuota();
                break;

            case 030:
                messaggioDaClient = new LetteraIndovinata(ps[1]);
                break;

            case 040:
                messaggioDaClient = new SoluzioneFrase(ps[1]);
                break;

            case 050:
                messaggioDaClient = new PassoTurno();
                break;

            case 101:
                messaggioDaClient = new GiraRuotaMeraviglie();
                break;

            case 104:
                messaggioDaClient = new SelezioneBusta(Integer.parseInt(ps[1]));
                break;

            case 903:
                //messaggioDaClient = new ControlloConnessione(Long.parseLong(ps[1]));
                break;

            case 999:
                //messaggioDaClient = new Test();
            	System.out.println("Server Parser: il test ha avuto successo");
                break;

            default:
                System.out.println("Server Parser: Messaggio non riconosciuto");
                break;
        }

        return messaggioDaClient;
    }
}