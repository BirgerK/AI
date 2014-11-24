package distributedExtension;

import static utils.Constants.CMD_BERECHNE_FERTIGUNGSZEIT;
import static utils.Constants.CMD_BERECHNE_KOSTEN;
import static utils.Constants.CMD_ERSTELLE_ANGEBOT;
import static utils.Constants.CMD_ERSTELLE_FERTIGUNGSAUFTRAG;
import static utils.Constants.CMD_ERSTELLE_KUNDENAUFTRAG;
import static utils.Constants.CMD_ERSTELLE_TRANSPORTAUFTRAG;
import static utils.Constants.CMD_IAMALIVE;
import static utils.Constants.CMD_PING;
import static utils.Constants.CMD_PONG;
import static utils.Constants.MPS_MONITOR_PORT;
import static utils.Constants.MPS_SERVER_SOCKET_TIMEOUT;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Map;

import exceptions.MethodNotAvailableException;
import exceptions.WrongArgumentlistException;
import models.Angebot;
import utils.SocketConnection;
import bossKomponente.MPS;

public class ServerThread extends Thread {
	private SocketConnection socketToClient = null;
	private ServerSocket serverSocket = null;
	private int portNumber;
	private boolean shutdown = false;
	private MPS mps = null;
	private InetAddress monitorAddress = null;
	
	public ServerThread(int portNumber,InetAddress monitorAddress){
		this.portNumber = portNumber;
		this.mps = new MPS();
		this.monitorAddress = monitorAddress;
	}
	
	public void run(){
		try {
			serverSocket = new ServerSocket(portNumber);
//			serverSocket.setSoTimeout(MPS_SERVER_SOCKET_TIMEOUT);
		} catch (IOException e) {
			System.err.println("ServerThread: Server Socket konnte nicht auf Port " + portNumber + " initialisiert werden.");
			shutdown = true;
		}
		
		//Server wartet auf Verbindungen von aussen
		while(!shutdown){
			socketToClient = new SocketConnection();
			MethodInvokeMessage incomingMessage = null;
			Object result = null;
			
			try {
				socketToClient.setSocket(serverSocket.accept());
			} catch (Exception e) {
				System.err.println("ServerThread: Warten auf einkommende Verbindung aufgrund Timeout abgebrochen.");
			}
			
			
			if(socketToClient.isSocketSet()){
				try {
					incomingMessage = (MethodInvokeMessage) socketToClient.readObject();
				} catch (Exception e) {
					System.err.println("Fehler bei Empfangen von Message-Objekt.");
				}
				
				switch(incomingMessage.getMethodToCall()){
					case CMD_ERSTELLE_KUNDENAUFTRAG:
						if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof Angebot){
							result = mps.erstelleKundenauftrag((Angebot) incomingMessage.getArgumentList().get(0));
						} else {
							result = new WrongArgumentlistException();
						}
						break;
					case CMD_BERECHNE_FERTIGUNGSZEIT:
						if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof Integer){
							result = mps.berechneFertigungszeitpunkt((int) incomingMessage.getArgumentList().get(0));
						} else {
							result = new WrongArgumentlistException();
						}
						break;
					case CMD_ERSTELLE_FERTIGUNGSAUFTRAG:
						if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof Angebot){
							result = mps.erstelleFertigungsauftrag((Angebot) incomingMessage.getArgumentList().get(0));
						} else {
							result = new WrongArgumentlistException();
						}
						break;
					case CMD_ERSTELLE_TRANSPORTAUFTRAG:
						if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof Angebot){
							result = mps.erstelleTransportauftrag((Angebot) incomingMessage.getArgumentList().get(0));
						} else {
							result = new WrongArgumentlistException();
						}
						break;
					case CMD_BERECHNE_KOSTEN:
						if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof Angebot){
							result = mps.berechneKosten((Angebot) incomingMessage.getArgumentList().get(0));
						} else {
							result = new WrongArgumentlistException();
						}
						break;
					case CMD_ERSTELLE_ANGEBOT:
						if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof Map &
								incomingMessage.getArgumentList().get(1) != null & incomingMessage.getArgumentList().get(1) instanceof Integer){
							result = mps.erstelleAngebot((Map<Integer, Integer>) incomingMessage.getArgumentList().get(0),(int) incomingMessage.getArgumentList().get(1));
						} else {
							result = new WrongArgumentlistException();
						}
						break;
					case CMD_PING:
						result = CMD_PONG;
						break;
					default:
						result = new MethodNotAvailableException();
				}
				
//				try {
//					socketToClient.writeObject(new ResultMessage(result));
//					socketToClient.closeConnection();
//				} catch (IOException e) {
//					System.err.println("Fehler bei Schreiben des Ergebnis auf den Stream.");
//				}
			}
			
			//Dem Monitor melden, dass der Server noch lebt
			try {
				SocketConnection socketToMonitor = new SocketConnection(monitorAddress, MPS_MONITOR_PORT);
				socketToMonitor.writeObject(new MethodInvokeMessage(CMD_IAMALIVE, null));
				socketToMonitor.closeConnection();
			} catch (Exception e) {
				System.err.println("MPS-Server: Fehler waehrend Melden am Monitor.");
			}
		}
	}
	
	
}
