package distributedExtension;

public abstract class DispatcherClientMonitorMain {
	private static Dispatcher dispatcher = null;
	public static void main(String[] args) {
		dispatcher = new Dispatcher();
		
		//client = new client(dispatcher);
		//monitor = new monitor(dispatcher);
		
		dispatcher.start();
		//client.start();
		//monitor.start();
		//hoffen dass es laeuft

	}

}
