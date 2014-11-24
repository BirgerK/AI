package distributedExtension;

import static utils.Constants.ANSWER_DONE;
import static utils.Constants.CMD_ADD_SERVER;
import static utils.Constants.CMD_PING;
import static utils.Constants.CMD_PONG;
import static utils.Constants.CMD_START_SERVER;
import static utils.Constants.CMD_STOP_SERVER;
import static utils.Constants.MPS_DISPATCHER_PORT;
import static utils.Constants.MPS_SERVER_THREAD_PORT;
import static utils.Constants.START_SERVER_SERVICE_PORT;
import static utils.Constants.STATUS_BUSY;
import static utils.Constants.STATUS_IDLE;
import static utils.Constants.STATUS_OFFLINE;
import interfaces.IDispatcherRequests;
import interfaces.IDispatcherToClient;
import interfaces.IDispatcherToMonitor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utils.SocketConnection;
import exceptions.CouldNotStartServerException;
import exceptions.NoIdleServerAvailableException;
import exceptions.WrongArgumentlistException;

public class Dispatcher extends Thread implements IDispatcherToClient,IDispatcherToMonitor,IDispatcherRequests {
	private SocketConnection socketToClient = null;
	private static Map<Integer,List<Object>> allServer = null; // Element 0 der List ist immer die ServerAdresse, Element 1 ist immer der Port des Servers, Element 2 ist immer der Status des Servers
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
					if(clientMessage.getArgumentList().get(0) != null & clientMessage.getArgumentList().get(0) instanceof InetAddress &
							clientMessage.getArgumentList().get(1) != null & clientMessage.getArgumentList().get(1) instanceof Integer){
						this.addServer((InetAddress) clientMessage.getArgumentList().get(0),(int) clientMessage.getArgumentList().get(1));
						socketToClient.writeObject(new ResultMessage(ANSWER_DONE));
					} else {
						socketToClient.writeObject(new ResultMessage(new WrongArgumentlistException()));
					}
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
	public void startServer(int id,InetAddress monitorAddress) throws UnknownHostException, IOException, ClassNotFoundException, CouldNotStartServerException {
		SocketConnection socketToServer = new SocketConnection(getServerAddress(allServer.get(id)),START_SERVER_SERVICE_PORT);
		
		socketToServer.writeObject(new MethodInvokeMessage(CMD_START_SERVER, new ArrayList<Object>(Arrays.asList(monitorAddress,getServerPort(allServer.get(id))))));
		ResultMessage answerFromServer = (ResultMessage) socketToServer.readObject();
		if(answerFromServer.getResult().equals(ANSWER_DONE)){
			setServerStatusToIdle(id);
		} else {
			throw new CouldNotStartServerException();
		}
	}

	@Override
	public void stopServer(int id) throws IOException, ClassNotFoundException, CouldNotStartServerException {
		SocketConnection socketToServer = new SocketConnection(getServerAddress(allServer.get(id)),START_SERVER_SERVICE_PORT);
		
		socketToServer.writeObject(new MethodInvokeMessage(CMD_STOP_SERVER, new ArrayList<Object>(Arrays.asList(getServerPort(allServer.get(id))))));
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
	public void addServer(InetAddress serverAddresse, int portNumber) throws UnknownHostException, IOException, ClassNotFoundException {
		SocketConnection connectToServer = new SocketConnection(serverAddresse,START_SERVER_SERVICE_PORT);
		
		connectToServer.writeObject(new MethodInvokeMessage(CMD_PING,null));
		ResultMessage resultMessage = (ResultMessage) connectToServer.readObject();
		
		if(resultMessage.getResult().equals(CMD_PONG)){
			addSafeEntryAllServers(allServer.size()+1, new ArrayList<Object>(Arrays.asList(serverAddresse,portNumber,STATUS_OFFLINE)));
		}
	}

	@Override
	public synchronized List<Object> getIdleServerAddress() throws NoIdleServerAvailableException {
		List<Object> result = new ArrayList<Object>();
		for(int key:allServer.keySet()){
			if(getServerStatus(allServer.get(key)).equals(STATUS_IDLE)){
				result.add(getServerAddress(allServer.get(key)));
				result.add(getServerPort(allServer.get(key)));
				setServerStatusToBusy(key);
				break;
			}
		}
		
		if(result.isEmpty()){
			throw new NoIdleServerAvailableException();
		}
		
		return result;
	}
	
	@Override
	public void setServerStatusToOffline(InetAddress serverAddress) {
		setServerStatus(serverAddress, STATUS_OFFLINE);
	}
	
	@Override
	public void setServerStatusToIdle(InetAddress serverAddress) {
		setServerStatus(serverAddress, STATUS_IDLE);
	}
	
	@Override
	public void setServerStatus(InetAddress serverAddress,String serverStatus) {
		for(int key:allServer.keySet()){
			if(getServerAddress(allServer.get(key)).equals(serverAddress)){ //Falls der zu aendernde Eintrag der jetzige Eintrag ist
				addSafeEntryAllServers(key, new ArrayList<Object>(Arrays.asList(serverAddress,getServerStatus(allServer.get(key)),serverStatus)));
			}
		}
	}
	
	
	
	//############## HELPER-FUNKTIONEN ###################
	private InetAddress getServerAddress(List<Object> mapElement){
		return (InetAddress) mapElement.get(0);
	}
	private int getServerPort(List<Object> mapElement){
		return (int) mapElement.get(1);
	}
	private String getServerStatus(List<Object> mapElement){
		return (String) mapElement.get(2);
	}
	private static synchronized void addSafeEntryAllServers(Object key,Object value){
		allServer.put((Integer) key,(List<Object>) value);
	}
	
	private void setServerStatusToIdle(int id){
		addSafeEntryAllServers(id, new ArrayList<Object>(Arrays.asList(getServerAddress(allServer.get(id)),getServerPort(allServer.get(id)),STATUS_IDLE))); //Sieht echt beknackt aus......
	}
	
	private void setServerStatusToBusy(int id){
		addSafeEntryAllServers(id, new ArrayList<Object>(Arrays.asList(getServerAddress(allServer.get(id)),getServerPort(allServer.get(id)),STATUS_BUSY))); //Sieht echt beknackt aus......
	}

	


	
}
