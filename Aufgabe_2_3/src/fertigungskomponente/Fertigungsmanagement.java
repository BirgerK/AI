package fertigungskomponente;
import java.util.Date;

import models.Angebot;
import models.Fertigungsauftrag;
import models.Kundenauftrag;
import models.Transportauftrag;

public class Fertigungsmanagement implements IFertigung{
	
	Auftragsverwalter auftragsverwalter;
	

	public Fertigungsmanagement(Auftragsverwalter averwalter){
		this.auftragsverwalter	= averwalter;
	}

	@Override
	public Date berechneFertigungszeitpunkt(int fertigungsAuftragId) {
		return auftragsverwalter.getFertigungsZeitpunkt(fertigungsAuftragId);
	}

	@Override
	public Fertigungsauftrag erstelleFertigungsauftrag(Angebot angebot) {
		return auftragsverwalter.erstelleFertigungsauftrag(angebot);
	}

	@Override
	public Transportauftrag erstelleTransportauftrag(Angebot angebot) {
		return auftragsverwalter.erstelleTransportauftrag(angebot);
	}

	@Override
	public Kundenauftrag erstelleKundenauftrag(Angebot angebot) {
		return auftragsverwalter.erstelleKundenauftrag(angebot);
	}
	
	
}
