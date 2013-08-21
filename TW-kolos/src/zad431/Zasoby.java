package zad431;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Zasoby {
	
	int ileA = 3;
	int ileB = 5;
	
	ReentrantLock lock = new ReentrantLock();
	Condition a = lock.newCondition();
	Condition b = lock.newCondition();
	Condition ab = lock.newCondition();
	
	boolean rezA =false, rezB=false;
	
	public void wezZasobA() throws InterruptedException
	{
		try{
			lock.lock();
			
			while(ileA<=0 || (lock.hasWaiters(ab) && (rezA==false)))
				a.await();
			
			ileA--;
			System.out.println("proces bierze z A zasoby: " + ileA + " " + ileB);	
		}
		finally{
			lock.unlock();
		}
	}
	
	public void wezZasobB() throws InterruptedException
	{
		try{
			lock.lock();
			
			while(ileB<=0 || (lock.hasWaiters(ab) && (rezB==false)))
				b.await();
				
			ileB--;
			System.out.println("proces bierze z B zasoby: " + ileA + " " + ileB);	
				
		}
		finally{
			lock.unlock();
		}
	}
	
	public void wezZasobAB() throws InterruptedException
	{
		try{
			lock.lock();
			
			if(ileA*ileB==0)
			{
				if(! lock.hasWaiters(ab))
				{
					rezA = (ileA>0);
					rezB = (ileB>0);
					ab.await();
					
				}
			}
			
			ileA--;
			ileB--;
			System.out.println("proces bierze z A i B zasoby: " + ileA + " " + ileB);	
			
			if(lock.hasWaiters(ab))
			{
				rezA = (ileA>0);
				rezB = (ileB>0);
			}

		}
		finally{
			lock.unlock();
		}
	}

	public void zwolnijZasobA()
	{
		try{
			lock.lock();
			
			ileA++;
			System.out.println("proces oddaje A zasoby: " + ileA + " " + ileB);	
	
			if(lock.hasWaiters(ab) && !rezA)
			{
				if(rezB)
					ab.signal();
				else
					rezA=true;
			}
			else
			{
				a.signal();
			}
		}
		finally{
			lock.unlock();
		}
	}
	
	public void zwolnijZasobB()
	{
		try{
			lock.lock();
			
			ileB++;
			System.out.println("proces oddaje B zasoby: " + ileA + " " + ileB);	

			if(lock.hasWaiters(ab) && !rezB)
			{
				if(rezA)
					ab.signal();
				else
					rezB=true;
			}
			else
			{
				b.signal();
			}
		}
		finally{
			lock.unlock();
		}
	}
	
	public void zwolnijZasobAB()
	{
		try{
			lock.lock();
			
			ileA++;
			ileB++;
			
			System.out.println("proces oddaje A i B zasoby: " + ileA + " " + ileB);	

			if(! lock.hasWaiters(a) && ! lock.hasWaiters(b))
					ab.signal();
			else
			{
				a.signal();
				b.signal();
			}
		}
		finally{
			lock.unlock();
		}
	}
}
