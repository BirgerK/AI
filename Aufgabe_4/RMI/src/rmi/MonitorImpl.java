package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;

public class MonitorImpl implements Monitor{
	
	public void THIIIS_IIS_MPS(String name) throws RemoteException {
		System.out.println(name);
		
	}

	public void register(String name) throws RemoteException {
		System.out.println(name);
	}

	  public static void main( String[] args ) throws RemoteException
	  {
	    LocateRegistry.createRegistry( Registry.REGISTRY_PORT );

	    MonitorImpl monitor = new MonitorImpl();
	    Monitor stub = (Monitor) UnicastRemoteObject.exportObject( monitor, 0 );
	    RemoteServer.setLog( System.out );

	    Registry registry = LocateRegistry.getRegistry();
	    registry.rebind( "Monitor", stub );

	    System.out.println( "Monitor angemeldet" );
	  }
	
	
}
