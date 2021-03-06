package GUI;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import distributedExtension.Dispatcher;
import exceptions.CouldNotStartServerException;

public class Monitor extends Thread {
	private final int FPS = 1;
	private boolean run = true;
	private int lastServerID = 0;
	private int selectedServer = 0;
	DashboardGUI gui;
	Dispatcher dispatcher;
	
	Set<Integer> listOfAllServers = new HashSet<Integer>();
	Map<Integer, String> statusOfAllServers = new HashMap<Integer, String>();
	
	public Monitor(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
		gui = new DashboardGUI(this);
	}
	
	public void run() {
		long startTime;
		long endTime;
		
		while(run) {
			//Messe Startzeit
			startTime = System.currentTimeMillis();
			
			//Aktualisiere die Serverstati
			dispatcher.refreshStatus();
			
			//Setze den Dispatcherstatus in der GUI
			gui.setDispatcherStatus(dispatcher.isAlive());
			
			//Wenn neue Server dazugekommen sind oder alte verschwunden sind aktualisiere die GUI
			Set<Integer> newListOfAllServers = dispatcher.getListOfAllServers();
			if(!listOfAllServers.containsAll(newListOfAllServers)) {
				gui.refreshServerList(newListOfAllServers);
			}
			
			//Bestimme den Status des ausgewaehlten Servers
			String statusOfSelectedServer = dispatcher.statusOfServer(selectedServer);
			
			//Gib den Status an die GUI weiter
			gui.setStatusOfSelectedServer(statusOfSelectedServer);
			
			//Bestimme die Anzahl der Idle und Busy Server
			int idle = dispatcher.getAmountIdleServer();
			int busy = dispatcher.getAmountBusyServer();
			
			//Gib die Anzahlen an die GUI weiter
			gui.setIdleAmount(idle);
			gui.setBusyAmount(busy);
			
			//Pruefe auf gestorbene Server
			for(Integer serverID : listOfAllServers) {
				String status = statusOfAllServers.get(serverID);
				
				if(status == null) {
					statusOfAllServers.put(serverID, dispatcher.statusOfServer(serverID));
				} else if((status.equals("Busy") || status.equals("Idle")) && dispatcher.statusOfServer(serverID).equals("Offline")) {
					gui.serverDied(serverID);
					statusOfAllServers.put(serverID, dispatcher.statusOfServer(serverID));
				} else {
					statusOfAllServers.put(serverID, dispatcher.statusOfServer(serverID));
				}
			}
			
			//Messe Endzeit
			endTime = System.currentTimeMillis();
			
			//Warte eine passende Zeit lang
			holdFPS(startTime, endTime);
		}
	}
	
	public void terminate() {
		run = false;
	}
	
	private void holdFPS(long startTime, long endTime) {
		
		try {
			if((1 / FPS) > (endTime - startTime) / 1000) {
				Monitor.sleep(1000 * (1 / FPS) - ((endTime - startTime)));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void startServer(int serverID) {
		
		try {
			dispatcher.startServer(serverID, InetAddress.getLocalHost());
		} catch (ClassNotFoundException | IOException
				| CouldNotStartServerException e) {
			e.printStackTrace();
		}
	}
	
	public void killServer(int serverID) {
		try {
			dispatcher.stopServer(serverID);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CouldNotStartServerException e) {
			e.printStackTrace();
		}
	}
	
	public void setSelectedServer(int newSelectedServer) {
		this.selectedServer = newSelectedServer;
	}
	
	private int findNewServerID() {
		int i = lastServerID + 1;
		
		while(dispatcher.getListOfAllServers().contains(i)) {
			i++;
		}
		
		lastServerID = i;
		return i;
	}
}
