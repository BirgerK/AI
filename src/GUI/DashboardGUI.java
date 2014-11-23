package GUI;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextPane;

public class DashboardGUI extends JFrame {
	Monitor monitor;
	JList<String> listOfAllServers;
	DefaultListModel<String> listOfAllServersListModel;
	JTextPane selectedServerStatusPane;
	
	public DashboardGUI(Monitor monitor) {
		super("Dashboard");
		
		this.monitor = monitor;
		
		setSize(1280, 720);
		setLocation(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnStartServer = new JButton("Start Server");
		btnStartServer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				startServer();
			}
		});
		btnStartServer.setBounds(33, 24, 118, 23);
		panel.add(btnStartServer);
		
		listOfAllServersListModel = new DefaultListModel<String>();
		listOfAllServersListModel.addElement("1");
		listOfAllServersListModel.addElement("2");
		listOfAllServersListModel.addElement("3");
		listOfAllServers = new JList<String>(listOfAllServersListModel);
		listOfAllServers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				int selectedIndex = listOfAllServers.getSelectedIndex();
				String selectedElement = listOfAllServersListModel.get(selectedIndex);
				setSelectedServer(Integer.parseInt(selectedElement));
				System.out.println(selectedElement + " selected");
			}
		});
		listOfAllServers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOfAllServers.setBounds(33, 79, 112, 567);
		panel.add(listOfAllServers);
		
		JTextPane txtpnSelectedServerStatus = new JTextPane();
		txtpnSelectedServerStatus.setEditable(false);
		txtpnSelectedServerStatus.setText("Selected Server Status:");
		txtpnSelectedServerStatus.setBounds(180, 79, 195, 23);
		panel.add(txtpnSelectedServerStatus);
		
		selectedServerStatusPane = new JTextPane();
		selectedServerStatusPane.setEditable(false);
		selectedServerStatusPane.setBounds(180, 108, 195, 23);
		panel.add(selectedServerStatusPane);
		setVisible(true);
		
	}
	
	private void startServer() {
		monitor.startServer();
		//listOfAllServersListModel.addElement("Clicky");
	}
	
	private void setSelectedServer(int serverID) {
		monitor.setSelectedServer(serverID);
	}
	
	public void setStatusOfSelectedServer(String status) {
		selectedServerStatusPane.setText(status);
	}
	
	public void refreshServerList(Set<Integer> serverList) {
		listOfAllServersListModel.clear();
		for(Integer server : serverList) {
			listOfAllServersListModel.addElement("Server No:" + server);
		}
	}
}
