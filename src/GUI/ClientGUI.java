package GUI;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import models.Komponente;
import Client.Client;

public class ClientGUI extends JFrame {
	private Client client;
	
	private JTextField kundennummerEingebefeld;
	private JTextField angebotListTitle;
	private JTextField serverAddresseTitel;
	private JTextField portTitel;
	private JTextField serverAddresseEingabefeld;
	private JTextField portEingabefeld;
	
	private DefaultListModel<Komponente> komponentenListModel;
	private DefaultListModel<Komponente> angebotListModel;
	private JList<Komponente> angebotListBody;
	private JList<Komponente> komponentenListBody;
	
	public ClientGUI(Client client) {
		super("Client");
		setResizable(false);
		
		this.client = client;
		
		setSize(399, 545);
		setLocation(300, 300);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		kundennummerEingebefeld = new JTextField();
		kundennummerEingebefeld.setBounds(10, 384, 157, 20);
		panel.add(kundennummerEingebefeld);
		kundennummerEingebefeld.setColumns(10);
		
		JTextField kundennummerTextfeld = new JTextField();
		kundennummerTextfeld.setEditable(false);
		kundennummerTextfeld.setText("Kundennummer");
		kundennummerTextfeld.setBounds(10, 356, 107, 20);
		panel.add(kundennummerTextfeld);
		
		JScrollPane komponentenAuswahlScrollPane = new JScrollPane();
		komponentenAuswahlScrollPane.setBounds(10, 57, 148, 287);
		panel.add(komponentenAuswahlScrollPane);
		
		komponentenListModel = new DefaultListModel<Komponente>();
		komponentenListModel.addElement(new Komponente("Schraube", 1, 5, 2));
		komponentenListModel.addElement(new Komponente("Brett", 1, 10, 4));
		komponentenListModel.addElement(new Komponente("Nasenbohrer", 1, 20, 100));
		komponentenListModel.addElement(new Komponente("Gartenlaube", 1, 100, 350));
		
		komponentenListBody = new JList(komponentenListModel);
		komponentenAuswahlScrollPane.setViewportView(komponentenListBody);
		
		JTextField komponentenListTitle = new JTextField();
		komponentenListTitle.setEditable(false);
		komponentenListTitle.setText("Komponenten");
		komponentenAuswahlScrollPane.setColumnHeaderView(komponentenListTitle);
		
		JScrollPane angebotScollPane = new JScrollPane();
		angebotScollPane.setBounds(191, 57, 148, 287);
		panel.add(angebotScollPane);
		
		angebotListModel = new DefaultListModel<Komponente>();
		
		angebotListBody = new JList(angebotListModel);
		angebotScollPane.setViewportView(angebotListBody);
		
		angebotListTitle = new JTextField();
		angebotListTitle.setEditable(false);
		angebotListTitle.setText("Angebotsinhalt");
		angebotScollPane.setColumnHeaderView(angebotListTitle);
		angebotListTitle.setColumns(10);
		
		JButton erstelleAngebotButton = new JButton("Angebot erstellen");
		erstelleAngebotButton.setBounds(191, 11, 117, 23);
		panel.add(erstelleAngebotButton);
		
		JButton komponenteZuAngebotButton = new JButton("Hinzufuegen");
		komponenteZuAngebotButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				angebotListModel.addElement(komponentenListBody.getSelectedValue());
			}
		});
		komponenteZuAngebotButton.setBounds(10, 11, 107, 23);
		panel.add(komponenteZuAngebotButton);
		
		serverAddresseTitel = new JTextField();
		serverAddresseTitel.setEditable(false);
		serverAddresseTitel.setText("Server Addresse");
		serverAddresseTitel.setBounds(10, 453, 91, 20);
		panel.add(serverAddresseTitel);
		serverAddresseTitel.setColumns(10);
		
		portTitel = new JTextField();
		portTitel.setText("Port");
		portTitel.setEditable(false);
		portTitel.setBounds(10, 484, 91, 20);
		panel.add(portTitel);
		portTitel.setColumns(10);
		
		serverAddresseEingabefeld = new JTextField();
		serverAddresseEingabefeld.setBounds(111, 453, 117, 20);
		panel.add(serverAddresseEingabefeld);
		serverAddresseEingabefeld.setColumns(10);
		
		portEingabefeld = new JTextField();
		portEingabefeld.setBounds(111, 484, 117, 20);
		panel.add(portEingabefeld);
		portEingabefeld.setColumns(10);
		
		JButton serverHinzufuegenButton = new JButton("Server Hinzufuegen");
		serverHinzufuegenButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!serverAddresseEingabefeld.getText().equals("") && !portEingabefeld.getText().equals("")) {
					InetAddress address;
					
					try {
						address = InetAddress.getByName(serverAddresseEingabefeld.getText());
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					}
					
					int port = Integer.parseInt(portEingabefeld.getText());
					
					try {
						addServer(address, port);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		serverHinzufuegenButton.setBounds(238, 452, 138, 23);
		panel.add(serverHinzufuegenButton);
		
		setVisible(true);
	}
	
	private void addServer(InetAddress address, int port) throws Exception {
		client.addServer(address, port);
	}
}
