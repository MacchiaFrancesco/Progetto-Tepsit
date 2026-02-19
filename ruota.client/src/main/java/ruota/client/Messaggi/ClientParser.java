package ruota.client.Messaggi;

public class ClientParser implements ClientMessage{
	
	public static ClientMessage parse(String s) {
		String[] ps = s.split(";");
		ClientMessage msg = null;
		
		switch(Integer.parseInt(ps[0])) {
			case 001:
				msg = new ConfermaLogin(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]));
				break;
		
			case 002:
				msg = new ListaGiocatori(ps[1], ps[2], ps[3]); //cè da capire com farla
				break;
				
			case 004:
				msg = new InizioPartita(Integer.parseInt(ps[1]));
				break;
				
			case 010:
				msg = new InizioTurno(ps[1], ps[2]);
				break;
				
			case 011:
				msg = new StatoGiocatori(Integer.parseInt(ps[1]),); //anche qui capire cosa fare
				break;
				
			case 012:
				msg = new TimerTurno(Integer.parseInt(ps[1]));
				break;
				
			case 015:
				msg = new AnnuncioTurno(Integer.parseInt(ps[1]));
				break;
				
			case 021:
				msg = new RisultatoDellaRuota(Integer.parseInt(ps[1]));
				break;
				
			case 033:
				msg = new EsitoLettera(ps[1], Integer.parseInt(ps[2]), Integer.parseInt(ps[3]), ps[4], Integer.parseInt(ps[5]));
				break;
				
			case 041:
				msg = new SoluzioneCorretta(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]));
				break;
			
			case 042:
				msg = new AnnuncioFrase(ps[1]);
				break;
				
			case 051:
				msg = new CambioTurno(Integer.parseInt(ps[1]));
				break;
			
			case 052:
				msg = new FineRound(Integer.parseInt(ps[1], Integer.parseInt(ps[2])) //anche qui capire come fare
				break;
				
			case 100:
				msg = new InizioFaseFinale(Integer.parseInt(ps[1]));
				break;
				
			case 102:
				msg = new InizioMinigioco(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]));
				break;
				
			case 103:
				msg = new TimerMinigiochi(Integer.parseInt(ps[1]));
				break;
				
			case 105:
				msg = new PremioFinale(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]));
				break;
				
			case 106:
				msg = new VincitoreFinale(Integer.parseInt(ps[1]), ps[2], Integer.parseInt(ps[3]));
				break;
				
			case 901:
				msg = new DisconnessioneGiocatore(Integer.parseInt(ps[1]), ps[2]);
				break;
				
			case 902:
				msg = new AvvisoTimeout();
				break;
				
			case 904:
				msg = new Bancarotta(Integer.parseInt(ps[1]), Integer.parseInt(ps[2]));
				break;
			
			default:
			System.out.println("Messaggio non riconosciuto");
		}
		
		return msg;
	}
}
