package distributedExtension;

import java.util.List;

public interface IMonitoring {
	/**Gibt eine List wieder die ID's enthaelt, mit denen in anderen Funktionen auf jeden Server speziell zugegriffen werden kann.
	 * @return Liste mit Server-ID's
	 */
	public List<Integer> getListOfAllServers();
	
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
	 * @return 'Busy' falls Server gerade am Arbeiten ist, 'Idle' falls Server gerade keinen Job bearbeitet, '
	 */
	public String statusOfServer(int id);
	
	/**Startet den Server.
	 * @param id ID des Server, der gestartet werden soll
	 */
	public void startServer(int id);
	
	/**Stoppt den Server
	 * @param id ID des Server, der gestoppt werden soll
	 */
	public void stopServer(int id);
}
