package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import utils.*;
import distributedExtension.*;

public class Client {
	
	SocketConnection sConnection = null;
	String hostName = null;
	InetAddress ipAdress = null;
	
	//Socket aufbauen
	private void Client(String hostName) throws UnknownHostException, IOException{
		this.hostName = hostName;
	}

	private void Client(InetAddress ipAddress) throws UnknownHostException, IOException{
		this.ipAdress = ipAddress;
	}
	// Im Dispatcher Server hinzufügen
	
	
	//Funktionen mit Argumenenten aufrufen
	public ResultMessage anfrageSenden(String command, List<Object> arguments) throws IOException, ClassNotFoundException {
		if (ipAdress == null && hostName == null)throw new SocketException("Socket isn't initalized");
		if(hostName == null && ipAdress != null)sConnection = new SocketConnection(this.ipAdress, Constants.MPS_DISPATCHER_PORT);
		if(ipAdress == null && hostName != null)sConnection = new SocketConnection(this.hostName, Constants.MPS_DISPATCHER_PORT);
		
		MethodInvokeMessage message = new MethodInvokeMessage(command, arguments);

		sConnection.writeObject(message);
		ResultMessage result = (ResultMessage) sConnection.readObject();
		
		return result;

	}
	
	
	

}
