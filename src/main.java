import java.util.HashSet;
import java.util.Set;

import models.Angebot;
import models.Fertigungsauftrag;
import fertigung.*;
import verkaufskomponente.*;


public class main {

	public static void main(String[] args) {
		Fertigungsmanagement fertigung = new Fertigungsmanagement(new Auftragsverwalter());
		Set<Komponente> komponenten = new HashSet<Komponente>();
		komponenten.add(new Komponente("Nasenbohrer",5));
		
		Angebot angebot = new Angebot(komponenten);
		
		Fertigungsauftrag auftrag = fertigung.erstelleFertigungsauftrag(angebot);
		fertigung.erstelleKundenauftrag(angebot);
		fertigung.erstelleTransportauftrag(angebot);
		
		System.out.println(auftrag.getFertigungsEnde());

	}

}
