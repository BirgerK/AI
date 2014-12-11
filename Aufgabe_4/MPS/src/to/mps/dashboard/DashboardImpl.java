package to.mps.dashboard;

import java.awt.EventQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import to.mps.monitor.MPSMonitoringVerwalter;
import to.mps.monitor.Monitor;
import to.mps.monitor.MonitorForDashboardInterface;

public class DashboardImpl {
	private MonitorForDashboardInterface monitor;
	private DashboardGUI window;
	
	public DashboardImpl(final MonitorForDashboardInterface monitor){
		this.monitor = monitor;

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new DashboardGUI(monitor);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	    service.scheduleWithFixedDelay(new Runnable()
	      {
	        @Override
	        public void run()
	        {
	        	update();
	        }
	      }, 1, 1, TimeUnit.SECONDS);
	}
	
	public void update(){
		window.update();
	}
}
