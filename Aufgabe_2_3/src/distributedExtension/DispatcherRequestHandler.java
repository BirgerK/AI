package distributedExtension;


import interfaces.IDispatcherRequests;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import utils.SocketConnection;
import exceptions.ServerCommunicationException;

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
		InetAddress serverForRequest;
		int serverPortNumber;
		ResultMessage result = null;
		try {
			List<Object> idleServer = myDispatcher.getIdleServerAddress();
			serverForRequest = (InetAddress) idleServer.get(0);
			serverPortNumber = (int) idleServer.get(1);
			SocketConnection socketToServer = new SocketConnection(serverForRequest, serverPortNumber);

			if (socketToServer != null){	//Server ist also erreichbar
				try {
					socketToServer.writeObject(messageToServer);
					result = (ResultMessage) socketToServer.readObject();
					//Anfrage komplett fertig bearbeitet, Server wird wieder freigegeben
					myDispatcher.setServerStatusToIdle(serverForRequest);
				} catch (Exception e) {
					result = new ResultMessage(new ServerCommunicationException());
				}
			}
		} catch (Exception e1) {
			result = new ResultMessage(e1);
		}
		
		
		try {
			socketToClient.writeObject(result);
			socketToClient.closeConnection();
		} catch (IOException e) {
			System.err.println("Dispatcher: Konnte Ergebnis nicht wieder an Client senden! Ergebnis wird verworfen. Moegliche Veraenderungen in der Persistenz bleiben bestehen!");
		}
		
		
	}
}
