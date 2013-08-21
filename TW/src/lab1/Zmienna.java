package lab1;

import lab2.SemaforBinarny;
import lab2.SemaforOgolny2;


public class Zmienna {

	public long a;
	public SemaforBinarny s;
	public SemaforOgolny2 so;

	Zmienna() {
		a = 0;
		s = new SemaforBinarny(true);
		so = new SemaforOgolny2(1);
	}

	long get() {
		return a;
	}

	long increment() throws InterruptedException {
		
		//s.opusc();
		so.opusc();
		long tmp = a;
		tmp = tmp + 1;
		a = tmp;
		//s.podnies();
		so.podnies();
		return a;
	}

}
