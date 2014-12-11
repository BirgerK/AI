package to.mps.fertigungskomponente.dataaccesslayer;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import to.mps.common.AbstractEntity;

@Entity
public class Stueckliste extends AbstractEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5710185565116728517L;
	private Date gueltigAb;
	private Date gueltigBis;
	private Bauteil bauteil;
	private Set<StuecklistenPosition> stuecklistenPositionen;
	
	public Stueckliste(){
		
	}
	
	
	public Stueckliste(Date gueltigAb, Date gueltigBis, Set<StuecklistenPosition> stuecklistenPositionen){
		this.setGueltigAb(gueltigAb);
		this.setGueltigBis(gueltigBis);
		this.stuecklistenPositionen = stuecklistenPositionen;
	}
	
	@Column
	public Date getGueltigAb() {
		return gueltigAb;
	}

	public void setGueltigAb(Date gueltigAb) {
		this.gueltigAb = gueltigAb;
	}
	
	@Column
	public Date getGueltigBis() {
		return gueltigBis;
	}

	public void setGueltigBis(Date gueltigBis) {
		this.gueltigBis = gueltigBis;
	}

	@OneToOne(optional=true, mappedBy="stueckliste")
	public Bauteil getBauteil() {
		return bauteil;
	}

	public void setBauteil(Bauteil bauteil) {
		this.bauteil = bauteil;
	}
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="STUECKLISTE_ID")
	public Set<StuecklistenPosition> getStuecklistenPositionen() {
		return stuecklistenPositionen;
	}

	public void setStuecklistenPositionen(Set<StuecklistenPosition> stuecklistenPositionen) {
		this.stuecklistenPositionen = stuecklistenPositionen;
	}
}
