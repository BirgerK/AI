package to.mps.dispatcher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.angebotskomponente.dataaccesslayer.AngebotTO;
import to.mps.auftragskomponente.dataaccesslayer.Auftrag;
import to.mps.auftragskomponente.dataaccesslayer.AuftragTO;
import to.mps.main.MPS;
import to.mps.monitor.Monitor;

public class DispatcherImpl implements Dispatcher{
	private Map<String, Boolean> server;
	private Monitor monitor;
	
	public DispatcherImpl(){
		server = new HashMap<String, Boolean>();
	}
	
	public void register(Monitor monitor){
		this.monitor = monitor;
	}
	
	@Override
	public AngebotTO erstelleAngebot(AngebotTO a) throws RemoteException{
		try {
			return getServer().erstelleAngebot(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public AuftragTO angebot2Auftrag(AngebotTO a) throws RemoteException{
		try {
			return getServer().angebot2Auftrag(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void update(String name, boolean b) {
		server.put(name, b);
	}

	private String getActiveServerName() throws Exception{
		for(Entry<String,Boolean> entry : server.entrySet()){
			if(entry.getValue()){
				return entry.getKey();
			}
		}
		
		throw new Exception("Kein aktiver Server");
	}
	
	private MPS getServer() throws Exception{
		String name = getActiveServerName();
		monitor.incrementNumberOfProcessedRequests(name);
		Registry registry = LocateRegistry.getRegistry();
		return ((MPS) registry.lookup(name));
	}

	@Override
	public void schliesseAb(AuftragTO a) {
		try {
			getServer().schliesseAb(a);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
