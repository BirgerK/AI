package fertigung;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import models.Angebot;
import models.Fertigungsauftrag;
import models.Kundenauftrag;
import models.Transportauftrag;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

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
	
	//######Fertigungsauftrag Setter Getter########
	
	public Date getFertigungsZeitpunkt(int fertigungsAuftragId){
		Fertigungsauftrag receivedAuftrag = (Fertigungsauftrag) loadObject(Fertigungsauftrag.class, fertigungsAuftragId);
		return receivedAuftrag.getFertigungsEnde();
	}
}
