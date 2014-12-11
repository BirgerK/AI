package fertigungskomponente;

import java.util.Date;

import models.Angebot;
import models.Fertigungsauftrag;
import models.Kundenauftrag;
import models.Transportauftrag;

public interface IFertigung {

	/**Gibt den Fertigungszeitpunkt des Auftrag mit der angegebenen ID wieder
	 * @param fertigungsAuftragId ID des Auftrags
	 * @return Date-Objekt, zu dem die Fertigung abgeschlossen ist
	 */
	public Date berechneFertigungszeitpunkt(int fertigungsAuftragId);
	
	/**Erstellt einen Fertigungsauftrag anhand des Angebots und sichert ihn zeitgleich in der Persistenz
	 * @param angebot Angebot das gefertigt werden soll
	 * @return der erstellte Fertigungsauftrag, der bereits in der Persistenz ist
	 */
	public Fertigungsauftrag erstelleFertigungsauftrag(Angebot angebot);
	/**Erstellt einen Transportauftrag anhand des Angebots und sichert ihn zeitgleich in der Persistenz
	 * @param angebot Angebot das versandt werden soll
	 * @return der erstellte Transportauftrag, der bereits in der Persistenz ist
	 */
	public Transportauftrag erstelleTransportauftrag(Angebot angebot);
	/**Erstellt einen Kundenauftrag anhand des Angebots und sichert ihn zeitgleich in der Persistenz
	 * @param angebot Angebot fuer den ein Kundenauftrag erstellt werdne soll
	 * @return der erstellte Kundenauftrag, der bereits in der Persistenz ist
	 */
	public Kundenauftrag erstelleKundenauftrag(Angebot angebot);
	
}
