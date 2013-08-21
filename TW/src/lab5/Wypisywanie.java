package lab5;

public class Wypisywanie extends Thread {


	
	public void run(){
		while(true){
			try {
				Monitor.lock.lock();
				Monitor.wypiszStan();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
			finally{
				Monitor.lock.unlock();
			}
		}
	}

}
