package to.mps.auftragskomponente.accesslayer;

import java.util.Date;

import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.auftragskomponente.dataaccesslayer.Auftrag;
import to.mps.auftragskomponente.dataaccesslayer.AuftragRepo;
import to.mps.managementdashboard.ManagementDashboard;


public class AuftragFacade implements AuftragServices{
	
	private AuftragRepo auftragRepo;
	
	public AuftragFacade(){
		auftragRepo = new AuftragRepo();
	}
	
	@Override
	public Auftrag erstelleAuftrag(Auftrag a) {
		auftragRepo.saveAuftrag(a);
		return a;
	}

	@Override
	public Auftrag erstelleAuftragAusAngebot(Angebot angebot) {
		Auftrag auftrag = new Auftrag(false, new Date(), angebot);
		auftragRepo.saveAuftrag(auftrag);
		return auftrag;
	}

	@Override
	public void schliesseAb(Auftrag a) {
		a.setIstAbgeschlossen(true);
	}
	
	
}
