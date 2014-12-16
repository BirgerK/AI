package to.mps.auftragskomponente.dataaccesslayer;

import java.util.Date;

import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.common.TransportObject;
import to.mps.managementdashboard.ManagementDashboard;

public class AuftragTO implements TransportObject<Auftrag> {

	private int id;
	private boolean istAbgeschlossen;
	private Date beauftragtAm;
	private Angebot angebot;
	
	public AuftragTO(){
		
	}
	
	public AuftragTO(boolean istAbgeschlossen, Date beauftragtAm, Angebot angebot){
		this.setIstAbgeschlossen(istAbgeschlossen);
		this.setBeauftragtAm(beauftragtAm);
		this.setAngebot(angebot);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isIstAbgeschlossen() {
		return istAbgeschlossen;
	}

	public void setIstAbgeschlossen(boolean istAbgeschlossen) {
		this.istAbgeschlossen = istAbgeschlossen;
	}

	public Date getBeauftragtAm() {
		return beauftragtAm;
	}

	public void setBeauftragtAm(Date beauftragtAm) {
		this.beauftragtAm = beauftragtAm;
	}

	public Angebot getAngebot() {
		return angebot;
	}

	public void setAngebot(Angebot angebot) {
		this.angebot = angebot;
	}
	
	@Override
	public Auftrag toEntity() {
		Auftrag auftrag = new Auftrag();
		
		auftrag.setId(id);
		auftrag.setIstAbgeschlossen(istAbgeschlossen);
		auftrag.setBeauftragtAm(beauftragtAm);
		auftrag.setAngebot(angebot);
		
		if(!(ManagementDashboard.exists(auftrag))){
			ManagementDashboard.newAngebot(auftrag.getAngebot());
			ManagementDashboard.angebotWirdAuftrag(auftrag.getAngebot());
		}
		
		return auftrag;
	}

}
