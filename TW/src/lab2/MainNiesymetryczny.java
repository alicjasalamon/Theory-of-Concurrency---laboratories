package lab2;

public class MainNiesymetryczny{

	public static SemaforBinarny widelce[];
	public static Thread filozofowie[];
	
	public static void main(String[] argv) throws InterruptedException{
			
		widelce = new SemaforBinarny[5];
		for(int i=0; i<5; ++i){
			widelce[i] = new SemaforBinarny(true);
		}
	
		filozofowie = new Thread[5];
		for(int i=0; i<4; i++){
			filozofowie[i] = new FilozofPraworeczny(i+1);
		}
		filozofowie[4]= new FilozofLeworeczny(5);
		
		for(int i=0; i<5; ++i){
			filozofowie[i].start();
		}

		for (int i=0; i<5; ++i){
			filozofowie[i].join();
		}	
	}
}
