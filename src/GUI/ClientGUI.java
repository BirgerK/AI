package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JScrollPane;

import models.Komponente;

public class ClientGUI extends JFrame {
	private JTextField kundennummerEingebefeld;
	private DefaultListModel<Komponente> komponentenListModel;
	
	public ClientGUI() {
		setTitle("Client");
		setResizable(false);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		kundennummerEingebefeld = new JTextField();
		kundennummerEingebefeld.setBounds(127, 11, 157, 20);
		panel.add(kundennummerEingebefeld);
		kundennummerEingebefeld.setColumns(10);
		
		JTextPane kundennummerTextfeld = new JTextPane();
		kundennummerTextfeld.setEditable(false);
		kundennummerTextfeld.setText("Kundennummer");
		kundennummerTextfeld.setBounds(10, 11, 107, 20);
		panel.add(kundennummerTextfeld);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 107, 287);
		panel.add(scrollPane);
		
		komponentenListModel = new DefaultListModel<Komponente>();
		komponentenListModel.addElement(new Komponente("Schraube", 1, 5, 2));
		komponentenListModel.addElement(new Komponente("Brett", 1, 10, 4));
		komponentenListModel.addElement(new Komponente("Nasenbohrer", 1, 20, 100));
		komponentenListModel.addElement(new Komponente("Gartenlaube", 1, 100, 350));
		
		JList komponentenListBody = new JList(komponentenListModel);
		scrollPane.setViewportView(komponentenListBody);
		
		JTextPane komponentenListTitle = new JTextPane();
		komponentenListTitle.setEditable(false);
		komponentenListTitle.setText("Komponenten");
		scrollPane.setColumnHeaderView(komponentenListTitle);
	}
}
