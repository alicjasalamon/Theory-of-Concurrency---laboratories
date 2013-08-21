package lab5;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
	
	static ReentrantLock lock = new ReentrantLock();
	static Condition k = lock.newCondition();
	static Condition p = lock.newCondition();
	
	static Queue<Integer> puste = new ArrayDeque<Integer>();
	static Queue<Integer> pelne = new ArrayDeque<Integer>();
	
	static char[] dostepnosc=new char[Main.rozmiarBufora];
	
	static
	{
		for(int i=0; i<Main.rozmiarBufora; i++)
		{
			puste.add(i);
			dostepnosc[i]='-';
		}
	}
	
	synchronized static void wypiszStan() {
		System.out.println(dostepnosc);
	}
	
	public static int wstaw_poczatek() throws InterruptedException
	{
		int i;
		try{
			lock.lock();
			
			while(puste.size()==0)
				p.await();
				
			
			i=puste.poll();
			dostepnosc[i]='P';
	//		System.out.println("Wstawiamy na " + i);
		}
		
		finally{
			lock.unlock();
		}
		//wypiszStan();
		return i;


	}
	
	public static int pobierz_poczatek() throws InterruptedException
	{
		int i;
		try{
			lock.lock();
			while(pelne.size()==0)
				k.await();
				
			
			i=pelne.poll();
			dostepnosc[i]='K';
		//	System.out.println("Bierzemy z " + i);
		}
		
		finally{
			lock.unlock();
		}
		
		//wypiszStan();
		return i;
	}
	
	public static void wstaw_koniec(int i) throws InterruptedException
	{
		try{
			lock.lock();
			
			pelne.offer(i);
			dostepnosc[i]='*';
			
			k.signal();
			//p.signal();
			//System.out.println("Wstawilismy " + i);
		}
		
		finally{
			lock.unlock();
		}
		
		//wypiszStan();
	}
	
	public static void pobierz_koniec(int i)
	{
		try{
			lock.lock();
			
			puste.offer(i);
			dostepnosc[i]='-';
			
			//k.signal();
			p.signal();
			
//			System.out.println("Wzielismy " + i);
		}
		
		finally{
			lock.unlock();
		}
		//wypiszStan();
	}

}
