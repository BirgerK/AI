package fertigung;

import java.util.Date;

import verkaufskomponente.Angebot;

public class Transportauftrag {

	int id;
	int kundenNr;
	Date transporttermin;
	int fertigungsauftragNr;
	String adresse;
	
	public static Transportauftrag getTransportauftrag(Angebot angebot){
		return new Transportauftrag(angebot);
	}
	
	private Transportauftrag(Angebot angebot){
		this.id = new ID().getID();
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
		this.fertigungsauftragNr = nr;
	}
}
