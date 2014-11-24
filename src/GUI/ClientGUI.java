package GUI;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	private DefaultListModel<Komponente> komponentenListModel;
	private JTextField angebotListTitle;
	
	public ClientGUI(Client client) {
		this.client = client;
		
		setTitle("Client");
		setResizable(false);
		
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
		
		JList komponentenListBody = new JList(komponentenListModel);
		komponentenAuswahlScrollPane.setViewportView(komponentenListBody);
		
		JTextField komponentenListTitle = new JTextField();
		komponentenListTitle.setEditable(false);
		komponentenListTitle.setText("Komponenten");
		komponentenAuswahlScrollPane.setColumnHeaderView(komponentenListTitle);
		
		JScrollPane angebotScollPane = new JScrollPane();
		angebotScollPane.setBounds(191, 57, 148, 287);
		panel.add(angebotScollPane);
		
		JList angebotListBody = new JList();
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
			}
		});
		komponenteZuAngebotButton.setBounds(10, 11, 107, 23);
		panel.add(komponenteZuAngebotButton);
		
		setVisible(true);
	}
}
