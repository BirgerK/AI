package to.mps.auftragskomponente.dataaccesslayer;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.common.AbstractEntity;
import to.mps.fertigungskomponente.dataaccesslayer.Bauteil;
import to.mps.fertigungskomponente.dataaccesslayer.Fertigungsauftrag;
import to.mps.managementdashboard.ManagementDashboard;



@Entity
@Table(name="Auftrag")
public class Auftrag extends AbstractEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2564439671295083364L;
	private boolean istAbgeschlossen;
	private Date beauftragtAm;
	private Angebot angebot;
	
	public Auftrag(){
		
	}
	
	public Auftrag(boolean istAbgeschlossen, Date beauftragtAm, Angebot angebot){
		super();
		this.setIstAbgeschlossen(istAbgeschlossen);
		this.setBeauftragtAm(beauftragtAm);
		this.setAngebot(angebot);
	}
	

	@Column
	public boolean isIstAbgeschlossen() {
		return istAbgeschlossen;
	}

	public void setIstAbgeschlossen(boolean istAbgeschlossen) {
		this.istAbgeschlossen = istAbgeschlossen;
		if(ManagementDashboard.exists(this)){
			ManagementDashboard.fertiggestellterAuftrag(this);
		}
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getBeauftragtAm() {
		return beauftragtAm;
	}

	public void setBeauftragtAm(Date beauftragtAm) {
		this.beauftragtAm = beauftragtAm;
	}
	
	@OneToOne(optional=true)
	@JoinColumn(name="ANGEBOT_ID")
	public Angebot getAngebot() {
		return angebot;
	}

	public void setAngebot(Angebot angebot) {
		this.angebot = angebot;
	}	
	
	public AuftragTO toTO() {
		AuftragTO auftragTO = new AuftragTO();
		
		auftragTO.setId(super.getId());
		auftragTO.setIstAbgeschlossen(istAbgeschlossen);
		auftragTO.setBeauftragtAm(beauftragtAm);
		auftragTO.setAngebot(angebot);
		
		return auftragTO;
	}
}
