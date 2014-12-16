package to.mps.angebotskomponente.dataaccesslayer;

import java.util.Date;

import to.mps.auftragskomponente.dataaccesslayer.Auftrag;
import to.mps.common.TransportObject;
import to.mps.fertigungskomponente.dataaccesslayer.Bauteil;
import to.mps.managementdashboard.ManagementDashboard;

public class AngebotTO implements TransportObject<Angebot>{

	private int id;
	private Date gueltigAb;
	private Date gueltigBis;
	private double preis;
	private Bauteil bauteil;
	
	public AngebotTO(){
		
	}
	
	public AngebotTO(Date gueltigAb, Date gueltigBis, double preis, Bauteil bauteil) {
		setGueltigAb(gueltigAb);
		setGueltigBis(gueltigBis);
		setPreis(preis);
		setBauteil(bauteil);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getGueltigAb() {
		return gueltigAb;
	}

	public void setGueltigAb(Date gueltigAb) {
		this.gueltigAb = gueltigAb;
	}

	public Date getGueltigBis() {
		return gueltigBis;
	}

	public void setGueltigBis(Date gueltigBis) {
		this.gueltigBis = gueltigBis;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public Bauteil getBauteil() {
		return bauteil;
	}

	public void setBauteil(Bauteil bauteil) {
		this.bauteil = bauteil;
	}


	public Angebot toEntity() {
		Angebot angebot = new Angebot();
		angebot.setId(id);
		angebot.setGueltigAb(gueltigAb);
		angebot.setGueltigBis(gueltigBis);
		angebot.setPreis(preis);
		angebot.setBauteil(bauteil);
		
		ManagementDashboard.newAngebot(angebot);
		
		return angebot;
	}

}
