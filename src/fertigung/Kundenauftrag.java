package fertigung;

import verkaufskomponente.Angebot;

public class Kundenauftrag {

	int id;
	int kundenNr;
	int FertigungsauftragNr;
	Angebot angebot;
	
	public static Kundenauftrag getKundenauftrag(Angebot angebot){
		return new Kundenauftrag(angebot);
	}
	
	private Kundenauftrag(Angebot angebot){
		this.id = new ID().getID();
		//this.kundenNr = angebot.getKundenNr();
		this.angebot = angebot;
	}
	
	public int getKundenauftragNr(){
		return this.id;
	}
	
	public void setFertigungsauftragNr(int nr){
		this.FertigungsauftragNr = nr;
	}
}
