package zad439;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Kelner {
	
	int iloscOsob;
	int przyStole=0;
	
	int czekajacyNaStol = 0;
	int czekajacyNaPare[];
	
	ReentrantLock lock = new ReentrantLock();
	Condition czekaNaStol = lock.newCondition();
	Condition czekaNaPare[];
		
	Kelner(int il)
	{
		iloscOsob = il;
		czekaNaPare = new Condition[il];
		czekajacyNaPare = new int[il];
		for(int i=0; i< il; i++)
		{
			czekaNaPare[i]= lock.newCondition();
			czekajacyNaPare[i]=0;
		}
		
	}
	
	void chceStolik(int j) throws InterruptedException
	{
		
		try{
			lock.lock();

			if(czekajacyNaPare[j]==0) 
			{
				czekajacyNaPare[j]++;
				while(czekajacyNaPare[j]!=2)
				{
					System.out.println("");
					czekaNaPare[j].await();
				}
				czekajacyNaPare[j]--;
				czekajacyNaPare[j]=0;
			}
			else
			{
				while(przyStole>0)
				{
					czekaNaStol.await();
				}
				czekajacyNaPare[j]++;
				przyStole=2;
				System.out.println("zasiadanie przy stoliku " +j);
				czekaNaPare[j].signal();
			}
		}
		finally{
			lock.unlock();
		}
	}
	
	void zwalaniam()
	{
		try{
			lock.lock();
			
			przyStole--;			
			System.out.println("zwalniam");
			if(przyStole==0)
				czekaNaStol.signal();
			
		}
		finally{
			lock.unlock();
		}
	}

}
