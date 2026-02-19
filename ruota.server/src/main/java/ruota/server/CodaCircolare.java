package ruota.server;

//import ruota.server.Messaggi.ServerMessage;

//coda: socket -> coda -> client
public class CodaCircolare {
	
	private final String[] buffer;
	private int head = 0;
	private int tail = 0;
	private int count = 0;
	
	public CodaCircolare(int size) {
	    buffer = new String[size];
	}
	
	//thread che legge dal socket
	public synchronized void preleva(String messaggio) throws InterruptedException {
	    while (count == buffer.length) { //guardia
	        wait();
	    }
	    buffer[tail] = messaggio;
	    tail = (tail + 1) % buffer.length;
	    count++;
	    notifyAll();
	}
	
	//thread che invia al client
	public synchronized String invio() throws InterruptedException {
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
