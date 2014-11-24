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
		int counter = 0;
		while(counter < CLIENT_MAX_RETRIES){
			try {
				socketToDispatcher = new SocketConnection(dispatcherAddress,MPS_DISPATCHER_PORT);
				socketToDispatcher.writeObject(messageToSend);
				ResultMessage resultMessage = (ResultMessage) socketToDispatcher.readObject();
				socketToDispatcher.closeConnection();	
				
				result = resultMessage.getResult();
				if(resultMessage.isResultException()){
					throw (Exception) result;
				}
			} catch (UnknownHostException e) {
				System.err.println("Client: Verbindung zum Server konnte aufgrund eines falschen Hostnamen nicht aufgebaut werden.");
				counter = CLIENT_MAX_RETRIES + 1;
			} catch (IOException e) {
				System.err.println("Client: Fehler waehrend Senden an Server. Versuch:" + counter++ + ", es wird erneut versucht.");
				Thread.sleep(CLIENT_WAITTIME_BETWEEN_RETRIES);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return result;		

	}
}
