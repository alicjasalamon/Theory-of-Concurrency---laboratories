package lab2;


public class MainZakleszczajacy{

	public static SemaforBinarny widelce[];
	public static Thread filozofowie[];
	
	public static void main(String[] argv) throws InterruptedException{
		
		
		widelce = new SemaforBinarny[5];
		for(int i=0; i<5; ++i){
			widelce[i] = new SemaforBinarny(true);
		}
		
		filozofowie = new Thread[5];
		for(int i=0; i<5; ++i){
			filozofowie[i] = new FilozofZakleszczajacy(i+1);
		}
		
		for(int i=0; i<5; ++i){
			filozofowie[i].start();
		}

		for (int i=0; i<5; ++i){
			filozofowie[i].join();
		}	
	}
}