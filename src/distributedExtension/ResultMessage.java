package distributedExtension;

public class ResultMessage {
	private Object result = null;
	
	public ResultMessage(Object result){
		this.result = result;
	}
	
	public Object getResult(){
		return this.result;
	}
	
	public boolean isResultException(){
		return (result instanceof Exception);
	}
}
