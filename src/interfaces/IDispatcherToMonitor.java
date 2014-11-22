package interfaces;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

import exceptions.CouldNotStartServerException;

public interface IDispatcherToMonitor {
	/**Gibt eine List wieder die ID's enthaelt, mit denen in anderen Funktionen auf jeden Server speziell zugegriffen werden kann.
	 * @return Liste mit Server-ID's
	 */
	public Set<Integer> getListOfAllServers();
	
	/**Gibt die Anzahl der Server wieder, die gerade keinen Job zu erledigen haben.
	 * @return Anzahl der arbeitslosen Server
	 */
	public int getAmountIdleServer();
	
	/**Gibt die Anzahl der Server wieder, die gerade einen Job bearbeiten.
	 * @return Anzahl der arbeitenden Server
	 */
	public int getAmountBusyServer();
	
	/**Gibt den Status des Servers wieder. Die ID's lassen sich per 'getListOfAllServers()' abrufen.
	 * @param id eindeutige ID des Servers
	 * @return 'Busy' falls Server gerade am Arbeiten ist, 'Idle' falls Server gerade keinen Job bearbeitet, 'Offline' falls noch gar nicht gestartet, null falls id nicht vorhanden
	 */
	public String statusOfServer(int id);
	
	/**Startet den Server.
	 * @param id ID des Server, der gestartet werden soll
	 * @param monitorAddress IP-Adresse oder Hostname des Monitorservers an dem sich der MPS-Server melden soll
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws ClassNotFoundException 
	 * @throws CouldNotStartServerException 
	 */
	public void startServer(int id,InetAddress monitorAddress) throws UnknownHostException, IOException, ClassNotFoundException, CouldNotStartServerException;
	
	/**Stoppt den Server
	 * @param id ID des Server, der gestoppt werden soll
	 * @param serverStatus Status des Servers. Nur Stati aus der Konstanten-Klasse verwenden! (Fangen alle mit 'STATUS' an)
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws CouldNotStartServerException 
	 */
	public void stopServer(int id) throws IOException, ClassNotFoundException, CouldNotStartServerException;
	
	public void setServerStatus(InetAddress serverAddress,String serverStatus);
}
