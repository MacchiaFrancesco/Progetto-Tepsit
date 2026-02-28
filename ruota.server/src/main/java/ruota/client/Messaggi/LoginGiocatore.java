package ruota.client.Messaggi;

public class LoginGiocatore implements ClientMessage{
	private static int id;
	private String nome;
	
	public LoginGiocatore(String nome) {
		this.nome=nome;
		id=000;
	}
	
	public String aggAsterischi(String string, int lunghezza) {
		while (string.length() < lunghezza) {
			string+= "*";
		}
		return string;
	}
	
	public String tostring() {
		String messaggio = id+";"+aggAsterischi(nome, 20);
		return messaggio;
	}
}
