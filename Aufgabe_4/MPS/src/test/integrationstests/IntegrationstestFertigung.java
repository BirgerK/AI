package test.integrationstests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import to.mps.angebotskomponente.accesslayer.AngebotFacade;
import to.mps.angebotskomponente.accesslayer.AngebotServices;
import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.angebotskomponente.dataaccesslayer.AngebotRepo;
import to.mps.auftragskomponente.accesslayer.AuftragFacade;
import to.mps.auftragskomponente.accesslayer.AuftragServices;
import to.mps.auftragskomponente.dataaccesslayer.Auftrag;
import to.mps.auftragskomponente.dataaccesslayer.AuftragRepo;
import to.mps.fertigungskomponente.accesslayer.FertigungFacade;
import to.mps.fertigungskomponente.accesslayer.FertigungFuerAuftragServices;
import to.mps.fertigungskomponente.dataaccesslayer.Bauteil;
import to.mps.fertigungskomponente.dataaccesslayer.FertigungRepo;
import to.mps.fertigungskomponente.dataaccesslayer.Fertigungsauftrag;

public class IntegrationstestFertigung {
	AuftragServices auftragServices = new AuftragFacade();
	FertigungFuerAuftragServices fertigungServices = new FertigungFacade();
	AuftragRepo auftragRepo = new AuftragRepo();
	FertigungRepo fertigungRepo = new FertigungRepo();
	AngebotServices angebotServices = new AngebotFacade();
	AngebotRepo angebotRepo = new AngebotRepo();
	
	@Test
	public void erstelleAuftragAusAngebotSuccess(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		
		try {
			date = sdf.parse("21/12/2012");
		
			Bauteil bauteil = new Bauteil("Hammer", null);
			fertigungRepo.save(bauteil);
			
			Angebot angebot = new Angebot(date, date, 1000, bauteil);
			angebotServices.erstelleAngebot(angebot);
			
			Auftrag auftrag = auftragServices.erstelleAuftragAusAngebot(angebot);
			
			fertigungServices.erstelleFertigungsauftrag(auftrag);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testErstelleFertigungsplanAusAuftrag(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse("21/12/2012");
		
			Bauteil bauteil = new Bauteil("Hammer", null);
			fertigungRepo.save(bauteil);
			
			Angebot angebot = new Angebot(date, date, 1000, bauteil);
			angebotServices.erstelleAngebot(angebot);
			
			Auftrag auftrag = auftragServices.erstelleAuftragAusAngebot(angebot);
			
			fertigungServices.erstelleFertigungsauftrag(auftrag);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testFuehreFertigungAusEinfachesBauteil(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse("21/12/2012");
					
			Bauteil bauteil = new Bauteil("Hammer", null);
			fertigungRepo.save(bauteil);
			
			Angebot angebot = new Angebot(date, date, 1000, bauteil);
			angebotServices.erstelleAngebot(angebot);
			
			// Auftrag erzeugen
			Auftrag auftrag = auftragServices.erstelleAuftragAusAngebot(angebot);
			
			// Fertigungsplan erzeugen
			Fertigungsauftrag fertigungsauftrag = fertigungServices.erstelleFertigungsauftrag(auftrag);
			
			fertigungRepo.save(fertigungsauftrag);
			
			Fertigungsauftrag fertigungsauftrag2 = fertigungRepo.findFertigungsauftrag(fertigungsauftrag.getId());
			
			assertEquals(fertigungsauftrag.getId(), fertigungsauftrag2.getId());
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
