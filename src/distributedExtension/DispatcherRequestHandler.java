package distributedExtension;


import static utils.Constants.*;

import java.io.IOException;
import java.net.InetAddress;

import utils.SocketConnection;

public class DispatcherRequestHandler extends Thread{
	private SocketConnection socketToClient = null;
	private IDispatcherRequests myDispatcher = null;
	private MethodInvokeMessage messageToServer = null;
	
	public DispatcherRequestHandler(SocketConnection socketToClient,IDispatcherRequests myDispatcher,MethodInvokeMessage messageToServer){
		this.socketToClient = socketToClient;
		this.myDispatcher = myDispatcher;
		this.messageToServer = messageToServer;
	}
	
	public void run(){
		//Anfrage an den Server stellen
		InetAddress serverForRequest = myDispatcher.getIdleServerAddress();
		ResultMessage result = null;
		SocketConnection socketToServer = null;
		try {
			socketToServer = new SocketConnection(serverForRequest, MPS_SERVER_THREAD_PORT);
		} catch (Exception e) {
			result = new ResultMessage(new ServerNotReachableException());
		}
		
		if (socketToServer != null){	//Server ist also erreichbar
			try {
				socketToServer.writeObject(messageToServer);
				result = new ResultMessage(socketToServer.readObject());
			} catch (Exception e) {
				result = new ResultMessage(new ServerCommunicationException());
			}
		}
		
		try {
			socketToClient.writeObject(result);
		} catch (IOException e) {
			System.err.println("Dispatcher: Konnte Ergebnis nicht wieder an Client senden! Ergebnis wird verworfen. Moegliche Veraenderungen in der Persistenz bleiben bestehen!");
		}
	}
}
