package ruota.client;

//import ruota.client.Messaggi.ClientMessage;

// ne usiamo 2, una per client->coda->socket una per socket->coda->client
public class CodaCircolare{
	
	private final String[] buffer;
	private int head = 0;
	private int tail = 0;
	private int count = 0; //Numero di celle occupate
	
	public CodaCircolare(int size) {
		buffer = new String[size];
	}
	
	public synchronized void inserisci(String messaggio) throws InterruptedException {
			while (count == buffer.length) { //guardia
				wait();
			}
			
			buffer[tail] = messaggio;
			tail = (tail + 1) % buffer.length;
			count++;
			notifyAll();
	}
	
		public synchronized String preleva() throws InterruptedException {
		while (count == 0) { //guardia
			wait();
	}
		String messaggio = buffer[head];
		head = (head + 1) % buffer.length;
		count--;
		notifyAll();
		return messaggio;
	}
}
