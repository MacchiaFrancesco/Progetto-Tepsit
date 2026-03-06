package ruota.server;
import ruota.client.Messaggi.*;

public class ServerParser {

	public static ClientMessage parse(String s) {
		s = s.trim();   // 🔥 IMPORTANTISSIMO
//	    System.out.println("Server Parser: Parsing messaggio: " + s);

	    try {

	        String[] ps = s.split(";");
	        int id = Integer.parseInt(ps[0]);
//	        System.out.println("ID PARSATO: " + id);
	        switch(id) {

	            case 0:
	                return new LoginGiocatore(ps[1], Integer.parseInt(ps[2]));

	            case 3:
	                return new InizioPartitaClient(Integer.parseInt(ps[1]));

	            case 20:
//	            	System.out.println("case 20 entrato");
	                return new GiraRuota();

	            case 30:
	                return new LetteraIndovinata(ps[1]);

	            case 40:
	                return new SoluzioneFrase(ps[1]);

	            case 50:
	                return new PassoTurno();

	            case 101:
	                return new GiraRuotaMeraviglie();

	            case 104:
	                return new SelezioneBusta(Integer.parseInt(ps[1]));

	            default:
	                System.out.println("Messaggio non riconosciuto: " + s);
	                return null;
	        }
	        
	       

	    } catch(Exception e) {

	        System.out.println("ERRORE PARSING MESSAGGIO: " + s);
	        return null;

	    }
	}
    
}