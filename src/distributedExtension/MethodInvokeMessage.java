package distributedExtension;

import java.io.Serializable;
import java.util.List;

public class MethodInvokeMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String methodToCall = null;
	private List<Object> argumentList = null;
	
	public MethodInvokeMessage(String method,List<Object> arguments){
		this.methodToCall = method;
		this.argumentList = arguments;
	}

	public String getMethodToCall() {
		return methodToCall;
	}

	public List<Object> getArgumentList() {
		return argumentList;
	}
}
