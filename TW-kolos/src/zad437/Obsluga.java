package zad437;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Obsluga {
	
	ReentrantLock lock = new ReentrantLock();
	Condition a = lock.newCondition();

	int ileDrukarek=3;
	int[] drukuje = new int[ileDrukarek];
	
	static Queue<Integer> wolne = new ArrayDeque<Integer>();
	static Queue<Integer> zajete = new ArrayDeque<Integer>();
	
	Obsluga()
	{
		for(int i =0; i<ileDrukarek; i++)
			wolne.add(i);
	}
	
	int zajmij(int id) throws InterruptedException
	{
		int i=0;
		try{
			lock.lock();
			
			while(wolne.size()==0)
				a.await();
			
			i = wolne.poll();
			drukuje[i] = id;
			
			zajete.add(i);
			
		}
		finally{
			lock.unlock();
			return i;
		}
	}
	
	public void zwolnij(int i)
	{
		try{
			lock.lock();
			
			drukuje[i]=0;
			
			zajete.remove(i);
			wolne.add(i);
			a.signal();
		}
		finally{
			lock.unlock();
		}
	}
}
