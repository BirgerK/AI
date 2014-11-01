package models;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
@Entity
@Table(name = "Fertigungsauftrag")
public class Fertigungsauftrag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int fertigungsAuftragId;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name="Angebot_Fertigungsauftrag", joinColumns = @JoinColumn(name="fertigungsAuftragId"), inverseJoinColumns = @JoinColumn(name="angebotId"))
	private Angebot angebot = null;
	@Column
	Date fertigungsBeginn;
	@Column
	Date fertigungsEnde;
	
	public Fertigungsauftrag(Angebot angebot){
		this.angebot = angebot;
		this.fertigungsBeginn = new Date();
		this.fertigungsEnde = this.berechneFertigungszeitpunkt(angebot.getKomponenten());
	}
	public Fertigungsauftrag(){}
	
	//### GETTER ###
	public int getFertigungsauftragNr(){
		return this.fertigungsAuftragId;
	}
	
	public Date getFertigungsEnde(){
		return this.fertigungsEnde;
	}
	
	public Angebot getAngebot(){
		return this.angebot;
	}
	
	
	/**Berechnet die Dauer der Fertigstellung aller angegebenen Komponenten
	 * @param komponenten Ein Set von Komponenten
	 * @return Dauer in ms
	 */
	public int getFertigungsdauer(Set<Komponente> komponenten){
		int SECOND_IN_MS = 1000;
		int MINUTE_IN_SECONDS = 60;
		int MINUTE_IN_MS = MINUTE_IN_SECONDS * SECOND_IN_MS;
		
		
		int result = 0;
		for(Komponente elem: komponenten){
			result += elem.getFertigungsdauer();
		}
		return (result*MINUTE_IN_MS);
	}
	
	
	
	private Date berechneFertigungszeitpunkt(Set<Komponente> komponenten) {
		int fertigungsDauer = getFertigungsdauer(komponenten);

		long fertigStellungInMS = System.currentTimeMillis() + fertigungsDauer;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(fertigStellungInMS);
		
		return calendar.getTime();
	}
	
}
