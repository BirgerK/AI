package fertigungskomponente;

import java.util.Date;

import models.Angebot;
import models.Fertigungsauftrag;
import models.Kundenauftrag;
import models.Transportauftrag;

import static utils.HibernateMaster.*;

public class Auftragsverwalter {
	
	//Constructor
	public Auftragsverwalter(){
		initializeHibernate();
	}
	//######Methoden ########
	
	public Fertigungsauftrag erstelleFertigungsauftrag(Angebot angebot) {
		Fertigungsauftrag temp = new Fertigungsauftrag(angebot);
		persistObject(temp);
		return temp;
	}
	
	public Kundenauftrag erstelleKundenauftrag(Angebot angebot) {
		Kundenauftrag temp = new Kundenauftrag(angebot);
		persistObject(temp);
		return temp;
	}
	
	public Transportauftrag erstelleTransportauftrag(Angebot angebot) {
		Transportauftrag temp = new Transportauftrag(angebot);
		persistObject(temp);
		return temp;
	}
	
	public Date getFertigungsZeitpunkt(int fertigungsAuftragId){
		Fertigungsauftrag receivedAuftrag = (Fertigungsauftrag) loadObject(Fertigungsauftrag.class, fertigungsAuftragId);
		return receivedAuftrag.getFertigungsEnde();
	}
}
