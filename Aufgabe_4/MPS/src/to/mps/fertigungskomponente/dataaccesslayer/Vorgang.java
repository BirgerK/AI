package to.mps.fertigungskomponente.dataaccesslayer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import to.mps.common.AbstractEntity;
import to.mps.fertigungskomponente.datatypes.VorgangArtType;

@Entity
public class Vorgang extends AbstractEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4130434145071429803L;
	private VorgangArtType art;
	private int ruestzeit;
	private int maschinenzeit;
	private int personenzeit;
	
	private Arbeitsplan arbeitsplan;
	
	public Vorgang(){
		
	}
	
	public Vorgang(VorgangArtType Art, int Restzeit, int Maschinenzeit, int Personenzeit) {
		this.art = Art;
		this.ruestzeit = Restzeit;
		this.maschinenzeit = Maschinenzeit;
		this.personenzeit = Personenzeit;
	}
	
	@ManyToOne
	@JoinColumn(name="ARBEITSPLAN_ID")
	public Arbeitsplan getArbeitsplan() {
		return this.arbeitsplan;
	}
	
	public void setArbeitsplan(Arbeitsplan Arbeitsplan) {
		this.arbeitsplan = Arbeitsplan;
	}

	@Enumerated(EnumType.STRING)
	public VorgangArtType getArt() {
		return this.art;
	}

	public void setArt(VorgangArtType art) {
		this.art = art;
	}

	@Column
	public int getRestzeit() {
		return this.ruestzeit;
	}

	public void setRestzeit(int Restzeit) {
		this.ruestzeit = Restzeit;
	}

	@Column
	public int getMaschinenzeit() {
		return this.maschinenzeit;
	}

	public void setMaschinenzeit(int Maschinenzeit) {
		this.maschinenzeit = Maschinenzeit;
	}

	@Column
	public int getPersonenzeit() {
		return this.personenzeit;
	}

	public void setPersonenzeit(int Personenzeit) {
		this.personenzeit = Personenzeit;
	}
}
