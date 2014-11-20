package distributedExtension;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import bossKomponente.IMPS;

public interface IDispatcher extends IMPS{
	/**Teilt dem Dispatcher mit, wo er noch einen Server findet.
	 * @param serverAddresse Host-Name oder IP-Adresse des Servers muessen angegeben sein
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws ClassNotFoundException 
	 */
	public void addServer(InetAddress serverAddresse) throws UnknownHostException, IOException, ClassNotFoundException;
}
