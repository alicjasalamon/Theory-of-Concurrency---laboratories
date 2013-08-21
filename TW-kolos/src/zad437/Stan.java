package zad437;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Stan extends Thread {
	
	Lock lock = new ReentrantLock();
	Obsluga o;
	
	Stan(Obsluga ob)
	{
		o=ob;
	}
	
	public void run(){
		while(true){
			try {
				lock.lock();
				
				for(int i=0; i<o.drukuje.length; i++)
				System.out.print(o.drukuje[i]);
				System.out.println();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
			finally{
				lock.unlock();
			}
		}
	}

}
