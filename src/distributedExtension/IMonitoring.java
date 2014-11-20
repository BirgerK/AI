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
	public int getBusyServer();
	
	/**Gibt den Status des Servers wieder. Die ID's lassen sich per 'getListOfAllServers()' abrufen.
	 * @param id eindeutige ID des Servers
	 * @return TRUE falls Server gerade am Arbeiten ist, FALSE falls Server gerade keinen Job bearbeitet.
	 */
	public boolean statusOfServer(int id);
}
