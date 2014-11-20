package distributedExtension;

import static utils.Constants.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;

import models.Angebot;
import utils.SocketConnection;
import bossKomponente.MPS;

public class ServerThread extends Thread {
	private SocketConnection socket = null;
	private ServerSocket serverSocket = null;
	private int portNumber;
	private boolean shutdown = false;
	private MPS mps = null;
	
	public ServerThread(int portNumber){
		this.portNumber = portNumber;
		this.mps = new MPS();
	}
	
	public void run(){
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.err.println("Server Socket konnte nicht initialisiert werden.");
			shutdown = true;
		}
		
		//Server wartet auf Verbindungen von auﬂen
		while(!shutdown){
			socket = new SocketConnection();
			MethodInvokeMessage incomingMessage = null;
			Object result = null;
			
			try {
				socket.setSocket(serverSocket.accept());
			} catch (Exception e) {
				System.err.println("Fehler bei Warten auf einkommende Verbindung.");
			}
			
			try {
				incomingMessage = (MethodInvokeMessage) socket.readObject();
			} catch (Exception e) {
				System.err.println("Fehler bei Empfangen von Message-Objekt.");
			}
			
			switch(incomingMessage.getMethodToCall()){
				case CMD_ERSTELLE_KUNDENAUFTRAG:
					if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof Angebot){
						result = mps.erstelleKundenauftrag((Angebot) incomingMessage.getArgumentList().get(0));
					}
				case CMD_BERECHNE_FERTIGUNGSZEIT:
					if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof Integer){
						result = mps.berechneFertigungszeitpunkt((int) incomingMessage.getArgumentList().get(0));
					}
				case CMD_ERSTELLE_FERTIGUNGSAUFTRAG:
					if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof Angebot){
						result = mps.erstelleFertigungsauftrag((Angebot) incomingMessage.getArgumentList().get(0));
					}
				case CMD_ERSTELLE_TRANSPORTAUFTRAG:
					if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof Angebot){
						result = mps.erstelleTransportauftrag((Angebot) incomingMessage.getArgumentList().get(0));
					}
				case CMD_BERECHNE_KOSTEN:
					if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof Angebot){
						result = mps.berechneKosten((Angebot) incomingMessage.getArgumentList().get(0));
					}
				case CMD_ERSTELLE_ANGEBOT:
					if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof Map &
							incomingMessage.getArgumentList().get(1) != null & incomingMessage.getArgumentList().get(1) instanceof Integer){
						result = mps.erstelleAngebot((Map<Integer, Integer>) incomingMessage.getArgumentList().get(0),(int) incomingMessage.getArgumentList().get(1));
					}
				case CMD_PING:
					result = CMD_PONG;
			}
			
			if(result != null){	//Falls eine Operation ausgefuehrt wurde, gibt es auch ein Ergebnis und dann wird es versendet
				try {
					socket.writeObject(new ResultMessage(result));
				} catch (IOException e) {
					System.err.println("Fehler bei Schreiben des Ergebnis auf den Stream.");
				}
			} else {	//Ansonsten Nachricht an den Dispatcher, dass Aufgabe fertig ist
				try {
					socket.writeObject(new ResultMessage(ANSWER_DONE));
				} catch (IOException e) {
					System.err.println("Fehler bei Schreiben des Ergebnis auf den Stream.");
				}
			}
		}
	}
	
	
}
