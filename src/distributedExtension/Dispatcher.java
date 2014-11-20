package distributedExtension;

import java.util.Date;
import java.util.List;
import java.util.Map;

import models.Angebot;
import models.Fertigungsauftrag;
import models.Kundenauftrag;
import models.Transportauftrag;
import bossKomponente.IMPS;

public class Dispatcher implements IMPS,IMonitoring {

	@Override
	public Date berechneFertigungszeitpunkt(int fertigungsAuftragId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fertigungsauftrag erstelleFertigungsauftrag(Angebot angebot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transportauftrag erstelleTransportauftrag(Angebot angebot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Kundenauftrag erstelleKundenauftrag(Angebot angebot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double berechneKosten(Angebot angebot) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Angebot erstelleAngebot(Map<Integer, Integer> matNrZuMenge,
			int kundenNr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getListOfAllServers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAmountIdleServer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBusyServer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean statusOfServer(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
