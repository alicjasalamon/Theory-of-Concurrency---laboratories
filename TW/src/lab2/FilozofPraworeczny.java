package lab2;

public class FilozofPraworeczny extends Thread {
	private int id;

	public FilozofPraworeczny(int id) {
		this.id = id;
	}

	public void run() {
		while (true) {
			try {

				System.out.println("" + this.id + " filozof rozmysla");
				
				MainNiesymetryczny.widelce[(this.id - 1) %5].opusc();
				MainNiesymetryczny.widelce[(this.id) % 5].opusc();
				System.out
						.println("" + this.id + " filozof mowi: mniam mniam!");
				Thread.sleep(1000);
				MainNiesymetryczny.widelce[(this.id - 1) %5].podnies();
				MainNiesymetryczny.widelce[(this.id) % 5].podnies();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
