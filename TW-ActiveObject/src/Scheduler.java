
public class Scheduler extends Thread {

	
	int bufferSize;
	MethodRequest mr;
	int methodTime;
	Servant s;
	ActivationQueue aq;
	
	public Scheduler(int bf, int mt) {
		s = new Servant(mt);
		bufferSize=bf;
		aq = new ActivationQueue(s,bufferSize);
	}
	
	//enqueue
	public void enqueue(MethodRequest req) {
		req.setServant(s);
		aq.enqueue(req);
	}
	
	//dispatch
	public void run()
	{
		while (true)
		{
				try {
					mr = aq.dequeue();
					mr.call();
				} catch (Exception e) {
					//e.printStackTrace();
				}

		}
	}

}
