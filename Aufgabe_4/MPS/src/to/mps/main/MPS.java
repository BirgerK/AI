package to.mps.main;

import java.rmi.Remote;
import java.rmi.RemoteException;

import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.angebotskomponente.dataaccesslayer.AngebotTO;
import to.mps.auftragskomponente.dataaccesslayer.Auftrag;
import to.mps.auftragskomponente.dataaccesslayer.AuftragTO;

public interface MPS extends Remote{
	AngebotTO erstelleAngebot(AngebotTO a) throws RemoteException;
	AuftragTO angebot2Auftrag(AngebotTO a) throws RemoteException;
	void schliesseAb(AuftragTO a) throws RemoteException;
}
