package ruota.client;
import ruota.server.Messaggi.*;

public class ClientParser {

    public static ServerMessage parse(String s) {
        String[] ps = s.split(";");
        ServerMessage messaggioDaServer = null;

        switch(Integer.parseInt(ps[0])) { //il campo in posizione 0 e' l'id del messaggio
            case 001:
                messaggioDaServer = new ConfermaLogin(Integer.parseInt(ps[1]), Boolean.parseBoolean(ps[2]));
                break;

            case 002:
                String[] lista = new String[Integer.parseInt(ps[1])];
                for (int i = 0; i < Integer.parseInt(ps[1]); i++) {
                    lista[i] = ps[i+2]; //2 e' l'offset per raggiungere i nomi
                }
                messaggioDaServer = new ListaGiocatori(Integer.parseInt(ps[1]), lista);
                break;

            case 004:
                messaggioDaServer = new InizioPartita(Integer.parseInt(ps[1]));
                break;

            case 010:
                messaggioDaServer = new InizioTurno(ps[1], ps[2]);
                break;

            case 011:
                int nGiocatori = Integer.parseInt(ps[1]);
                int[] idGiocatori = new int[nGiocatori];
                int[] salvadanaioGiocatori = new int[nGiocatori];
                int[] soldiTurnoGiocatori = new int[nGiocatori];

                for (int i = 0; i < nGiocatori; i++) { //i vari campi hanno un offset di 3 tra di loro quindi lo aggiungo per ogni iterazione
                    idGiocatori[i] = Integer.parseInt(ps[2+(i*3)]);
                    salvadanaioGiocatori[i] = Integer.parseInt(ps[3+(i*3)]);
                    soldiTurnoGiocatori[i] = Integer.parseInt(ps[4+(i*3)]);
                }
                messaggioDaServer = new StatoGiocatore(nGiocatori, idGiocatori, salvadanaioGiocatori, soldiTurnoGiocatori);
                break;

            case 012:
                messaggioDaServer = new TimerTurno(Integer.parseInt(ps[1]));
                break;

            case 015:
                messaggioDaServer = new AnnuncioTurno(Integer.parseInt(ps[1]));
                break;

            case 021:
                messaggioDaServer = new RisultatoRuota(Integer.parseInt(ps[1]));
                break;

            case 033:
                messaggioDaServer = new EsitoLettera(ps[1], Integer.parseInt(ps[2]), Integer.parseInt(ps[3]), ps[4], Integer.parseInt(ps[5]));
                break;

            case 041:
                messaggioDaServer = new SoluzioneCorretta(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]));
                break;

            case 042:
                messaggioDaServer = new AnnuncioFrase(ps[1]);
                break;

            case 051:
                messaggioDaServer = new CambioTurno(Integer.parseInt(ps[1]));
                break;

            case 052:
            	int[] soldiGiocatore = new int[Integer.parseInt(ps[3])];
            	 for (int i = 0; i < Integer.parseInt(ps[3]); i++) { //i vari campi hanno un offset di 2 tra di loro quindi lo aggiungo per ogni iterazione
            		 soldiGiocatore[i] = Integer.parseInt(ps[5+(i*2)]);
                 }
                messaggioDaServer = new FineRound(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]), Integer.parseInt(ps[3]), soldiGiocatore);
                break;

            case 100:
                messaggioDaServer = new InizioFaseFinale(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]));
                break;

            case 102:
                messaggioDaServer = new InizioMinigioco(Integer.parseInt(ps[1]));
                break;

            case 103:
                messaggioDaServer = new TimerMinigiochi(Integer.parseInt(ps[1]));
                break;

            case 105:
                messaggioDaServer = new PremioFinale(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]));
                break;

            case 106:
                messaggioDaServer = new VincitoreFinale(Integer.parseInt(ps[1]), ps[2], Integer.parseInt(ps[3]));
                break;

            case 901:
                messaggioDaServer = new DisconnessioneGiocatore(Integer.parseInt(ps[1]), ps[2]);
                break;

            case 902:
                messaggioDaServer = new AvvisoTimeOut();
                break;

            case 904:
                messaggioDaServer = new Bancarotta(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]));
                break;

            default:
                System.out.println("Messaggio non riconosciuto");
                break;
        }

        return messaggioDaServer;
    }
}