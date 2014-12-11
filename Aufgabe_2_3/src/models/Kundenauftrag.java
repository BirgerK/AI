package models;
import javax.persistence.*;
@Entity
@Table(name = "Kundenauftrag")
public class Kundenauftrag {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int kundenAuftragId;
	@OneToOne
	@JoinColumn(name="angebotId")
	private Angebot angebot = null;
	
	public Kundenauftrag(Angebot angebot){
		this.angebot = angebot;
		this.angebot.setKundenauftrag(this);
	}
	public Kundenauftrag(){}
	
	//### GETTER ###
	public int getKundenauftragNr(){
		return this.kundenAuftragId;
	}
}
