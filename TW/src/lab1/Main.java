package lab1;

public class Main {

	public static void main(String args[]) {
		for (int i = 0; i < 10000000; i++) {

			Zmienna zm = new Zmienna();
			Thread a = new Thread1(zm);
			Thread b = new Thread1(zm);

			a.start();
			b.start();

			try {
				a.join();
				b.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("wartosc " + zm.get());
		}
	}
}
