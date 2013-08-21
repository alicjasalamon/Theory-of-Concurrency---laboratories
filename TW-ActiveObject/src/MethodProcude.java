import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


public class MethodProcude extends MethodRequest {

	Servant servant;
	Future result;
	Condition c;
	Lock l;
	
	public MethodProcude(Future wynik, int howMany, Condition condition, Lock lock) {
		this.result = wynik;
		this.howMany = howMany;
		this.name = "Producer";
		this.c = condition;
		this.l = lock;
	}

	@Override
	public void call() throws InterruptedException {
		servant.produce(howMany); //ileElementow += howMany;
		//System.out.println("produkcja " + howMany + " elementow, teraz jest " + servant.ileElementow);
		
		try{
			l.lock();
			result.value = servant.howMany;
			c.signal();
		}finally{
			l.unlock();
		}
	}

	@Override
	public void setServant(Servant serv) {
		this.servant = serv;
	}

}
