package to.mps.auftragskomponente.accesslayer;

import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.auftragskomponente.dataaccesslayer.Auftrag;

public interface AuftragServices {
	public Auftrag erstelleAuftrag(Auftrag a);
	
	public Auftrag erstelleAuftragAusAngebot(Angebot angebot);
	
	public void schliesseAb(Auftrag a);
}
