package iFertigung;

import java.util.Date;

import models.Angebot;
import models.Fertigungsauftrag;
import models.Kundenauftrag;
import models.Transportauftrag;

public interface IFertigung {

	public Date berechneFertigungszeitpunkt(int fertigungsAuftragId);
	
	public Fertigungsauftrag erstelleFertigungsauftrag(Angebot angebot);
	
	public Transportauftrag erstelleTransportauftrag(Angebot angebot);
	
	public Kundenauftrag erstelleKundenauftrag(Angebot angebot);
	
}
