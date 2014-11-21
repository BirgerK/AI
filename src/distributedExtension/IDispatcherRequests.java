package distributedExtension;

import java.net.InetAddress;

public interface IDispatcherRequests {
	public InetAddress getIdleServerAddress();
	public void setServerStatusToOffline(InetAddress serverAddress);
}
