package to.mps.monitor;

import java.rmi.RemoteException;
import java.util.List;

public interface MonitorForDashboardInterface{
	public List<String> getMpsSystemsNames();
	public boolean isAlive(String name) throws RemoteException;
	public void setOffline(String name) throws RemoteException;
	public void setOnline(String name) throws RemoteException;
	public int getNumberOfProcessedRequests(String name) throws RemoteException;
	public long getUptime(String name) throws RemoteException;
	public long getDowntime(String name) throws RemoteException;
}
