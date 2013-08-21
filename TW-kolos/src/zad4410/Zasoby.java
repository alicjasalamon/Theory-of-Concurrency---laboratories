package zad4410;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Zasoby {
	
	int ileA = 3;
	int ileB = 5;
	
	int ileWKolejce1 =0;
	
	ReentrantLock lock = new ReentrantLock();
	Condition kolejka1 = lock.newCondition();
	Condition kolejka2 = lock.newCondition();
	
	public void przydziel1() throws InterruptedException
	{
		try{
			lock.lock();
			while(ileA==0)
			{	
				ileWKolejce1++;
				kolejka1.await();
				ileWKolejce1--;
			}
			System.out.println("proces 1 zjada A");
			ileA--;
		}
		finally{
			lock.unlock();
		}
	}
	
	public char przydziel2() throws InterruptedException
	{
		char ret='-';
		try{
			lock.lock();
			while(ileA==0 && ileB==0) kolejka2.await();
			
			if(ileA>0)
			{
				ileA--;
				System.out.println("proces 2 zjada A");
				ret = 'a';
			}
			else if(ileB>0)
			{
				ileB--;
				System.out.println("proces 2 zjada B");
				ret = 'b';
			}	
		}
		finally{
			lock.unlock();
		}
		return ret;

	}
	
	public char przydziel3() throws InterruptedException
	{
		char ret='-';
		try{
			lock.lock();
			
			if(ileB>0)
			{
				ileB--;
				System.out.println("proces 3 zjada B");
				ret='b';
			}
			else if(ileA>0)
			{
				ileA--;
				System.out.println("proces 3 zjada B");
				ret='a';
			}
		}
		finally{
			lock.unlock();
		}
		return ret;
	}

	public void zwolnij1()
	{
		try{
			lock.lock();
			
			System.out.println("proces 1 oddaje A");
			ileA--;
			
			if(ileWKolejce1>0)
				kolejka1.signal();
			else
				kolejka2.signal();
		}
		finally{
			lock.unlock();
		}
	}
	
	public void zwolnij2(char typ)
	{
		try{
			lock.lock();
			
			if(typ=='a')
			{
				System.out.println("proces 2 oddaje A");
				ileA++;
				if(ileWKolejce1>0)
					kolejka1.signal();
				else
					kolejka2.signal();
			}
			else
			{
				System.out.println("proces 2 oddaje B");
				ileB++;
				kolejka2.signal();
			}
				
		}
		finally{
			lock.unlock();
		}
	}
	
	public void zwolnij3(char typ)
	{
		try{
			lock.lock();
			if(typ=='a')
			{
				System.out.println("proces 3 oddaje A");
				ileA++;
				if(ileWKolejce1>0)
					kolejka1.signal();
				
				
			}
			else if(typ=='b')
			{
				System.out.println("proces 3 oddaje B");
				ileB++;
				kolejka2.signal();
			}
		}
		finally{
			lock.unlock();
		}
	}
}
