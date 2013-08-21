package lab2;

public class SemaforOgolny2 {
	
	private int zasoby;

	public SemaforOgolny2(int ileZasobow) {
		zasoby = ileZasobow;
	}

	synchronized public void opusc() throws InterruptedException {
		while (! (zasoby>0)) {
			wait();
		}
		zasoby--;
	}

	synchronized public void podnies() {
		zasoby++;
		notify();
	}
	
}
