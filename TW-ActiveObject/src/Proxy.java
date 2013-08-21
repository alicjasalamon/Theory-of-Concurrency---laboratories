import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Proxy {

	Scheduler scheduler;
	Lock lock;

	public Proxy(Scheduler s) {
		this.scheduler = s;
		lock = new ReentrantLock();
	}

	public Future produce(int ile) {
		Condition c = lock.newCondition();
		Future result = new Future(c, lock);
		MethodRequest request = new MethodProcude(result, ile, c, lock);
		scheduler.enqueue(request);
		return result;
	}

	public Future consume(int ile) {
		Condition c = lock.newCondition();
		Future result = new Future(c, lock);
		MethodRequest request = new MethodConsume(result, ile, c, lock);
		scheduler.enqueue(request);
		return result;
	}

}
