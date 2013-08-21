import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


public class MethodConsume extends MethodRequest{

	Servant servant;
	Future wynik;
	Lock l;
	Condition c;
	
	public MethodConsume(Future wynik, int howMany, Condition condition, Lock l) {
		this.wynik = wynik;
		this.howMany = howMany;
		this.name = "Consumer";
		this.c = condition;
		this.l =l;
	}

	@Override
	public void call() throws InterruptedException {
		servant.consume(howMany);
		try{
			l.lock();
			wynik.value = servant.howMany;
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
