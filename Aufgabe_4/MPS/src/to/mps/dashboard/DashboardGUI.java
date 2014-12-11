package to.mps.dashboard;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import to.mps.monitor.MonitorForDashboardInterface;

import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class DashboardGUI {
	private MonitorForDashboardInterface monitor;
	private JFrame frame;
	private JTable table;
	private TableModel tableModel; 
	
	private int lastRowIndex = 0;
	private JButton Stop;
	private JButton Start;
	private JPanel panel;
	/**
	 * Create the application.
	 */
	public DashboardGUI(MonitorForDashboardInterface monitor) {
		this.tableModel = new TableModel(monitor);
		this.monitor = monitor;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		
		table = new JTable(tableModel);

		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.setRowHeight(32);
		scrollPane.setViewportView(table);
		
		panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		Start = new JButton("Start");
		Start.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(table.getSelectedRow() != -1){
					try {
						monitor.setOnline((String)tableModel.getValueAt(table.getSelectedRow(), 0));
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		panel.add(Start);
		
		Stop = new JButton("Stop");
		Stop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(table.getSelectedRow() != -1){
					try {
						monitor.setOffline((String)tableModel.getValueAt(table.getSelectedRow(), 0));
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		panel.add(Stop);
		frame.setVisible(true);
	}
	
	public void update(){
		tableModel.update(this.table);
	}
}
