package lab4;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bufor {

	int rozmiarBufora = 20;
	int ile=-1;
	Random rand = new Random();
	
	boolean czyJestProducent = false;
	boolean czyJestKonsument = false;

	private final static ReentrantLock lock = new ReentrantLock(true);
    final Condition pierszwyKon = lock.newCondition();
    final Condition resztaKon = lock.newCondition();    
    final Condition pierszwyProd = lock.newCondition();
    final Condition resztaProd = lock.newCondition();

	public void konsumuj(int id)
	{
		try{
			lock.lock();	
			while(czyJestKonsument)
				resztaKon.await();
			
			czyJestKonsument=true;
			int n = rand.nextInt(rozmiarBufora/2);
			
			while(ile<n)
				pierszwyKon.await();
			
			ile -= n;

			Thread.sleep(Main.sleep);
			
			czyJestKonsument = false;
			resztaKon.signal();
			pierszwyProd.signal();
			
		}catch(Exception e){}
		finally{
			lock.unlock();
		}
	}
	
	public void produkuj(int id)
	{
		try{
			lock.lock();
			while(czyJestProducent) resztaProd.await();
			
			czyJestProducent=true;

			int n = rand.nextInt(rozmiarBufora/2);
			while(rozmiarBufora -ile <n) pierszwyProd.await();
						
			ile += n;
			Thread.sleep(Main.sleep);
			
			czyJestProducent=false;

			resztaProd.signal();
			pierszwyKon.signal();
		}catch(Exception e){}
		finally{
			lock.unlock();
		}
	}
	
	
}
