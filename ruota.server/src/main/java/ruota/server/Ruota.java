package ruota.server;
import java.util.Random;

public class Ruota {
	private int[] ruota = {	200, 350, 100, 200, -1, 300, 150, 400, 250, 500, 
							-2, 200, 300, 150, 100, 200, -1, 250, 100, 500, 
							1000, 400, -2, 2000
							};
	
	private Random rand;
	public Ruota() {
		this.rand = new Random();
	}
	
	public int giraRuota() {
		return ruota[rand.nextInt(ruota.length)];
	}
}
