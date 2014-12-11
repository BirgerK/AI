package to.mps.fertigungskomponente.dataaccesslayer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import to.mps.common.AbstractEntity;

@Entity
public class StuecklistenPosition extends AbstractEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 795170789568167428L;
	private int menge;
	private Stueckliste stueckliste;
	private Bauteil bauteil;
	
	public StuecklistenPosition(){
		
	}
	
	public StuecklistenPosition(int menge, Bauteil bauteil){
		this.setMenge(menge);
		this.setBauteil(bauteil);
	}
	
	@Column
	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}
	
	@ManyToOne
	public Stueckliste getStueckliste() {
		return stueckliste;
	}

	public void setStueckliste(Stueckliste stueckliste) {
		this.stueckliste = stueckliste;
	}
	
	@ManyToOne
	@JoinColumn(name="BAUTEIL_ID")
	public Bauteil getBauteil() {
		return bauteil;
	}

	public void setBauteil(Bauteil bauteil) {
		this.bauteil = bauteil;
	}
		
	
}
