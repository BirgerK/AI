package fertigung;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import verkaufskomponente.Angebot;

public class Fertigungsauftrag {
	
	int id;
	Angebot angebot;
	Date fertigungsBeginn;
	Date fertigungsEnde;
	Set<Komponente> komponenten;

	public static Fertigungsauftrag getFertigungsauftrag(Angebot angebot){
		return new Fertigungsauftrag(angebot);
	}
	
	private Fertigungsauftrag(Angebot angebot){
		this.id = new ID().getID();
		this.angebot = angebot;
		this.fertigungsBeginn = new Date();
		//komponenten = angebot.getKomponenten(); Müssen wir befüllen oder im Angebot implementieren
		this.fertigungsEnde = this.berechneFertigungszeitpunkt();
	}
	
	public int getFertigungsauftragNr(){
		return this.id;
	}
	
	public Date getFertigungsEnde(){
		return this.fertigungsEnde;
	}
	
	/*
	 * Integer = Zeit in Minuten
	 */
	public Set<Integer> getFertigungsdauer(){
		Set<Integer> result = new HashSet<Integer>();
		for(Komponente elem: komponenten){
			result.add(elem.getFertigungsdauer());
		}
		return result;
	}
	
	public Date berechneFertigungszeitpunkt() {
		Set<Integer> dauer = this.getFertigungsdauer();
		int result = 0;
		Date date = new Date();
		
		for(int i: dauer){
			result = result + i;
		}
		int timeInMlls = (date.getMinutes()+result)*1000*1000;
		date.setTime(timeInMlls);	
		
		return date;
	}
	
	public void setFertigungsEnde(Date date){
		this.fertigungsEnde = date;
	}
	
	
}
