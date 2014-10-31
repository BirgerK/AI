package fertigung;
import javax.persistence.*;

import verkaufskomponente.Angebot;
@Entity
@Table(name = "Kundenauftrag")
public class Kundenauftrag {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column
	private int kundenNr;
	@Column
	private Angebot angebot;
	
	public static Kundenauftrag getKundenauftrag(Angebot angebot){
		return new Kundenauftrag(angebot);
	}
	
	private Kundenauftrag(Angebot angebot){
		//this.kundenNr = angebot.getKundenNr();
		this.angebot = angebot;
	}
	
	public int getKundenauftragNr(){
		return this.id;
	}
}
