package models;
import javax.persistence.*;

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
	
	/*
	 * Integer = Zeit in Minuten
	 */
	public Set<Integer> getFertigungsdauer(Set<Komponente> komponenten){
		Set<Integer> result = new HashSet<Integer>();
		for(Komponente elem: komponenten){
			result.add(elem.getFertigungsdauer());
		}
		return result;
	}
	
	
	
	private Date berechneFertigungszeitpunkt(Set<Komponente> komponenten) {
		Set<Integer> dauer = this.getFertigungsdauer(komponenten);
		int result = 0;
		Date date = new Date();
		
		for(int i: dauer){
			result = result + i;
		}
		int timeInMlls = (date.getMinutes()+result)*1000*1000;
		date.setTime(timeInMlls);	
		
		return date;
	}
	
}
