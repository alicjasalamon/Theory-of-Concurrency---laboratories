package lab6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Czytelnia {

	int ileCzyta =0;
	int ilePisze =0;
	int iloscMiejsc=2;
	int ileBudzicCzytelnikow = 2;
	
	ReentrantLock lock = new ReentrantLock();
	Condition c = lock.newCondition();
	Condition p = lock.newCondition();
	
	boolean ktosCzyta = false;
	boolean ktosPisze = false;
	boolean poPisarzuCzytelnik = true;
	
	
	public void poczatekCzytania() throws InterruptedException
	{
		try{
			lock.lock();
			ktosCzyta=true;
			System.out.println("zaczynam czytac");
			while(ktosPisze || ilePisze>0 || ileCzyta==iloscMiejsc)
				c.await();
			ileCzyta++;
		}
		finally{
			lock.unlock();
		}
	}
	
	public void koniecCzytania() 
	{
		try{
			lock.lock();
			ileCzyta--;
			System.out.println("koncze czytac");
			if(ileCzyta==0) 
				p.signal();
			ktosCzyta=false;
		}
		finally{
			lock.unlock();
		}
	}
	
	public void poczatekPisania() throws InterruptedException
	{
		try{
			lock.lock();
			System.out.println("zaczynam pisac");
			ktosPisze =true;
			while((ileCzyta + ilePisze)>0) 
				p.await();
			ilePisze =1;
			
		}
		finally{
			lock.unlock();
		}
	}
	
	public void koniecPisania()
	{
		try{
			lock.lock();
			ilePisze=0;
			
			System.out.println("pisarz konczy ");
			if(ktosCzyta) 
			{	poPisarzuCzytelnik = true;
				System.out.println("po mnie ma ");
			}
			if(ktosCzyta)
			{	
				c.signalAll();
			}
			else 
				p.signal();
			
			ileBudzicCzytelnikow =2;
			ktosPisze=false;
		}		
		finally{
			lock.unlock();
		}
	}
	
}
