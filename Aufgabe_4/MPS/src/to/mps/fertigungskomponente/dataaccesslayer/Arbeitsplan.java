package to.mps.fertigungskomponente.dataaccesslayer;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;







import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import to.mps.common.AbstractEntity;

@Entity
public class Arbeitsplan extends AbstractEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4816010603731657256L;
	private Bauteil bauteil;
	private List<Vorgang> vorgang;
	
	public Arbeitsplan(){
		
	}
	
	public Arbeitsplan(Bauteil bauteil, List<Vorgang> vorgang){
		this.bauteil = bauteil;
		this.vorgang = vorgang;
	}
	
	@OneToOne
	@JoinColumn(name="BAUTEIL_ID")
	public Bauteil getBauteil() {
		return bauteil;
	}

	public void setBauteil(Bauteil bauteil) {
		this.bauteil = bauteil;
	}

	@OneToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name="ARBEITSPLAN_ID")
	public List<Vorgang> getVorgang() {
		return this.vorgang;
	}

	public void setVorgang(List<Vorgang> vorgang) {
		this.vorgang = vorgang;
	}
}
