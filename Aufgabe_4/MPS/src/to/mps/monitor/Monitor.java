package to.mps.monitor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Monitor extends Remote{
	public void ping(String name) throws RemoteException;
	public void register(String name) throws RemoteException;
	public int incrementNumberOfProcessedRequests(String name) throws RemoteException;
}
