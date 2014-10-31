package fertigung;
import javax.persistence.*;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import verkaufskomponente.Angebot;
@Entity
@Table(name = "Transportauftrag")
public class Transportauftrag {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column
	private int kundenNr;
	@Column
	private Date transporttermin;
	@OneToOne
	@JoinColumn(name="fertigungsauftragId")
	private Fertigungsauftrag fertigungsauftrag;
	@Column
	private String adresse;
	
	public static Transportauftrag getTransportauftrag(Angebot angebot){
		return new Transportauftrag(angebot);
	}
	
	private Transportauftrag(Angebot angebot){
		//this.adresse = angebot.getLieferAdresse();
	}
	
	public int getTransportauftragNr(){
		return this.id;
	}
	
	//###Setter####
	public void setKundenNr(int nr){
		this.kundenNr = nr;
	}
	
	public void setLieferTermin(Date date){
		this.transporttermin = date;
	}
	
	public void setFertigungsauftragNr(int nr){
		this.fertigungsauftrag.getFertigungsauftragNr();
	}
}
