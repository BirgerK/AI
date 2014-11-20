package distributedExtension;

import static utils.Constants.CMD_PING;
import static utils.Constants.CMD_PONG;
import static utils.Constants.START_SERVER_SERVICE_PORT;
import static utils.Constants.STATUS_BUSY;
import static utils.Constants.STATUS_IDLE;
import static utils.Constants.STATUS_OFFLINE;

import java.io.IOException;
import java.net.InetAddress;
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

public class Dispatcher implements IDispatcher,IMonitoring {
	private SocketConnection socket = null;
	private Map<Integer,List<Object>> allServer = null; // Element 0 der List ist immer die ServerAdresse, Element 1 ist immer der Status des Servers
	
	public Dispatcher(){
		this.allServer = new HashMap<Integer,List<Object>>();
	}
	
	
	@Override
	public Date berechneFertigungszeitpunkt(int fertigungsAuftragId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fertigungsauftrag erstelleFertigungsauftrag(Angebot angebot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transportauftrag erstelleTransportauftrag(Angebot angebot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Kundenauftrag erstelleKundenauftrag(Angebot angebot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double berechneKosten(Angebot angebot) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Angebot erstelleAngebot(Map<Integer, Integer> matNrZuMenge,int kundenNr) {
		// TODO Auto-generated method stub
		return null;
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
	public void startServer(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopServer(int id) {
		// TODO Auto-generated method stub
		
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
			allServer.put(allServer.size()+1, new ArrayList<Object>(Arrays.asList(serverAddresse,STATUS_OFFLINE)));
		}
	}

	
	
	
	
	//############## HELPER-FUNKTIONEN ###################
	private InetAddress getServerAddress(List<Object> mapElement){
		return (InetAddress) mapElement.get(0);
	}
	private String getServerStatus(List<Object> mapElement){
		return (String) mapElement.get(1);
	}
}
