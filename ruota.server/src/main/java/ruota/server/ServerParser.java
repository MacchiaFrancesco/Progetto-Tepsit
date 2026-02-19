package ruota.server;

public class ServerParser {

    public static void parse(String s) {
        String[] ps = s.split(";");

        switch(Integer.parseInt(ps[0])) {
            case 000:
                InputHandler.loginGiocatore(ps[1]);
                break;

            case 003:
                InputHandler.inizioPartita(Integer.parseInt(ps[1]));
                break;

            case 020:
                InputHandler.girareRuota();
                break;

            case 030:
                InputHandler.letteraIndovinata(ps[1]);
                break;

            case 040:
                InputHandler.dareSoluzioneFrase(ps[1]);
                break;

            case 050:
                InputHandler.passoTurno();
                break;

            case 101:
                InputHandler.girareRuotaMeraviglie();
                break;

            case 104:
                InputHandler.selezioneBusta(Integer.parseInt(ps[1]));
                break;

            case 903:
                InputHandler.controlloConnessione(Long.parseLong(ps[1]));
                break;

            default:
                System.out.println("Messaggio non riconosciuto");
        }
    }
}