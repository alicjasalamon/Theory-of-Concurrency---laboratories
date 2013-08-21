package zad4410;

public class Main {

public static void main(String[] args) throws InterruptedException{		
		
		int iloscProcesow1 = 5;
		int iloscProcesow2 = 8;
		int iloscProcesow3 = 4;
		
		Thread procesy1[] = new Thread[iloscProcesow1];
		Thread procesy2[] = new Thread[iloscProcesow2];
		Thread procesy3[] = new Thread[iloscProcesow3];
		
		Zasoby zasoby = new Zasoby();

		for(int i=0; i<iloscProcesow1; ++i){
			procesy1[i] = new Proces1(zasoby);
		}

		for(int i=0; i<iloscProcesow2; ++i){
			procesy2[i] = new Proces2(zasoby);
		}
		
		for(int i=0; i<iloscProcesow3; ++i){
			procesy3[i] = new Proces3(zasoby);
		}

		for(int i=0; i<iloscProcesow1; ++i){
			procesy1[i].start();
		}
		
		for(int i=0; i<iloscProcesow2; ++i){
			procesy2[i].start();
		}
		
		for(int i=0; i<iloscProcesow3; ++i){
			procesy3[i].start();
		}
		
		for(int i=0; i<iloscProcesow1; ++i){
			procesy1[i].join();
		}
		
		for(int i=0; i<iloscProcesow2; ++i){
			procesy2[i].join();
		}
		
		for(int i=0; i<iloscProcesow3; ++i){
			procesy3[i].join();
		}
	}
}
