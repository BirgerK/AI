package to.mps.dashboard;

import java.awt.Button;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import to.mps.monitor.MPSMonitoringVerwalter;
import to.mps.monitor.Monitor;
import to.mps.monitor.MonitorForDashboardInterface;


class TableModel extends AbstractTableModel {
	private String columnNames[] = {"Name", "Anzahl Serviceanfragen", "Uptime", "Downtime", "Status"};
	private MonitorForDashboardInterface monitor;
	private Object data[][] = null;
	
    ImageIcon onIcon = new ImageIcon("src/to/mps/dashboard/on.jpg");
    ImageIcon offIcon = new ImageIcon("src/to/mps/dashboard/off.jpg");
    
	public TableModel(MonitorForDashboardInterface monitor){
		this.monitor = monitor;
	}
	
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
    	if(data != null){
    		return data.length;
    	}
    	else {
    		return 0;
    	}
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
    	return data[row][col];
    }

    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /**
     * Update the table content
     */
    public void update(JTable table){
    	List<String> systemNames = monitor.getMpsSystemsNames();

    	if(data == null || data.length != systemNames.size()){
        	data = new Object[systemNames.size()][columnNames.length];
        	fireTableDataChanged();
    	}

    	for (int i = 0; i < systemNames.size(); i++) {
			String name = systemNames.get(i);
    		data[i][0] = name;
			try {
				data[i][1] = monitor.getNumberOfProcessedRequests(name);
				data[i][2] = monitor.getUptime(name);
				data[i][3] = monitor.getDowntime(name);
				if(monitor.isAlive(name)){
					data[i][4] = onIcon;
				}
				else {
					data[i][4] = offIcon;
				}
				fireTableRowsUpdated(i, i);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

//    	if(table.getRowCount() > lastSelection){
//    		table.setRowSelectionInterval(lastSelection, lastSelection);
//    	}
    }
    
}