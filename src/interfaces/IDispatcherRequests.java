package interfaces;

import java.net.InetAddress;

import exceptions.NoIdleServerAvailableException;

public interface IDispatcherRequests {
	public InetAddress getIdleServerAddress() throws NoIdleServerAvailableException;
	public void setServerStatusToOffline(InetAddress serverAddress);
	public void setServerStatusToIdle(InetAddress serverAddress);
}
