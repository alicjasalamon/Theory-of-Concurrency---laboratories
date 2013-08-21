package lab2;

public class FilozofZakleszczajacy extends Thread {
	private int id;

	public FilozofZakleszczajacy(int id) {
		this.id = id;
	}

	public void run() {
		while (true) {
			try {

				System.out.println("" + this.id + " filozof rozmysla");

				MainZakleszczajacy.widelce[this.id - 1].opusc();
				MainZakleszczajacy.widelce[(this.id) % 5].opusc();
				System.out
						.println("" + this.id + " filozof mowi: mniam mniam!");
				Thread.sleep(10000);
				MainZakleszczajacy.widelce[(this.id) % 5].podnies();
				MainZakleszczajacy.widelce[this.id - 1].podnies();

				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}