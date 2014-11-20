package distributedExtension;

import java.util.List;

public class MethodInvokeMessage {
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
