package fertigung;

import java.util.Date;
import java.util.Set;

import verkaufskomponente.Angebot;

public class Auftragsverwalter {
	
	Fertigungsauftrag fertigungsauftrag;
	Kundenauftrag kundenauftrag;
	Transportauftrag transportauftrag;
	
	//Constructor
	public Auftragsverwalter(){
		
	}
	//######Methoden ########
	
	public Boolean erstelleFertigungsauftrag(Angebot angebot) {
		this.fertigungsauftrag = Fertigungsauftrag.getFertigungsauftrag(angebot);
		return true;
	}
	
	public Boolean erstelleKundensauftrag(Angebot angebot) {
		this.kundenauftrag = Kundenauftrag.getKundenauftrag(angebot);
		this.setKundeFertigungsauftragNr();
		return true;
	}
	
	public Boolean erstelleTransportauftrag(Angebot angebot) {
		this.transportauftrag = Transportauftrag.getTransportauftrag(angebot);
		this.setKundenNr();
		this.setTransportFertigungsauftragNr();
		return true;
	}
	
	//######Fertigungsauftrag Setter Getter########
	
	public Date getFertigungsdauer(){
		return this.fertigungsauftrag.getFertigungsEnde();
	}
	
	public void setFertigungsEnde(Date date){
		fertigungsauftrag.setFertigungsEnde(date);
	}
	
	//######Kundensauftrag Setter Getter########
	
	public void setKundeFertigungsauftragNr(){
		this.kundenauftrag.setFertigungsauftragNr(this.fertigungsauftrag.getFertigungsauftragNr());
	}
	
	//######Transportauftrag Setter Getter########
	
	public void setKundenNr(){
		this.transportauftrag.setKundenNr(this.kundenauftrag.getKundenauftragNr());
	}
	
	public void setLieferTermin(){
		Date termin = this.fertigungsauftrag.getFertigungsEnde();
		termin.setDate((termin.getMinutes()+ 3600)*1000*1000);
		this.transportauftrag.setLieferTermin(termin);
	}
	
	public void setTransportFertigungsauftragNr(){
		this.transportauftrag.setFertigungsauftragNr(this.fertigungsauftrag.getFertigungsauftragNr());
	}
}
