package distributedExtension;

import java.net.InetAddress;

public interface IDispatcherRequests {
	public InetAddress getIdleServerAddress() throws NoIdleServerAvailableException;
	public void setServerStatusToOffline(InetAddress serverAddress);
	public void setServerStatusToIdle(InetAddress serverAddress);
}
