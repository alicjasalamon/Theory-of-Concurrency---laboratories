
public class Main {


	public static double wynikiA[][]= new double[5][6];
	public static double wynikiB[][]= new double[5][6];
	public static Integer watki=-1;
	public static Integer czasy=-1;
	
	public static void wypisz(){
		for(int i=0; i<5; i++){
			for(int j=0; j<6;j++)
				System.out.print(wynikiA[i][j] + "\t");
			System.out.println();
		}
	}
	
	public static void wypisz2(){
		for(int i=0; i<5; i++){
			for(int j=0; j<6;j++)
				System.out.print(wynikiB[i][j] + "\t");
			System.out.println();
		}
	}
	
	public static void main(String[] args){		

		int bufferSize=20;
		Scheduler sched;

		
		for(int iloscWatkow=10; iloscWatkow<51; iloscWatkow+=10)
		{
			watki++;
			for(int czasMetody=0; czasMetody<101; czasMetody+=20)
			{
				czasy++;
				System.out.println("WATKI "+ iloscWatkow + " CZAS " + czasMetody);
				
				int howManyProducers =iloscWatkow/2, howManyConsumers=iloscWatkow/2;
				int mt=czasMetody;
				
				sched = new Scheduler(bufferSize, mt);
				sched.start();
		
				Proxy proxy = new Proxy(sched);
		
				Thread producenci[] = new Thread[howManyProducers];
				Thread konsumenci[] = new Thread[howManyConsumers];
				
				
				for(int i=0; i<howManyProducers; ++i){
					producenci[i] = new Producer(proxy, bufferSize/2);
				}
		
				for(int i=0; i<howManyConsumers; ++i){
					konsumenci[i] = new Consumer(proxy, bufferSize/2);
				}
			

				for(int i=0; i<howManyProducers; ++i){
					producenci[i].start();
				}
				
				for(int i=0; i<howManyConsumers; ++i){
					konsumenci[i].start();
				}
				
				try{
				if(mt==0) Thread.sleep(300);
				Thread.sleep(50*mt*iloscWatkow);
				}
				catch(Exception e){}
				
				for(int i=0; i<howManyProducers; ++i){
					producenci[i].interrupt();
					konsumenci[i].interrupt();
				}
				sched.interrupt();
				wypisz2();
				
			}
			czasy=-1;
		}
		System.out.println();
		System.out.println();
		wypisz();
		System.out.println();
		wypisz2();
	}

	
}
