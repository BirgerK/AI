package models;
import javax.persistence.*;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
@Entity
@Table(name = "Transportauftrag")
public class Transportauftrag {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int transportAuftragid;
	@Column
	private Date transporttermin = null;;
	@OneToOne
	@JoinColumn(name="angebotId")
	private Angebot angebot = null;
	
	public Transportauftrag(Angebot angebot){
		this.angebot = angebot;
		angebot.setTransportauftrag(this);
	}
	public Transportauftrag(){}
	
	//### GETTER ###
	public int getTransportauftragNr(){
		return this.transportAuftragid;
	}
}
