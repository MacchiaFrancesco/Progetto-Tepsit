package ruota.client;

public class ClientParser{

    public static void parse(String s) {
        String[] ps = s.split(";");

        switch(Integer.parseInt(ps[0])) { //il campo in posizione 0 e' l'id del messaggio
            case 001:
                InputHandler.confermaLogin(Integer.parseInt(ps[1]), Boolean.parseBoolean(ps[2]));
                break;

            case 002:
            	String[] lista = new String[Integer.parseInt(ps[1])];
            	for (int i = 0; i < Integer.parseInt(ps[1]); i++) {
            		lista[i] = ps[i+2]; //2 e' l'offset per raggiungere i nomi;
            	}
                InputHandler.listaGiocatori(Integer.parseInt(ps[1]), lista); 
                break;

            case 004:
                InputHandler.inizioPartita(Integer.parseInt(ps[1]));
                break;

            case 010:
                InputHandler.inizioTurno(ps[1], ps[2]);
                break;

            case 011:
            	int nGiocatori =  Integer.parseInt(ps[1]);
            	int [] idGiocatori = new int[nGiocatori];
            	int[] salvadanaioGiocatori = new int[nGiocatori];
            	int[] soldiTurnoGiocatori = new int[nGiocatori];
            	
            	for (int i = 0; i < nGiocatori; i++) { //i vari campi hanno un offset di 3 tra di loro quindi lo aggiungo per ogni iterazione
            		idGiocatori[i] = Integer.parseInt(ps[2+(i*3)]);   
            		salvadanaioGiocatori[i] = Integer.parseInt(ps[3+(i*3)]);
            		soldiTurnoGiocatori[i] = Integer.parseInt(ps[4+(i*3)]);
            	}
                InputHandler.statoGiocatori(nGiocatori, idGiocatori, salvadanaioGiocatori, soldiTurnoGiocatori); // anche qui capire cosa fare
                break;

            case 012:
                InputHandler.timerTurno(Integer.parseInt(ps[1]));
                break;

            case 015:
                InputHandler.annuncioTurno(Integer.parseInt(ps[1]));
                break;

            case 021:
                InputHandler.risultatoDellaRuota(Integer.parseInt(ps[1]));
                break;

            case 033:
                InputHandler.esitoLettera(ps[1], Boolean.parseBoolean(ps[2]), Integer.parseInt(ps[3]), ps[4], Integer.parseInt(ps[5]));
                break;

            case 041:
                InputHandler.soluzioneCorretta(Boolean.parseBoolean(ps[1]), Integer.parseInt(ps[2]));
                break;

            case 042:
                InputHandler.annuncioFrase(ps[1]);
                break;

            case 051:
                InputHandler.cambioTurno(Integer.parseInt(ps[1]));
                break;

            case 052:
                InputHandler.fineRound(Integer.parseInt(ps[1]), Integer.parseInt(ps[2])); // anche qui capire come fare
                break;

            case 100:
                InputHandler.inizioFaseFinale(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]));
                break;

            case 102:
                InputHandler.inizioMinigioco(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]));
                break;

            case 103:
                InputHandler.timerMinigiochi(Integer.parseInt(ps[1]));
                break;

            case 105:
                InputHandler.premioFinale(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]));
                break;

            case 106:
                InputHandler.vincitoreFinale(Integer.parseInt(ps[1]), ps[2], Integer.parseInt(ps[3]));
                break;

            case 901:
                InputHandler.disconnessioneGiocatore(Integer.parseInt(ps[1]), ps[2]);
                break;

            case 902:
                InputHandler.avvisoTimeout();
                break;

            case 904:
                InputHandler.bancarotta(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]));
                break;

            default:
                System.out.println("Messaggio non riconosciuto");
        }
    }
}