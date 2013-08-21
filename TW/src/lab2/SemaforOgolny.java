package lab2;

public class SemaforOgolny {
	private int iloscOwieczek;
	private SemaforBinarny owieczki;		//moje zasoby
	private SemaforBinarny licznikOwieczek; //licznik zasobow
	
	public SemaforOgolny(int wartoscPoczatkowa) {
		iloscOwieczek = wartoscPoczatkowa;
		if(wartoscPoczatkowa > 0) owieczki = new SemaforBinarny(true);
		else owieczki = new SemaforBinarny(false);
		licznikOwieczek = new SemaforBinarny(true); //broni dostepu do licznika
	}
	
	public void opusc() throws InterruptedException{
		owieczki.opusc();
		licznikOwieczek.opusc();
		iloscOwieczek--;
		if(iloscOwieczek > 0) owieczki.podnies();
		licznikOwieczek.podnies();
	}			
	
	public void podnies() throws InterruptedException {
		licznikOwieczek.opusc();
		iloscOwieczek++;
		if(iloscOwieczek == 1) owieczki.podnies();
		licznikOwieczek.podnies();
	}
}