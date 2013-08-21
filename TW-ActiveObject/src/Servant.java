
public class Servant {

	int howMany;
	int methodTime;
	public Servant(int methodTime) {
		howMany=0;
		this.methodTime = methodTime;
	}

	public void consume(int ile) throws InterruptedException {
		Thread.sleep(methodTime);
		//System.out.println("konsument sobie zjada porcje " + ile
		//		+ " zostalo nam " + howMany);
		howMany -= ile;
	}

	public void produce(int ile) throws InterruptedException {
		Thread.sleep(methodTime);
		//System.out.println("producent produkuje sobie porcje " + ile
		//		+ ", a w calosci mamy " + (howMany));
		howMany += ile;
	}
}
