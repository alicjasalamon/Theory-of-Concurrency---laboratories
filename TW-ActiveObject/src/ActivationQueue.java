import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ActivationQueue {

	Deque<MethodRequest> mixedQueue;
	Deque<MethodRequest> producerQueue;
	Deque<MethodRequest> konsumerQueue;
	Lock lock = new ReentrantLock();
	
	Condition isProducer = lock.newCondition();
	Condition isKonsumer = lock.newCondition();
	Condition isTask = lock.newCondition();
	
	Servant servant;
	int bufferSize;
	
	ActivationQueue(Servant s, int bf){
		mixedQueue = new LinkedList<MethodRequest>();
		producerQueue = new LinkedList<MethodRequest>();
		konsumerQueue = new LinkedList<MethodRequest>();
		servant = s;
		bufferSize = bf;
	}

	
	public void enqueue(MethodRequest mr) {
		lock.lock();
		try{
			
			mixedQueue.offer(mr);
			if(mr.name.equals("Producer")){
				producerQueue.offer(mr);
				isProducer.signal();
			}
			else{
				konsumerQueue.offer(mr);
				isKonsumer.signal();
			}
			isTask.signal();
		}
		finally{
			lock.unlock();
		}
	}

	public MethodRequest dequeue()  {
		
		lock.lock();
		MethodRequest result = null;
		
		try {

			while(mixedQueue.size()==0) 
				isTask.await();
				
			MethodRequest preview = mixedQueue.peek();

			if(preview.name.equals("Producer"))
			{
				if(servant.howMany+preview.howMany<bufferSize){
					result = mixedQueue.poll();
					producerQueue.poll();
				}
				else
				{
					while( konsumerQueue.size()==0 ) 
						isKonsumer.await();
						
					result = konsumerQueue.poll();
					mixedQueue.remove(result);
					
				}
			}
			else
			{
				if(servant.howMany-preview.howMany>0){
					result = mixedQueue.poll();
					konsumerQueue.poll();
				}
				else
				{
					while( producerQueue.size()==0 ) 
						isProducer.await();
					
					result = producerQueue.poll();
					mixedQueue.remove(result);
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
		return result;
	}
}
	
