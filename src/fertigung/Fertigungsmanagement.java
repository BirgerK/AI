package fertigung;
import java.util.Date;
import java.util.Set;

import verkaufskomponente.Angebot;
import iFertigung.IFertigung;

public class Fertigungsmanagement implements IFertigung{
	
	Auftragsverwalter auftragsverwalter;
	

	public Fertigungsmanagement(Auftragsverwalter averwalter){
		this.auftragsverwalter	= averwalter;
	}

	@Override
	public Date berechneFertigungszeitpunkt() {
		Set<Integer> dauer = auftragsverwalter.getFertigungsdauer();
		int result = 0;
		Date date = new Date();
		
		for(int i: dauer){
			result = result + i;
		}
		int timeInMlls = (date.getMinutes()+result)*1000*1000;
		date.setTime(timeInMlls);	
		
		return date;
	}

	@Override
	public Boolean erstelleFertigungsauftrag(Angebot angebot) {
		return auftragsverwalter.erstelleFertigungsauftrag(angebot);
	}

	@Override
	public Boolean erstelleTransportauftrag(Angebot angebot) {
		return auftragsverwalter.erstelleTransportauftrag(angebot);
	}

	@Override
	public Boolean erstelleKundenauftrag(Angebot angebot) {
		return auftragsverwalter.erstelleKundensauftrag(angebot);
	}
	
	public void setTransporttermin(){
		auftragsverwalter.setLieferTermin();
	}
	
	
}
