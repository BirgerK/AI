package to.mps.fertigungskomponente.dataaccesslayer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import to.mps.auftragskomponente.dataaccesslayer.Auftrag;
import to.mps.common.AbstractEntity;

@Entity
public class Fertigungsauftrag extends AbstractEntity  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3579420080678236921L;
	private Bauteil bauteil;
	private int auftragNr;
	
	public Fertigungsauftrag(){
		
	}
	
	public Fertigungsauftrag(Bauteil bauteil, int auftragNr){
		this.setBauteil(bauteil);
		this.setAuftragNr(auftragNr);
	}
	
	@ManyToOne
	@JoinColumn(name="BAUTEIL_ID")
	public Bauteil getBauteil() {
		return bauteil;
	}

	public void setBauteil(Bauteil bauteil) {
		this.bauteil = bauteil;
	}
	
	@Column
	public int getAuftragNr() {
		return auftragNr;
	}

	public void setAuftragNr(int auftragNr) {
		this.auftragNr = auftragNr;
	}
}
