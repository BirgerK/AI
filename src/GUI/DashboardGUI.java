package GUI;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
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

import javax.swing.JTextField;

public class DashboardGUI extends JFrame {
	Monitor monitor;
	JList<String> listOfAllServers;
	DefaultListModel<String> listOfAllServersListModel;
	JTextPane selectedServerStatusPane;
	JTextField idleTextPane;
	JTextField busyTextPane;
	private JTextField txtAvailableServers;
	private JTextField dispatcherStatusTextField;
	
	public DashboardGUI(Monitor monitor) {
		super("Dashboard");
		setResizable(false);
		
		this.monitor = monitor;
		
		setSize(428, 310);
		setLocation(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnStartServer = new JButton("Start Server");
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
		btnStartServer.setBounds(155, 22, 112, 23);
		panel.add(btnStartServer);
		
		listOfAllServersListModel = new DefaultListModel<String>();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 24, 112, 238);
		panel.add(scrollPane);
		listOfAllServers = new JList<String>(listOfAllServersListModel);
		scrollPane.setViewportView(listOfAllServers);
		listOfAllServers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(!listOfAllServers.isSelectionEmpty()) {
					String selectedElement = listOfAllServers.getSelectedValue();
					setSelectedServer(Integer.parseInt(selectedElement));
				} else {
					setSelectedServer(0);
				}
			}
		});
		listOfAllServers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		txtAvailableServers = new JTextField();
		txtAvailableServers.setEditable(false);
		txtAvailableServers.setText("Available Servers");
		scrollPane.setColumnHeaderView(txtAvailableServers);
		txtAvailableServers.setColumns(10);
		
		JTextField txtpnSelectedServerStatus = new JTextField();
		txtpnSelectedServerStatus.setEditable(false);
		txtpnSelectedServerStatus.setText("Selected Server Status:");
		txtpnSelectedServerStatus.setBounds(155, 62, 234, 23);
		panel.add(txtpnSelectedServerStatus);
		
		selectedServerStatusPane = new JTextPane();
		selectedServerStatusPane.setEditable(false);
		selectedServerStatusPane.setBounds(155, 96, 234, 23);
		panel.add(selectedServerStatusPane);
		
		JButton killServerButton = new JButton("Stop Server");
		killServerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				killServer(Integer.parseInt(listOfAllServers.getSelectedValue()));
			}
		});
		killServerButton.setBounds(277, 22, 112, 23);
		panel.add(killServerButton);
		
		idleTextPane = new JTextField();
		idleTextPane.setEditable(false);
		idleTextPane.setBounds(155, 208, 102, 23);
		panel.add(idleTextPane);
		
		busyTextPane = new JTextField();
		busyTextPane.setEditable(false);
		busyTextPane.setBounds(287, 208, 102, 23);
		panel.add(busyTextPane);
		
		dispatcherStatusTextField = new JTextField();
		dispatcherStatusTextField.setEditable(false);
		dispatcherStatusTextField.setBounds(156, 242, 233, 20);
		panel.add(dispatcherStatusTextField);
		dispatcherStatusTextField.setColumns(10);
		setVisible(true);
		
	}
	
	private void startServer() {
		if(!listOfAllServers.isSelectionEmpty()) {
			monitor.startServer(Integer.parseInt(listOfAllServers.getSelectedValue()));
		}
	}
	
	private void killServer(int serverID) {
		if(!listOfAllServers.isSelectionEmpty()) {
			monitor.killServer(serverID);
		}
	}
	
	private void setSelectedServer(int serverID) {
		monitor.setSelectedServer(serverID);
	}
	
	synchronized public void setIdleAmount(int noOfIdleServers) {
		idleTextPane.setText("Idle: " + noOfIdleServers);
	}
	
	synchronized public void setBusyAmount(int noOfBusyServers) {
		busyTextPane.setText("Busy: " + noOfBusyServers);
	}
	
	synchronized public void setDispatcherStatus(boolean alive) {
		if(alive) {
			dispatcherStatusTextField.setText("Dispatcher Status: Alive");
		} else {
			dispatcherStatusTextField.setText("Dispatcher Status: Dead");
		}
	}
	
	synchronized public void setStatusOfSelectedServer(String status) {
		if(status == null) {
			selectedServerStatusPane.setText("Not available");
		} else {
			selectedServerStatusPane.setText(status);
		}
	}
	
	synchronized public void refreshServerList(Set<Integer> serverList) {
		int selectedIndex = listOfAllServers.getSelectedIndex();
		listOfAllServersListModel.clear();
		for(Integer server : serverList) {
			listOfAllServersListModel.addElement(Integer.toString(server));
		}
		if(selectedIndex < listOfAllServersListModel.getSize()) {
			listOfAllServers.setSelectedIndex(selectedIndex);
		} else {
			listOfAllServers.setSelectedIndex(listOfAllServersListModel.getSize() - 1);
		}
	}
	
	synchronized public void serverDied(int serverID) {
		JOptionPane.showInputDialog("Sir, Server " + serverID + " just died");
	}
}
