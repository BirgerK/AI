package distributedExtension;

import java.io.IOException;
import java.net.ServerSocket;

import utils.SocketConnection;
import static utils.Constants.*;

public class StartServerService {

	private static int SERVER_PORT = START_SERVER_SERVICE_PORT;
	private static ServerSocket serverSocket = null;
	private static SocketConnection socket = null;
	private static ServerThread serverThread = null;
	private static boolean shutdown = false;
	
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
			
			switch(incomingMessage.getMethodToCall()){
			case CMD_PING:
				result = CMD_PONG;
			case CMD_START_SERVER:
				serverThread = new ServerThread(MPS_SERVER_THREAD_PORT);
				serverThread.start();
				result = ANSWER_DONE;
			case CMD_STOP_SERVER:
				if(serverThread != null & serverThread.isAlive()){
					serverThread.destroy();
				}
				result = ANSWER_DONE;
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
