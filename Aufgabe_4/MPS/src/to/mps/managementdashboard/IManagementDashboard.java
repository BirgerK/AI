package to.mps.managementdashboard;

import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.auftragskomponente.dataaccesslayer.Auftrag;

public interface IManagementDashboard {
	/**
	 * Erstellt ein fertiges Angebot. </br>
	 * Das uebergebene Angebot <code>angebot</code> muss fertig erstellt sein.
	 * Dabei wird ein Timestamp <code>Erstellungszeit</code> gesetzt.
	 * 
	 * @param angebot <code>Angebot</code> Zu erstellendes Angebot.
	 */
	public void newAngebot(Angebot angebot);
	
	/**
	 * Der Zahlungseingang wird registriert. Dabei wird ein neuer Timestamp 
	 * <code>Zahlungszeit</code> gesetzt.
	 * 
	 * @param auftrag <code>Auftrag</code> Auftrag fuer den ein Zahlungseingang
	 * registriert wurde.
	 */
	public void zahlungseingangFuer(Auftrag auftrag);
	
	/**
	 * Auftrag wird auf fertiggestellt gesetzt. Dabei wird ein neuer Timestamp
	 * <code>Fertigstellungszeit</code> gesetzt.
	 * 
	 * @param auftrag <code>Auftrag</code> Auftrag der in der Produktion 
	 * fertiggestellt wurde. 
	 */
	public void fertiggestellterAuftrag(Auftrag auftrag);
	
	/**
	 * Auftrag wird auf ausgeliefert gesetzt. Dabei wird ein neuer Timestamp
	 * <code>Auflieferungszeit</code> gesetzt.
	 * 
	 * @param auftrag <code>Auftrag</code> Auftrag der ausgeliefert wurde.
	 */
	public void ausgelieferterAuftrag(Auftrag auftrag);
	
	/**
	 * Setzt einen erstellten Auftrag <code>auftrag</code> auf storniert.
	 * 
	 * @param auftrag <code>Auftrag</code> Zu cancelnder Auftrag.
	 */
	public void cancelAuftrag(Auftrag auftrag);
	
}
