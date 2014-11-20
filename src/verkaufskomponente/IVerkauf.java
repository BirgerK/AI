package verkaufskomponente;

import java.util.Map;

import models.Angebot;

public interface IVerkauf {
	public double berechneKosten(Angebot angebot);
	public Angebot erstelleAngebot(Map<Integer,Integer> matNrZuMenge,int kundenNr);
}
