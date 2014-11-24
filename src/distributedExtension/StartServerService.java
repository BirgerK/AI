package distributedExtension;

import static utils.Constants.ANSWER_DONE;
import static utils.Constants.CMD_PING;
import static utils.Constants.CMD_PONG;
import static utils.Constants.CMD_START_SERVER;
import static utils.Constants.CMD_STOP_SERVER;
import static utils.Constants.MPS_SERVER_THREAD_PORT;
import static utils.Constants.START_SERVER_SERVICE_PORT;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import utils.SocketConnection;
import exceptions.WrongArgumentlistException;

public class StartServerService {

	private static int SERVER_PORT = START_SERVER_SERVICE_PORT;
	private static ServerSocket serverSocket = null;
	private static SocketConnection socket = null;
	private static ServerThread serverThread = null;
	private static boolean shutdown = false;
	private static Map<Integer,Thread> serverThreads = Collections.synchronizedMap(new HashMap<Integer,Thread>());
	
	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket (SERVER_PORT);
		} catch (IOException e) {
			System.err.println("Server Socket konnte nicht initialisiert werden.");
			shutdown = true;
		}
		
		while(!shutdown){
			socket = new SocketConnection();
			MethodInvokeMessage incomingMessage = null;
			Object result = null;
			try {
				socket.setSocket(serverSocket.accept());
			} catch (Exception e) {
				System.err.println("Fehler bei Warten auf einkommende Verbindung.");
			}
			
			//Nachricht von der Leitung lesen
			try {
				incomingMessage = (MethodInvokeMessage) socket.readObject();
			} catch (Exception e) {
				System.err.println("Fehler bei Empfangen von Message-Objekt.");
			} 
			if(incomingMessage != null){
				switch(incomingMessage.getMethodToCall()){
				case CMD_PING:
					result = CMD_PONG;
					break;
				case CMD_START_SERVER:
					if(incomingMessage.getArgumentList().size() == 1){
						if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof InetAddress){
							serverThread = new ServerThread(MPS_SERVER_THREAD_PORT,(InetAddress) incomingMessage.getArgumentList().get(0));
							serverThread.start();
							result = ANSWER_DONE;
						} else {
							result = new ResultMessage(new WrongArgumentlistException());
						}
					} else {
						if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof Integer &
							incomingMessage.getArgumentList().get(1) != null & incomingMessage.getArgumentList().get(1) instanceof InetAddress) {
							serverThread = new ServerThread((int) incomingMessage.getArgumentList().get(0),(InetAddress) incomingMessage.getArgumentList().get(1));
							serverThread.start();
							serverThreads.put((int) incomingMessage.getArgumentList().get(1), serverThread);
							result = ANSWER_DONE;
						} else {
							result = new ResultMessage(new WrongArgumentlistException());
						}
					}
					break;
				case CMD_STOP_SERVER:
					if(incomingMessage.getArgumentList().get(0) != null & incomingMessage.getArgumentList().get(0) instanceof Integer &
					serverThread != null & serverThread.isAlive()){
						serverThreads.get(incomingMessage.getArgumentList().get(0)).destroy();
					}
					result = ANSWER_DONE;
				}
				
				if(result != null){	//Falls eine Operation ausgefuehrt wurde, gibt es auch ein Ergebnis und dann wird es versendet
					try {
						socket.writeObject(new ResultMessage(result));
						socket.closeConnection();
					} catch (IOException e) {
						System.err.println("Fehler bei Schreiben des Ergebnis auf den Stream.");
					}
				} else {	//Ansonsten Nachricht an den Dispatcher, dass Aufgabe fertig ist
					try {
						socket.writeObject(new ResultMessage(ANSWER_DONE));
						socket.closeConnection();
					} catch (IOException e) {
						System.err.println("Fehler bei Schreiben des Ergebnis auf den Stream.");
					}
				}
			}
		}
		System.out.println("StartServerService wird heruntergefahren.");
	}
}
