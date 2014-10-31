package iFertigung;

import java.util.Date;

import verkaufskomponente.Angebot;

public interface IFertigung {

	public Date berechneFertigungszeitpunkt();
	
	public Boolean erstelleFertigungsauftrag(Angebot angebot);
	
	public Boolean erstelleTransportauftrag(Angebot angebot);
	
	public Boolean erstelleKundenauftrag(Angebot angebot);
	
}
