package distributedExtension;

import java.io.Serializable;

public class ResultMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
