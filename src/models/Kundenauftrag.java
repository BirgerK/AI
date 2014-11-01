package models;
import javax.persistence.*;
@Entity
@Table(name = "Kundenauftrag")
public class Kundenauftrag {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int kundenauftragId;
	@OneToOne
	@PrimaryKeyJoinColumn
	private Angebot angebot = null;
	
	public Kundenauftrag(Angebot angebot){
		this.angebot = angebot;
	}
	
	//### GETTER ###
	public int getKundenauftragNr(){
		return this.kundenauftragId;
	}
}
