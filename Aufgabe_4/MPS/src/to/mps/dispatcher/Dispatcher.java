package to.mps.dispatcher;

import java.rmi.Remote;
import java.rmi.RemoteException;

import to.mps.angebotskomponente.dataaccesslayer.AngebotTO;
import to.mps.auftragskomponente.dataaccesslayer.AuftragTO;
import to.mps.monitor.Monitor;

public interface Dispatcher extends Remote {
	public AngebotTO erstelleAngebot(AngebotTO a) throws RemoteException;
	public AuftragTO angebot2Auftrag(AngebotTO a) throws RemoteException;
	public void update(String name, boolean b);
	public void register(Monitor m);
	public void schliesseAb(AuftragTO a);
}
