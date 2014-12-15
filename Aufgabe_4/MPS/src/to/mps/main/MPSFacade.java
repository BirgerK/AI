package to.mps.main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.angebotskomponente.dataaccesslayer.AngebotTO;
import to.mps.angebotskomponente.accesslayer.AngebotFacade;
import to.mps.auftragskomponente.dataaccesslayer.Auftrag;
import to.mps.auftragskomponente.dataaccesslayer.AuftragTO;
import to.mps.auftragskomponente.accesslayer.AuftragFacade;
import to.mps.monitor.Monitor;

public class MPSFacade implements MPS{
	private final String name;
	private final long PERIOD_IN_MS = 500;
	private final AngebotFacade angebotFacade;
	private final AuftragFacade auftragFacade;
	
	public MPSFacade(String name){
		this.name = name;
		this.angebotFacade = new AngebotFacade();
		this.auftragFacade = new AuftragFacade();
	}
	
	public static void main(String[] args){
		String mpsName = "";
		if(args.length == 0){
			System.out.println("Args: String MPSName");
			System.exit(-1);
		}
		else{
			mpsName = args[0];
		}
		
	    try {
	    	
			MPS mps = new MPSFacade(mpsName);
			MPS stub = (MPS) UnicastRemoteObject.exportObject( mps, 0 );
		    RemoteServer.setLog( System.out );

		    Registry registry = LocateRegistry.getRegistry();
		    registry.rebind( mpsName, stub );

		    System.out.println( "MPS Server als "+ mpsName +" angemeldet" );		
			
		    ((MPSFacade) mps).run();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run(){
	    Registry registry;
		try {
			registry = LocateRegistry.getRegistry();
			final Monitor monitor = (Monitor) registry.lookup( "Monitor" );
			monitor.register(name);
			Timer timer = new Timer();

			timer.schedule( new TimerTask() {
			    public void run() {
			    	try {
						monitor.ping(name);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			 }, 0, PERIOD_IN_MS);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	@Override
	public AngebotTO erstelleAngebot(AngebotTO a) throws RemoteException {
		System.out.println("erstelle Angebot");
		return angebotFacade.erstelleAngebot(a.toEntity()).toTO();
	}

	@Override
	public AuftragTO angebot2Auftrag(AngebotTO a) throws RemoteException {
		System.out.println("angebot2Auftrag");
		try{
			return auftragFacade.erstelleAuftragAusAngebot(a.toEntity()).toTO();
		}
		catch(Exception e){
			return null;
		}
	}

	@Override
	public void schliesseAb(AuftragTO a) throws RemoteException {
		System.out.println("Auftrag wird abgeschlossen");
		auftragFacade.schliesseAb(a.toEntity());
	}
	
	
}
