package lab1;

public class Thread1 extends Thread {

	Zmienna a;

	Thread1(Zmienna zm) {
		a = zm;
	}

	public void run() {
		for (long i = 0; i < 500000; i++)
			try {
				a.increment();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
