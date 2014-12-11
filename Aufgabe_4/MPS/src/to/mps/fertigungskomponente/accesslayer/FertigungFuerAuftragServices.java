package to.mps.fertigungskomponente.accesslayer;

import to.mps.auftragskomponente.dataaccesslayer.Auftrag;
import to.mps.fertigungskomponente.dataaccesslayer.Arbeitsplan;
import to.mps.fertigungskomponente.dataaccesslayer.Fertigungsauftrag;

public interface FertigungFuerAuftragServices {
	public Fertigungsauftrag erstelleFertigungsauftrag(Auftrag f);

	public boolean sendToFertigungssystem(Arbeitsplan ap);
}
