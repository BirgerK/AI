package to.mps.monitor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MPSMonitoringVerwalter {
	Map<String, MPSSystemMonitor> mpsSystems;
	
	public MPSMonitoringVerwalter() {
		this.mpsSystems = new Hashtable<>();
	}
	
	public void add(String mpsSystemName) {
		mpsSystems.put(mpsSystemName, new MPSSystemMonitor(mpsSystemName));
	}
	
	public boolean isAlive(String name) {
		return mpsSystems.get(name).isAlive();
	}

	public void setAlive(String name, boolean isAlive) {
		mpsSystems.get(name).setAlive(isAlive);
	}

	public void setOffline(String name) {
		mpsSystems.get(name).setMonitored(false);
		this.setAlive(name, false);
	}

	public void setOnline(String name) {
		mpsSystems.get(name).setMonitored(true);
		this.setAlive(name, true);
	}

	public boolean isMonitored(String name) {
		return mpsSystems.get(name).isMonitored();
	}
	
	public List<String> getNames(){
		return new ArrayList<String>(mpsSystems.keySet());
	}
	
	public int incrementNumberOfProcessedRequests(String name) {
		return mpsSystems.get(name).incrementNumberOfProcessedRequests();
	}
	
	public int getNumberOfProcessedRequests(String name) {
		return mpsSystems.get(name).getNumberOfProcessedRequests();
	}

	public long getUptime(String name) {
		return mpsSystems.get(name).getUptime();
	}

	public long getDowntime(String name) {
		return mpsSystems.get(name).getDowntime();
	}
	
	public long getUptimeStart(String name){
		return mpsSystems.get(name).getUptimeStart();
	}
}
