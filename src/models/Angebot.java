package models;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Angebot")
public class Angebot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int angebotId;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Angebot_Komponente", joinColumns = { @JoinColumn(name = "angebotId") }, inverseJoinColumns = { @JoinColumn(name = "komponenteId") })  
	private Set<Komponente> komponenten = new HashSet<Komponente>();
	@Column
	private int kundenNr;
	
	//Auftraege die zu diesem Angebot gehoeren:
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="kundenAuftragId")
	private Kundenauftrag kundenAuftrag;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name="Angebot_Fertigungsauftrag", joinColumns = @JoinColumn(name="angebotId"), inverseJoinColumns = @JoinColumn(name="fertigungsAuftragId"))
	private Fertigungsauftrag fertigungsAuftrag = null;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="transportAuftragId")
	private Transportauftrag transportAuftrag;
	
	public Angebot(Set<Komponente> komponenten,int kundenNr){
		this.komponenten = komponenten;
		this.kundenNr = kundenNr;
	}
	public Angebot(){}
	
	public int getAngebotNr(){
		return this.angebotId;
	}
	
	public int getFertigungskosten(){
		int accu = 0;
		for(Komponente komponente:komponenten){
			accu +=komponente.getFertigungskosten();
		}
		return accu;
	}
	
	public Set<Komponente> getKomponenten(){
		return this.komponenten;
	}
	
	public Kundenauftrag getKundenauftrag(){
		return this.kundenAuftrag;
	}
	public Fertigungsauftrag getFertigungsauftrag(){
		return this.fertigungsAuftrag;
	}
	public Transportauftrag getTransportauftrag(){
		return this.transportAuftrag;
	}
	
	public void setKundenauftrag(Kundenauftrag newAuftrag){
		this.kundenAuftrag = newAuftrag;
	}
	public void setFertigungsauftrag(Fertigungsauftrag newAuftrag){
		this.fertigungsAuftrag = newAuftrag;
	}
	public void setTransportauftrag(Transportauftrag newAuftrag){
		this.transportAuftrag = newAuftrag;
	}
}
