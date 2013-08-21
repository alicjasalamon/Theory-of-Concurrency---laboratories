package lab2;

public class SemaforBinarny {
	
	private boolean podniesiony;

	public SemaforBinarny(boolean czyPodniesiony) {
		podniesiony = czyPodniesiony;
	}

	synchronized public void opusc() throws InterruptedException {
		while (!podniesiony) {
			wait();
		}
		podniesiony = false;
	}

	synchronized public void podnies() {
		podniesiony = true;
		notify();
	}
	
}