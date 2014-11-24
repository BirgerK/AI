package Client;

import interfaces.IDispatcherToClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import utils.*;
import distributedExtension.*;

public class Client {
	
	private IDispatcherToClient dispatcher = null;
	
	//Socket aufbauen
	public Client(IDispatcherToClient dispatcher) throws UnknownHostException, IOException{
		this.dispatcher = dispatcher;
	}

	
	
	

}
