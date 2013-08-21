
public abstract class MethodRequest {

	public String name;
	public int howMany;
	public abstract void call() throws InterruptedException;
	public abstract void setServant(Servant serv);

}
