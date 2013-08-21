package lab3;

public class Konsument extends Thread{
	
	Bufor bufor;
	int id;
	
	public Konsument(Bufor b, int i){
		bufor=b;
		id=i;
	}
	
	public void run(){
		while(true){
			try {
				bufor.konsumuj(id);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
}

 