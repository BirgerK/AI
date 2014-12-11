package to.mps.fertigungskomponente.dataaccesslayer;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;




import javax.persistence.OneToOne;

import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.auftragskomponente.dataaccesslayer.Auftrag;
import to.mps.common.AbstractEntity;

@Entity(name="Bauteil")
public class Bauteil extends AbstractEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1684280134988923344L;
	private String name;
	private List<Angebot> angebote;
	private Stueckliste stueckliste;
	private List<StuecklistenPosition> stuecklistenPositionen;
	private Arbeitsplan arbeitsplan;
	private List<Fertigungsauftrag> fertigungsauftrag;
	
	public Bauteil(){
		
	}
	
	public Bauteil(String name, Stueckliste stueckliste){
		super();
		this.name = name;
		this.stueckliste = stueckliste;
	}
	

	@OneToOne(optional=true)
	@JoinColumn(name="STUECKLISTE_ID", unique=true, nullable=true)
	public Stueckliste getStueckliste() {
		return this.stueckliste;
	}
	
	public void setStueckliste(Stueckliste stueckliste){
		this.stueckliste = stueckliste;
	}
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="bauteil")
	public List<Angebot> getAngebote(){
		return angebote;
	}
	
	public void setAngebote(List<Angebot> angebote){
		this.angebote = angebote;
	}

	@OneToMany(mappedBy="bauteil", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	public List<StuecklistenPosition> getStuecklistenPositionen() {
		return stuecklistenPositionen;
	}


	public void setStuecklistenPositionen(List<StuecklistenPosition> stuecklistenPositionen) {
		this.stuecklistenPositionen = stuecklistenPositionen;
	}

	@OneToOne(mappedBy="bauteil")
	public Arbeitsplan getArbeitsplan() {
		return arbeitsplan;
	}


	public void setArbeitsplan(Arbeitsplan arbeitsplan) {
		this.arbeitsplan = arbeitsplan;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="bauteil")
	public List<Fertigungsauftrag> getFertigungsauftrag() {
		return fertigungsauftrag;
	}


	public void setFertigungsauftrag(List<Fertigungsauftrag> fertigungsauftrag) {
		this.fertigungsauftrag = fertigungsauftrag;
	}

	@Column
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
}
