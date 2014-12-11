package to.mps.fertigungskomponente.accesslayer;

import to.mps.auftragskomponente.dataaccesslayer.Auftrag;
import to.mps.fertigungskomponente.dataaccesslayer.Arbeitsplan;
import to.mps.fertigungskomponente.dataaccesslayer.Bauteil;
import to.mps.fertigungskomponente.dataaccesslayer.FertigungRepo;
import to.mps.fertigungskomponente.dataaccesslayer.Fertigungsauftrag;

public class FertigungFacade implements FertigungFuerAuftragServices, FertigungServices{
	
	private FertigungRepo fertigungRepo;
	
	public FertigungFacade(){
		fertigungRepo = new FertigungRepo();
	}
	
	@Override
	public Fertigungsauftrag erstelleFertigungsauftrag(Auftrag auftrag) {
		Bauteil bauteil = auftrag.getAngebot().getBauteil();
		
		Fertigungsauftrag fertigungsauftrag = new Fertigungsauftrag(bauteil, auftrag.getId());
		
		fertigungRepo.save(fertigungsauftrag);
		
		return fertigungsauftrag;
	}

	@Override
	public Fertigungsauftrag getFertigungsauftrag(int id) {
		return fertigungRepo.findFertigungsauftrag(id);
	}

	@Override
	public boolean sendToFertigungssystem(Arbeitsplan ap) {
		/**
		 * Dummymethode, da kein Nachbarsystem
		 * Sende Arbeitsplan an Fertigungssystem
		 * gebe true zurück, wenn erfolgreich
		 */
		return true;
	}

}
