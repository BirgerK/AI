package to.mps.client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.angebotskomponente.dataaccesslayer.AngebotTO;
import to.mps.auftragskomponente.dataaccesslayer.AuftragTO;
import to.mps.dashboard.DashboardImpl;
import to.mps.dispatcher.Dispatcher;
import to.mps.dispatcher.DispatcherImpl;
import to.mps.fertigungskomponente.dataaccesslayer.Bauteil;
import to.mps.monitor.Monitor;
import to.mps.monitor.MonitorImpl;

public class ClientImpl {
	Dispatcher dispatcher;
	private final long PERIOD = 1*1000;
	private static final int TIMEOUT_IN_SEC = 3;
	private static final int SLEEP_IN_MS = 10000;
	public static void main( String[] args ){
		Dispatcher dispatcher = new DispatcherImpl();
		Monitor monitor = new MonitorImpl(dispatcher, TIMEOUT_IN_SEC);

	    Monitor stub;
		try {
			LocateRegistry.createRegistry( Registry.REGISTRY_PORT );
			stub = (Monitor) UnicastRemoteObject.exportObject( monitor, 0 );
		    RemoteServer.setLog( System.out );

		    Registry registry = LocateRegistry.getRegistry();
		    registry.rebind( "Monitor", stub );

		    System.out.println( "Monitor angemeldet" );
		    
		    Thread.sleep(SLEEP_IN_MS);
		    
		    
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new ClientImpl(dispatcher).run();
		  	
	}

	public ClientImpl(Dispatcher dispatcher){
		this.dispatcher = dispatcher;
	}
	
	public void run(){
		Timer timer = new Timer();

		timer.schedule( new TimerTask() {
		    public void run() {
		       AngebotTO a;
				try {
					a = dispatcher.erstelleAngebot(getAngebot());
			       double random = Math.random();
			       if(random > 0.5){
			    	   AuftragTO b = dispatcher.angebot2Auftrag(a);
			    	   dispatcher.schliesseAb(b);
			       }
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		    }
		 }, 0, PERIOD);
	}
	
	private AngebotTO getAngebot(){
		return new AngebotTO(new Date(), new Date(), 10.0, null);
	}
}
