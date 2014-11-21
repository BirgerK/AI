package distributedExtension;

import static utils.Constants.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.Angebot;
import models.Fertigungsauftrag;
import models.Kundenauftrag;
import models.Transportauftrag;
import utils.SocketConnection;

public class Dispatcher extends Thread implements IDispatcherToClient,IMonitoring,IDispatcherRequests {
	private SocketConnection socketToClient = null;
	private Map<Integer,List<Object>> allServer = null; // Element 0 der List ist immer die ServerAdresse, Element 1 ist immer der Status des Servers
	private ServerSocket serverSocket = null;
	private boolean shutdown = false;
	
	public Dispatcher(){
		this.allServer = new HashMap<Integer,List<Object>>();
	}
	
	public void run(){
		try {
			serverSocket = new ServerSocket(MPS_DISPATCHER_PORT);
			
			while(!shutdown){
				socketToClient = new SocketConnection();
				
				//Auf eine eingehende Verbindung warten
				socketToClient.setSocket(serverSocket.accept());
				MethodInvokeMessage clientMessage = (MethodInvokeMessage) socketToClient.readObject();
				if(clientMessage.getMethodToCall().equals(CMD_ADD_SERVER)){
					if(clientMessage.getArgumentList().get(0) != null & clientMessage.getArgumentList().get(0) instanceof InetAddress){
						this.addServer((InetAddress) clientMessage.getArgumentList().get(0));
						socketToClient.writeObject(new ResultMessage(ANSWER_DONE));
					}
					socketToClient.writeObject(new ResultMessage(new WrongArgumentlistException()));
				} else {
					new DispatcherRequestHandler(socketToClient, this, clientMessage).start();
				}
			}
		} catch (IOException e) {
			System.err.println("Dispatcher: Error while listening on port " + MPS_DISPATCHER_PORT);
		} catch (ClassNotFoundException e) {
			System.err.println("Dispatcher: Error while receiving Message from Client.");
		}
		System.out.println("Dispatcher faehrt nun herunter.");
	}
	
	
	
	@Override
	public Set<Integer> getListOfAllServers() {
		return allServer.keySet();
	}

	@Override
	public int getAmountIdleServer() {
		int result = 0;
		
		for(int key:allServer.keySet()){
			if(getServerStatus(allServer.get(key)).equals(STATUS_IDLE)){
				result++;
			}
		}
		
		return result;
	}

	@Override
	public int getAmountBusyServer() {
		int result = 0;
		
		for(int key:allServer.keySet()){
			if(getServerStatus(allServer.get(key)).equals(STATUS_BUSY)){
				result++;
			}
		}
		
		return result;
	}

	@Override
	public void startServer(int id) throws UnknownHostException, IOException, ClassNotFoundException, CouldNotStartServerException {
		SocketConnection socketToServer = new SocketConnection(getServerAddress(allServer.get(id)),MPS_SERVER_THREAD_PORT);
		
		socketToServer.writeObject(new MethodInvokeMessage(CMD_START_SERVER, null));
		ResultMessage answerFromServer = (ResultMessage) socketToServer.readObject();
		if(answerFromServer.getResult().equals(ANSWER_DONE)){
			setServerStatusToIdle(id);
		} else {
			throw new CouldNotStartServerException();
		}
	}

	@Override
	public void stopServer(int id) throws IOException, ClassNotFoundException, CouldNotStartServerException {
SocketConnection socketToServer = new SocketConnection(getServerAddress(allServer.get(id)),MPS_SERVER_THREAD_PORT);
		
		socketToServer.writeObject(new MethodInvokeMessage(CMD_STOP_SERVER, null));
		ResultMessage answerFromServer = (ResultMessage) socketToServer.readObject();
		if(answerFromServer.getResult().equals(ANSWER_DONE)){
			setServerStatusToIdle(id);
		} else {
			throw new CouldNotStartServerException();
		}
	}

	@Override
	public String statusOfServer(int id) {
		if(allServer.containsKey(id)){
			return getServerStatus(allServer.get(id));
		}
		return null;
	}


	@Override
	public void addServer(InetAddress serverAddresse) throws UnknownHostException, IOException, ClassNotFoundException {
		SocketConnection connectToServer = new SocketConnection(serverAddresse,START_SERVER_SERVICE_PORT);
		
		connectToServer.writeObject(new MethodInvokeMessage(CMD_PING,null));
		ResultMessage resultMessage = (ResultMessage) connectToServer.readObject();
		
		if(resultMessage.getResult().equals(CMD_PONG)){
			addSafeEntryAllServers(allServer.size()+1, new ArrayList<Object>(Arrays.asList(serverAddresse,STATUS_OFFLINE)));
		}
	}

	@Override
	public synchronized InetAddress getIdleServerAddress() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setServerStatusToOffline(InetAddress serverAddress) {
		for(int key:allServer.keySet()){
			if(getServerAddress(allServer.get(key)).equals(serverAddress)){ //Falls der zu aendernde Eintrag der jetzige Eintrag ist
				addSafeEntryAllServers(key, new ArrayList<Object>(Arrays.asList(serverAddress,STATUS_OFFLINE)));
			}
		}
	}
	
	
	
	//############## HELPER-FUNKTIONEN ###################
	private InetAddress getServerAddress(List<Object> mapElement){
		return (InetAddress) mapElement.get(0);
	}
	private String getServerStatus(List<Object> mapElement){
		return (String) mapElement.get(1);
	}
	private synchronized void addSafeEntryAllServers(Object key,Object value){
		allServer.put((Integer) key,(List<Object>) value);
	}
	
	private void setServerStatusToIdle(int id){
		addSafeEntryAllServers(id, new ArrayList<Object>(Arrays.asList(getServerAddress(allServer.get(id)),STATUS_IDLE))); //Sieht echt beknackt aus......
	}

	


	
}
