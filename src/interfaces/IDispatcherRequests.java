package interfaces;

import java.net.InetAddress;
import java.util.List;

import exceptions.NoIdleServerAvailableException;

public interface IDispatcherRequests {
	/**
	 * @return Liste, erstes Element ist InetAddress des Server, zweites Element ist Portnummer des Server
	 * @throws NoIdleServerAvailableException
	 */
	public List<Object> getIdleServerAddress() throws NoIdleServerAvailableException;
	public void setServerStatusToOffline(InetAddress serverAddress);
	public void setServerStatusToIdle(InetAddress serverAddress);
}
