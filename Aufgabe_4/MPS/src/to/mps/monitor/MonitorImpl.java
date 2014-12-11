package to.mps.monitor;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import to.mps.dashboard.DashboardImpl;
import to.mps.dispatcher.Dispatcher;

public class MonitorImpl implements Monitor, MonitorForDashboardInterface{
	private MPSMonitoringVerwalter mpsSystems = new MPSMonitoringVerwalter();
	private int timeoutInSec;
	private Map<String, ScheduledExecutorService> timers;
	private Dispatcher dispatcher;
	private DashboardImpl dashboard;

	public MonitorImpl(Dispatcher dispatcher, int timeoutInSec) {
		dashboard = new DashboardImpl(this);
		this.timeoutInSec = timeoutInSec;
		this.timers = new Hashtable<>();
		this.dispatcher = dispatcher;
		dispatcher.register(this);
	}
	
	private void shutdownAndRemoveTimer(String name){
		if(timers.containsKey(name)){
			timers.get(name).shutdownNow();
			timers.remove(name);
		}
	}
	
	private void shutdownTimer(String name){
		if(timers.containsKey(name)){
			timers.get(name).shutdownNow();
		}
	}
	
	@Override
	public void ping(String name) throws RemoteException {
		shutdownAndRemoveTimer(name);
		startTimer(name);
		if(mpsSystems.isMonitored(name)) {
			mpsSystems.setAlive(name, true);
			dispatcher.update(name, true);
			dashboard.update();
		}
		else{
			mpsSystems.setAlive(name,  false);
		}
	}

	@Override
	public void register(String name) throws RemoteException {
		mpsSystems.add(name);
		startTimer(name);
		dispatcher.update(name, true);
	}

	private void startTimer(final String name) {
		final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	    service.scheduleWithFixedDelay(new Runnable()
	      {
	        @Override
	        public void run()
	        {
	        	if((mpsSystems.getUptimeStart(name)+timeoutInSec*1000 < System.currentTimeMillis())) {
	        		dispatcher.update(name, false);
	        		mpsSystems.setAlive(name, false);
	        		System.out.println(mpsSystems.isAlive(name));
	        	}
	        }
	      }, timeoutInSec, timeoutInSec, TimeUnit.SECONDS);
        timers.put(name, service);
	}

	@Override
	public boolean isAlive(String name) throws RemoteException {
		return mpsSystems.isAlive(name);
	}

	@Override
	public void setOffline(String name) throws RemoteException {
		if(mpsSystems.isMonitored(name)){

			dispatcher.update(name, false);
		}
		mpsSystems.setOffline(name);
		
		shutdownAndRemoveTimer(name);
		

		dashboard.update();
	}

	@Override
	public void setOnline(String name) throws RemoteException {
		if(!mpsSystems.isMonitored(name)){
			dispatcher.update(name, true);
		}
		mpsSystems.setOnline(name);
		startTimer(name);

		dashboard.update();
	}

	@Override
	public int getNumberOfProcessedRequests(String name) throws RemoteException {
		return this.mpsSystems.getNumberOfProcessedRequests(name);
	}

	@Override
	public long getUptime(String name) throws RemoteException {
		return mpsSystems.getUptime(name);
	}

	@Override
	public long getDowntime(String name) throws RemoteException {
		return mpsSystems.getDowntime(name);
	}

	@Override
	public List<String> getMpsSystemsNames() {
		return mpsSystems.getNames();
	}

	public int incrementNumberOfProcessedRequests(String name)
			throws RemoteException {
		return this.mpsSystems.incrementNumberOfProcessedRequests(name);
	}

}
