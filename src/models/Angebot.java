package models;

import javax.persistence.*;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import fertigung.*;

@Entity
@Table(name = "Angebot")
public class Angebot {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int angebotId;
	@Column
	private Set<Komponente> komponenten;
	
	//Auftraege die zu diesem Angebot gehoeren:
	@OneToOne(mappedBy="Angebot", cascade = CascadeType.ALL)
	private Kundenauftrag kundenAuftrag = null;
	@OneToOne(mappedBy="Angebot", cascade = CascadeType.ALL)
	private Fertigungsauftrag fertigungsAuftrag = null;
	@OneToOne(mappedBy="Angebot", cascade = CascadeType.ALL)
	private Transportauftrag transportAuftrag = null;
	
	public Angebot(Set<Komponente> komponenten){
		this.komponenten = komponenten;
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
}
