package fertigung;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import verkaufskomponente.Angebot;
@Entity
@Table(name = "Fertigungsauftrag")
public class Fertigungsauftrag {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int fertigungsAuftragId;
	@Column
	Angebot angebot;
	@Column
	Date fertigungsBeginn;
	@Column
	Date fertigungsEnde;
	@Column
	Set<Komponente> komponenten;

	public static Fertigungsauftrag getFertigungsauftrag(Angebot angebot){
		return new Fertigungsauftrag(angebot);
	}
	
	private Fertigungsauftrag(Angebot angebot){
		this.angebot = angebot;
		this.fertigungsBeginn = new Date();
		//komponenten = angebot.getKomponenten(); Muessen wir befuellen oder im Angebot implementieren
		this.fertigungsEnde = this.berechneFertigungszeitpunkt();
	}
	
	public int getFertigungsauftragNr(){
		return this.fertigungsAuftragId;
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
