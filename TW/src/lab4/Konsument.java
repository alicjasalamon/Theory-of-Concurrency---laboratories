package lab4;


public class Konsument extends Thread{
	
	Bufor bufor;
	int id;
	long timeA, timeB;
	
	public Konsument(Bufor b, int i){
		bufor=b;
		id=i;
	}
	
	public void loguj()
	{
		synchronized (Main.class) {
			Main.ile++;
			Main.wynikiA[Main.watki][Main.czasy]+=( (timeB-timeA)-(Main.wynikiA[Main.watki][Main.czasy]))/Main.ile;
		}
	}
	public void run(){
		try{
			while(true){
				timeA = System.nanoTime();
				bufor.konsumuj(id);
				timeB= System.nanoTime();
				loguj();	
			}
		}catch(Exception e){}
	}
}

 