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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DashboardGUI extends JFrame {
	Monitor monitor;
	JList<String> listOfAllServers;
	DefaultListModel<String> listOfAllServersListModel;
	JTextPane selectedServerStatusPane;
	JTextPane idleTextPane;
	JTextPane busyTextPane;
	
	public DashboardGUI(Monitor monitor) {
		super("Dashboard");
		
		this.monitor = monitor;
		
		setSize(428, 310);
		setLocation(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnStartServer = new JButton("Start new Server");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnStartServer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				startServer();
			}
		});
		btnStartServer.setBounds(33, 24, 118, 23);
		panel.add(btnStartServer);
		
		listOfAllServersListModel = new DefaultListModel<String>();
		listOfAllServersListModel.addElement("1");
		listOfAllServersListModel.addElement("2");
		listOfAllServersListModel.addElement("3");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 79, 112, 183);
		panel.add(scrollPane);
		listOfAllServers = new JList<String>(listOfAllServersListModel);
		scrollPane.setViewportView(listOfAllServers);
		listOfAllServers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				int selectedIndex = listOfAllServers.getSelectedIndex();
				String selectedElement = listOfAllServersListModel.get(selectedIndex);
				setSelectedServer(Integer.parseInt(selectedElement));
				System.out.println(selectedElement + " selected");
			}
		});
		listOfAllServers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JTextPane txtpnSelectedServerStatus = new JTextPane();
		txtpnSelectedServerStatus.setEditable(false);
		txtpnSelectedServerStatus.setText("Selected Server Status:");
		txtpnSelectedServerStatus.setBounds(180, 79, 195, 23);
		panel.add(txtpnSelectedServerStatus);
		
		selectedServerStatusPane = new JTextPane();
		selectedServerStatusPane.setEditable(false);
		selectedServerStatusPane.setBounds(180, 113, 195, 23);
		panel.add(selectedServerStatusPane);
		
		JButton killServerButton = new JButton("Kill Server");
		killServerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				killServer(Integer.parseInt(listOfAllServers.getSelectedValue()));
			}
		});
		killServerButton.setBounds(180, 147, 118, 23);
		panel.add(killServerButton);
		
		idleTextPane = new JTextPane();
		idleTextPane.setEditable(false);
		idleTextPane.setBounds(180, 24, 87, 23);
		panel.add(idleTextPane);
		
		busyTextPane = new JTextPane();
		busyTextPane.setEditable(false);
		busyTextPane.setBounds(288, 24, 87, 23);
		panel.add(busyTextPane);
		setVisible(true);
		
	}
	
	private void startServer() {
		monitor.startServer();
		//listOfAllServersListModel.addElement("Clicky");
	}
	
	private void killServer(int serverID) {
		monitor.killServer(serverID);
	}
	
	private void setSelectedServer(int serverID) {
		monitor.setSelectedServer(serverID);
	}
	
	public void setIdleAmount(int noOfIdleServers) {
		idleTextPane.setText("Idle: " + noOfIdleServers);
	}
	
	public void setBusyAmount(int noOfBusyServers) {
		busyTextPane.setText("Busy: " + noOfBusyServers);
	}
	
	public void setStatusOfSelectedServer(String status) {
		if(status == null) {
			selectedServerStatusPane.setText("Not available");
		} else {
			selectedServerStatusPane.setText(status);
		}
	}
	
	public void refreshServerList(Set<Integer> serverList) {
		listOfAllServersListModel.clear();
		for(Integer server : serverList) {
			listOfAllServersListModel.addElement("Server No:" + server);
		}
	}
}
