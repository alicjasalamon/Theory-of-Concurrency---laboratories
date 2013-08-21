package lab4;

public class Main {
	
	public static int sleep;
	public static double wynikiA[][]= new double[5][6];
	public static double ile=0;
	public static Integer watki=-1;
	public static Integer czasy=-1;
//	public static volatile boolean stop=true;
	
	public static void wypisz(){
		for(int i=0; i<5; i++){
			for(int j=0; j<6;j++)
				System.out.print(wynikiA[i][j] + "\t");
			System.out.println();
		}
	}
	
	public static void main(String[] args){		
		
		for(int iloscWatkow=10; iloscWatkow<51; iloscWatkow+=10)
		{
			watki++;
			for(int czasMetody=0; czasMetody<101; czasMetody+=20)
			{
				Main.ile=0;
				System.out.println("WATKI "+ iloscWatkow + " CZAS " + czasMetody);
				czasy++;
				sleep=czasMetody;
			
				int iloscProducentow =iloscWatkow/2, iloscKonsumetow=iloscWatkow/2;
				Bufor bufor = new Bufor();
				Thread producenci[];
				Thread konsumenci[];
				
				producenci = new Thread[iloscProducentow];
				konsumenci = new Thread[iloscKonsumetow];
				
				for(int i=0; i<iloscProducentow; ++i){
					producenci[i] = new Producent(bufor, i);
				}
		
				for(int i=0; i<iloscKonsumetow; ++i){
					konsumenci[i] = new Konsument(bufor, i);
				}
				
				for(int i=0; i<iloscProducentow; ++i){
					producenci[i].start();
				}
		
				for(int i=0; i<iloscKonsumetow; ++i){
					konsumenci[i].start();
				}
				
				try{
					//if(czasMetody==0) Thread.sleep(3000);
					Thread.sleep(60000);
				}catch(Exception e){}
					
				for(int i=0; i<iloscWatkow/2; ++i){
					producenci[i].interrupt();
					konsumenci[i].interrupt();
				}

				wypisz();
			}
			czasy=-1;
		}
		System.out.println();
		wypisz();
	}
	
}
