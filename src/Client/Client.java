package Client;

import static utils.Constants.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import models.Angebot;
import models.Fertigungsauftrag;
import models.Kundenauftrag;
import models.Transportauftrag;
import utils.SocketConnection;
import GUI.ClientGUI;
import bossKomponente.IMPS;
import distributedExtension.MethodInvokeMessage;
import distributedExtension.ResultMessage;

public class Client{
	
	private InetAddress dispatcherAddress = null;
	ClientGUI gui;
	
	//Socket aufbauen
	public Client(InetAddress dispatcherAddress) throws UnknownHostException, IOException{
		gui = new ClientGUI(this);
		this.dispatcherAddress = dispatcherAddress;
	}

	public Date berechneFertigungszeitpunkt(int fertigungsAuftragId) throws Exception {
		return (Date) sendRequestToServerAndGetAnswer(new MethodInvokeMessage(CMD_BERECHNE_FERTIGUNGSZEIT, new ArrayList<Object>(Arrays.asList(fertigungsAuftragId))));
	}

	public Fertigungsauftrag erstelleFertigungsauftrag(Angebot angebot) throws Exception {
		return (Fertigungsauftrag) sendRequestToServerAndGetAnswer(new MethodInvokeMessage(CMD_ERSTELLE_FERTIGUNGSAUFTRAG, new ArrayList<Object>(Arrays.asList(angebot))));
	}

	public Transportauftrag erstelleTransportauftrag(Angebot angebot) throws Exception {
		return (Transportauftrag) sendRequestToServerAndGetAnswer(new MethodInvokeMessage(CMD_ERSTELLE_TRANSPORTAUFTRAG, new ArrayList<Object>(Arrays.asList(angebot))));
	}

	public Kundenauftrag erstelleKundenauftrag(Angebot angebot) throws Exception {
		return (Kundenauftrag) sendRequestToServerAndGetAnswer(new MethodInvokeMessage(CMD_ERSTELLE_KUNDENAUFTRAG, new ArrayList<Object>(Arrays.asList(angebot))));
	}

	public double berechneKosten(Angebot angebot) throws Exception {
		return (double) sendRequestToServerAndGetAnswer(new MethodInvokeMessage(CMD_BERECHNE_KOSTEN, new ArrayList<Object>(Arrays.asList(angebot))));
	}

	public Angebot erstelleAngebot(Map<Integer, Integer> matNrZuMenge,int kundenNr) throws Exception {
		return (Angebot) sendRequestToServerAndGetAnswer(new MethodInvokeMessage(CMD_ERSTELLE_ANGEBOT, new ArrayList<Object>(Arrays.asList(matNrZuMenge,kundenNr))));
	}
	
	public void addServer(InetAddress serverAddresse, int portNumber) throws Exception{
		sendRequestToServerAndGetAnswer(new MethodInvokeMessage(CMD_ADD_SERVER, new ArrayList<Object>(Arrays.asList(serverAddresse,portNumber))));
	}

	public void start() {
	}
	
	private Object sendRequestToServerAndGetAnswer(MethodInvokeMessage messageToSend) throws Exception{
		Object result = null;
		SocketConnection socketToDispatcher;

		socketToDispatcher = new SocketConnection(dispatcherAddress,MPS_DISPATCHER_PORT);
		socketToDispatcher.writeObject(messageToSend);
		ResultMessage resultMessage = (ResultMessage) socketToDispatcher.readObject();
		socketToDispatcher.closeConnection();	
		
		result = resultMessage.getResult();
		if(resultMessage.isResultException()){
			throw (Exception) result;
		}
		
		
		return result;		

	}
}
