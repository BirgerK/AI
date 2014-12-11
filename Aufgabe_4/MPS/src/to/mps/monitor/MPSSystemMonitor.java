package to.mps.monitor;

public class MPSSystemMonitor {
	private String name;
	private boolean isAlive;
	private boolean isMonitored;
	private int numberOfProcessedRequests;
	private long uptime;
	private long downtime;
	private long uptimeStart;
	private long downTimeStart;

	

	public MPSSystemMonitor(String mpsSystemName) {
		this.setName(mpsSystemName);
		this.setAlive(true);
		this.setMonitored(true);
		this.numberOfProcessedRequests = 0;
		this.downtime = 0;
		this.uptime = 0;
		this.uptimeStart = System.currentTimeMillis();
	}

	public int getNumberOfProcessedRequests() {
		return numberOfProcessedRequests;
	}

	public int incrementNumberOfProcessedRequests() {
		return (++this.numberOfProcessedRequests);
	}

	public boolean isMonitored() {
		return isMonitored;
	}

	public void setMonitored(boolean isMonitored) {
		this.isMonitored = isMonitored;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		if(this.isAlive) {
			if(isAlive) {
				uptime += System.currentTimeMillis() - uptimeStart;
				uptimeStart = System.currentTimeMillis();
			} else {
				uptime += System.currentTimeMillis() - uptimeStart;
				downTimeStart = System.currentTimeMillis();
			}
		} else {
			if(isAlive) {
				downtime += System.currentTimeMillis() - downTimeStart;
				uptimeStart = System.currentTimeMillis();
			} else {
				downtime += System.currentTimeMillis() - downTimeStart;
				downTimeStart = System.currentTimeMillis();
			}
		}
		
		this.isAlive = isAlive;
		
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public long getUptime() {
		return this.uptime;
	}

	public void setUptime(long uptime) {
		this.uptime = uptime;
	}
	
	public long getDowntime() {
		return this.downtime;
	}
	
	public void setDowntime(long downtime) {
		this.downtime = downtime;
	}

	public long getUptimeStart(){
		return this.uptimeStart;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAlive ? 1231 : 1237);
		result = prime * result + (isMonitored ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MPSSystemMonitor other = (MPSSystemMonitor) obj;
		if (isAlive != other.isAlive)
			return false;
		if (isMonitored != other.isMonitored)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
