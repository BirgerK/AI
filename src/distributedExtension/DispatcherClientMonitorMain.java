package distributedExtension;

import GUI.Monitor;

public abstract class DispatcherClientMonitorMain {
	private static Dispatcher dispatcher = null;
	private static Monitor monitor = null;
	
	public static void main(String[] args) {
		dispatcher = new Dispatcher();
		//client = new client(dispatcher);
		monitor = new Monitor(dispatcher);
		
		
		StartServerService.main(null);
		dispatcher.start();
		//client.start();
		monitor.start();
		//hoffen dass es laeuft
	}

}
