package to.mps.angebotskomponente.accesslayer;

import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.angebotskomponente.dataaccesslayer.AngebotRepo;
import to.mps.managementdashboard.ManagementDashboard;

public class AngebotFacade implements AngebotServices{

	private AngebotRepo angebotRepo = new AngebotRepo();
	
	@Override
	public Angebot erstelleAngebot(Angebot angebot) {
		angebotRepo.saveAngebot(angebot);
		return angebot;
	}
}
