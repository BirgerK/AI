package bossKomponente;

import java.util.Date;
import java.util.Map;

import models.Angebot;
import models.Fertigungsauftrag;
import models.Kundenauftrag;
import models.Transportauftrag;
import verkaufskomponente.Verkaufsmanagement;
import verkaufskomponente.Verkaufsverwalter;
import fertigungskomponente.Auftragsverwalter;
import fertigungskomponente.Fertigungsmanagement;

public class MPS implements IMPS{

	private Fertigungsmanagement fertigung = null;
	private Verkaufsmanagement verkauf = null;
	
	public MPS(){
		fertigung = new Fertigungsmanagement(new Auftragsverwalter());
		verkauf = new Verkaufsmanagement(new Verkaufsverwalter());
	}
	
	@Override
	public Date berechneFertigungszeitpunkt(int fertigungsAuftragId) {
		return fertigung.berechneFertigungszeitpunkt(fertigungsAuftragId);
	}

	@Override
	public Fertigungsauftrag erstelleFertigungsauftrag(Angebot angebot) {
		return fertigung.erstelleFertigungsauftrag(angebot);
	}

	@Override
	public Transportauftrag erstelleTransportauftrag(Angebot angebot) {
		return fertigung.erstelleTransportauftrag(angebot);
	}

	@Override
	public Kundenauftrag erstelleKundenauftrag(Angebot angebot) {
		return fertigung.erstelleKundenauftrag(angebot);
	}

	@Override
	public double berechneKosten(Angebot angebot) {
		return verkauf.berechneKosten(angebot);
	}

	@Override
	public Angebot erstelleAngebot(Map<Integer, Integer> matNrZuMenge,int kundenNr) {
		return verkauf.erstelleAngebot(matNrZuMenge, kundenNr);
	}

}
