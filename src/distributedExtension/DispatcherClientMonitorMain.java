package distributedExtension;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import Client.Client;
import GUI.Monitor;

public abstract class DispatcherClientMonitorMain {
	private static Dispatcher dispatcher = null;
	private static Monitor monitor = null;
	private static Client client = null;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		dispatcher = new Dispatcher();
		client = new Client(InetAddress.getLocalHost());
		monitor = new Monitor(dispatcher);
		
		
		//StartServerService.main(null);
		dispatcher.start();
		client.start();
		monitor.start();
		//hoffen dass es laeuft
	}

}
