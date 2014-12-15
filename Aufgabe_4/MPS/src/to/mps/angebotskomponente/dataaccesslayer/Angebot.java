package to.mps.angebotskomponente.dataaccesslayer;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import to.mps.auftragskomponente.dataaccesslayer.Auftrag;
import to.mps.common.AbstractEntity;
import to.mps.fertigungskomponente.dataaccesslayer.Bauteil;
import to.mps.managementdashboard.ManagementDashboard;


@Entity
public class Angebot extends AbstractEntity implements Serializable{
	private static final long serialVersionUID = 687286262458511183L;
	private Date gueltigAb;
	private Date gueltigBis;
	private double preis;
	private Bauteil bauteil;
	private Auftrag auftrag;
	
	public Angebot(){
		
	}
	
	public Angebot(Date gueltigAb, Date gueltigBis, double preis, Bauteil bauteil) {
		super();
		setGueltigAb(gueltigAb);
		setGueltigBis(gueltigBis);
		setPreis(preis);
		setBauteil(bauteil);
		ManagementDashboard.newAngebot(this);
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getGueltigAb() {
		return gueltigAb;
	}

	public void setGueltigAb(Date gueltigAb) {
		this.gueltigAb = gueltigAb;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getGueltigBis() {
		return gueltigBis;
	}

	public void setGueltigBis(Date gueltigBis) {
		this.gueltigBis = gueltigBis;
	}

	@Column
	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	@ManyToOne
	@JoinColumn(name="BAUTEIL_ID")
	public Bauteil getBauteil() {
		return bauteil;
	}

	public void setBauteil(Bauteil bauteil) {
		this.bauteil = bauteil;
	}

	@OneToOne(mappedBy="angebot")
	public Auftrag getAuftrag() {
		return auftrag;
	}

	public void setAuftrag(Auftrag auftrag) {
		this.auftrag = auftrag;
	}
	
	public AngebotTO toTO() {
		AngebotTO angebotTO = new AngebotTO();
		
		angebotTO.setId(super.getId());
		angebotTO.setGueltigAb(gueltigAb);
		angebotTO.setGueltigBis(gueltigBis);
		angebotTO.setPreis(preis);
		angebotTO.setBauteil(bauteil);
		
		return angebotTO;
	}
}
