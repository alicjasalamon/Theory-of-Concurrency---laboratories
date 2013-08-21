import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Future {

	public Integer value;
	Condition c;
	Lock l;

	public Future(Condition c, Lock l) {
		value = -1;
		this.c = c;
		this.l = l;
	}

	public boolean jestDostepny() {
		return (value != -1);
	}

	public void get() throws InterruptedException {
		try
		{
			l.lock();
			while(value==-1) c.await();
		}finally{
			l.unlock();
		}
	}

}
