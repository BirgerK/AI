package verkaufskomponente;

import static utils.HibernateMaster.initializeHibernate;
import static utils.HibernateMaster.loadObject;
import static utils.HibernateMaster.persistObject;

import java.util.Set;

import models.Angebot;
import models.Komponente;

public class Verkaufsverwalter {
	
	public Verkaufsverwalter(){
		initializeHibernate();
	}
	
	public Angebot erstelleAngebot(Set<Komponente> komponenten,int kundenNr) {
		Angebot temp = new Angebot(komponenten,kundenNr);
		persistObject(temp);
		return temp;
	}
	
	
	public Komponente getFertigungsZeitpunkt(int komponentenId){
		return (Komponente) loadObject(Komponente.class, komponentenId);
	}
}
