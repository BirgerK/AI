package verkaufskomponente;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.Angebot;
import models.Komponente;

public class Verkaufsmanagement implements IVerkauf{

	private Verkaufsverwalter verwalter = null;
	private double PREIS_FAKTOR = 1.05;
	
	public Verkaufsmanagement() {
		verwalter = new Verkaufsverwalter();
	}
	
	@Override
	public double berechneKosten(Angebot angebot) {
		return angebot.getFertigungskosten() * PREIS_FAKTOR;
	}

	@Override
	public Angebot erstelleAngebot(Map<Integer, Integer> matNrZuMenge,int kundenNr) {
		Set<Komponente> komponenten = new HashSet<Komponente>();
		for(int komponentenId:matNrZuMenge.keySet()){
			komponenten.add(verwalter.getFertigungsZeitpunkt(komponentenId));
		}
		return verwalter.erstelleAngebot(komponenten,kundenNr);
	}

}
