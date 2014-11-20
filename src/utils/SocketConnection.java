package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;



/**
 * Kleine Abstraktion der Klasse Socket. Zum Aufbauen von Sockets, sowie lesen und schreiben auf/von der Verbindung.
 * @author Birger Kamp, Fabian Sawatzki
 *
 */
public class SocketConnection {
	private Socket socket = null;
	private InputStream inputByteStream = null;
	private InputStream input = null;
	private ObjectInputStream inputObject = null;
	private BufferedReader inputLinewise = null;
	private OutputStream output = null;
	private ObjectOutputStream outputObject = null;
	
	
	/**
	 * Socket ohne Verbindungsdaten. Ist für ServerSocket notwendig. Socket kann anschließend manuell über setSocket() gesetzt werden.
	 */
	public SocketConnection(){
	}
	
	
	/**
	 * Initialisiert die SocketConnection mit hostName und Port. Anschließendes Ändern der Verbindungsdaten ist nicht moeglich.
	 * @param hostName DNS-Name der Gegenseite
	 * @param port Zu benutzender Port auf der Gegenseite
	 */
	public SocketConnection(String hostName, int port)throws UnknownHostException, IOException{
			socket = new Socket(hostName,port);
	}
	
	
	/**
	 * Initialisiert die SocketConnection mit IP-Adresse und Port. Anschließendes Ändern der Verbindungsdaten ist nicht moeglich.
	 * @param ipAddress IP-Adresse der Gegenseite, falls Hostname vorhanden wird dieser verwendet, ansonsten die IP-Adresse
	 * @param port Zu benutzender Port auf der Gegenseite
	 */
	public SocketConnection(InetAddress ipAddress, int port)throws UnknownHostException, IOException{
		if(ipAddress.getHostName() != null){
			socket = new Socket(ipAddress.getHostName(),port);
		} else {
			socket = new Socket(ipAddress.getHostAddress(),port);
		}
	}
	
	
	/**
	 * Erlaubt den Socket manuell zu setzen. Ist nur möglich, falls Socket bislang nicht gesetzt ist!
	 * @param newSocket neuer Socket
	 * @throws SocketException 
	 */
	public void setSocket(Socket newSocket) throws SocketException{
		if(socket == null){
			socket = newSocket;
		}
	}
	
	
	
	/**Liest die nächste Zeile des Input-Streams bis zu CRLF
	 * @return nächste String-Zeile des Input-Streams
	 * @throws IOException 
	 */
	public String readUntilCRLF() throws IOException{
		if (inputLinewise == null){
			initializeInputLinewise();
		}
		String reply = null;
		try {
            reply = inputLinewise.readLine();
        } catch (IOException e) {
            System.err.println("Connection aborted.");
        }

        return reply;
	}
	
	/**Liest ein Objekt von dem Input-Stream.
	 * @return vom Stream gelesenes Objekt
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Object readObject() throws IOException, ClassNotFoundException{
		if(inputObject == null){
			initializeObjectInput();
		}
		return inputObject.readObject();
	}
	
	
	/**Schreibt eine Zeile auf den Output-Stream. Es wird ein "\n" angehangen.
	 * @param message String-Zeile die geschrieben wird
	 * @throws IOException 
	 */
	public void writeln(String message) throws IOException{
		if(output == null){
			initializeOutput();
		}
		if(message != ""){
			byte[] byteArray = (message + "\n").getBytes("UTF-8");
			output.write(byteArray, 0, byteArray.length);
		}
	}
	
	/**Schreibt eine Zeile auf den Output-Stream. Es wird ein CRLF ("\r\n") angehangen.
	 * @param message String-Zeile die geschrieben wird
	 * @throws IOException 
	 */
	public void writeWithCRLF(String message) throws IOException{
		if(output == null){
			initializeOutput();
		}
		
		if(message != ""){
			byte[] byteArray = ("\r\n").getBytes("UTF-8");
			output.write(byteArray, 0, byteArray.length);
		}
	}
	
	/**Schreibt ein Objekt auf den Output-Stream.
	 * @param object Objekt das geschrieben wird
	 * @throws IOException 
	 */
	public void writeObject(Object object) throws IOException{
		if(outputObject == null){
			initializeObjectOutput();
		}
		outputObject.writeObject(object);
	}
	
	/** Initialisiert den Inputstream fuer zeilenweises lesen
	 * @throws IOException Inputstream konnte nicht initialisiert werden
	 */
	private void initializeInputLinewise() throws IOException{
		inputLinewise = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	/** Initialisiert den Inputstream
	 * @throws IOException Inputstream konnte nicht initialisiert werden
	 */
	private void initializeInput() throws IOException{
		input = socket.getInputStream();
	}
	
	/** Initialisiert den Inputstream fuer objektorientiertes Lesen
	 * @throws IOException Inputstream konnte nicht initialisiert werden
	 */
	private void initializeObjectInput() throws IOException{
		if(input == null){
			initializeInput();
		}
		inputObject = new ObjectInputStream(input);
	}
	

	
	
	/** Initialisiert den Outputstream
	 * @throws IOException Outputstream konnte nicht initialisiert werden
	 */
	private void initializeOutput() throws IOException{
		output = socket.getOutputStream();
	}
	
	/** Initialisiert den Outputstream fuer objektorientiertes Schreiben
	 * @throws IOException Outputstream konnte nicht initialisiert werden
	 */
	private void initializeObjectOutput() throws IOException{
		if(output == null){
			initializeOutput();
		}
		outputObject = new ObjectOutputStream(output);
	}
	
	
	/**Errechnet die interne IP-Addresse des Sockets
	 * @return interne IP-Adresse des Sockets
	 */
	public String getClientAddress(){
		return socket.getInetAddress().getHostAddress();
	}
	
	
	/**Schließt In- und Outputstream sowie den Socket
	 * @throws IOException
	 */
	public void closeConnection() throws IOException{
		if(output != null){
			output.close();
		}
		if(outputObject != null){
			outputObject.close();
		}
		if(input != null){
			input.close();
		}
		if(inputObject != null){
			inputObject.close();
		}
		if(inputByteStream != null){
			inputByteStream.close();
		}
		socket.close();
	}
}
