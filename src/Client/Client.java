package Client;

import interfaces.IDispatcherToClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.Angebot;
import models.Fertigungsauftrag;
import models.Kundenauftrag;
import models.Transportauftrag;
import GUI.ClientGUI;
import bossKomponente.IMPS;
import utils.*;
import distributedExtension.*;

public class Client implements IMPS {
	
	private IDispatcherToClient dispatcher = null;
	private ClientGUI gui;
	
	//Socket aufbauen
	public Client(IDispatcherToClient dispatcher) throws UnknownHostException, IOException{
		this.dispatcher = dispatcher;
	}

	@Override
	public Date berechneFertigungszeitpunkt(int fertigungsAuftragId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fertigungsauftrag erstelleFertigungsauftrag(Angebot angebot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transportauftrag erstelleTransportauftrag(Angebot angebot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Kundenauftrag erstelleKundenauftrag(Angebot angebot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double berechneKosten(Angebot angebot) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Angebot erstelleAngebot(Map<Integer, Integer> matNrZuMenge,
			int kundenNr) {
		// TODO Auto-generated method stub
		return null;
	}

	public void start() {
		gui = new ClientGUI();
	}
}
