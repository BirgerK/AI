import fertigung.Auftragsverwalter;
import fertigung.Fertigungsmanagement;
import iFertigung.*;
//import fertigung.*;
import verkaufskomponente.*;


public class main {

	public static void main(String[] args) {
		Fertigungsmanagement fertigung = new Fertigungsmanagement(new Auftragsverwalter());
		
		Angebot angebot = new Angebot();
		
		fertigung.erstelleFertigungsauftrag(angebot);
		fertigung.erstelleKundenauftrag(angebot);
		fertigung.erstelleTransportauftrag(angebot);
		
		System.out.println(fertigung.berechneFertigungszeitpunkt().toString());

	}

}
