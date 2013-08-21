package lab3;

public class Producent extends Thread{
	
	Bufor bufor;
	int id;
	
	public Producent(Bufor b, int i){
		bufor = b;
		id=i;
	}
	
	public void run(){
		while(true){
			
			try {
				bufor.produkuj(id);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

 