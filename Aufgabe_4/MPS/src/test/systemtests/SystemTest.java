package test.systemtests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import to.mps.fertigungskomponente.dataaccesslayer.Arbeitsplan;
import to.mps.fertigungskomponente.dataaccesslayer.Bauteil;
import to.mps.fertigungskomponente.dataaccesslayer.FertigungRepo;
import to.mps.fertigungskomponente.dataaccesslayer.Fertigungsauftrag;
import to.mps.fertigungskomponente.dataaccesslayer.Stueckliste;
import to.mps.fertigungskomponente.dataaccesslayer.StuecklistenPosition;
import to.mps.fertigungskomponente.dataaccesslayer.Vorgang;
import to.mps.fertigungskomponente.datatypes.VorgangArtType;

public class SystemTest {
	AuftragServices auftragServices = new AuftragFacade();
	FertigungFuerAuftragServices fertigungServices = new FertigungFacade();
	AuftragRepo auftragRepo = new AuftragRepo();
	FertigungRepo fertigungRepo = new FertigungRepo();
	AngebotServices angebotServices = new AngebotFacade();
	AngebotRepo angebotRepo = new AngebotRepo();
	
	@Test
	public void testFuehreSzenarioAusKomplexesBauteil(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse("21/12/2012");
			Date date2 = sdf.parse("21/12/2013");
			
			Bauteil motor = new Bauteil("Motor", null);
			Bauteil raeder = new Bauteil("Räder", null);
			
			fertigungRepo.save(motor);
			fertigungRepo.save(raeder);
			
			// Stückliste des Bauteils erzeugen
			StuecklistenPosition motorSL = new StuecklistenPosition(1, motor);
			StuecklistenPosition raederSL = new StuecklistenPosition(4, raeder);
			
			Set<StuecklistenPosition> set = new HashSet<StuecklistenPosition>();
			set.add(motorSL);
			set.add(raederSL);
			
			Stueckliste stueckliste = new Stueckliste(date, date2, set);
			fertigungRepo.save(stueckliste);
			
			// komplexes Bauteil erzeugen
			Bauteil bauteil = new Bauteil("Mähdrescher", stueckliste);
			fertigungRepo.save(bauteil);
			
			Angebot angebot = new Angebot(date, date, 1000, bauteil);
			angebotServices.erstelleAngebot(angebot);
			
			// Auftrag erzeugen
			Auftrag auftrag = auftragServices.erstelleAuftragAusAngebot(angebot);		
			
			// Fertigungsplan erzeugen
			Fertigungsauftrag fertigungsauftrag = fertigungServices.erstelleFertigungsauftrag(auftrag);
			
			// Vorgänge erzeugen
			//Vorgang v1 = new Vorgang(VorgangArtType.Bereitstellung, new DauerInMinuten(100), new DauerInMinuten(200), new DauerInMinuten(300));
			//Vorgang v2 = new Vorgang(VorgangArtType.Montage, new DauerInMinuten(100), new DauerInMinuten(320), new DauerInMinuten(120));
			
			Vorgang v1 = new Vorgang(VorgangArtType.Bereitstellung, 100,200,300);
			Vorgang v2 = new Vorgang(VorgangArtType.Montage, 100,320,120);
			
			List<Vorgang> vorgaenge = new ArrayList<Vorgang>(Arrays.asList(v1, v2));
			
			Arbeitsplan ap = new Arbeitsplan(bauteil, vorgaenge);
			fertigungRepo.save(ap);
			
			Fertigungsauftrag fertigungsauftrag2 = fertigungRepo.findFertigungsauftrag(fertigungsauftrag.getId());
			Auftrag auftrag2 = auftragRepo.findAuftrag(auftrag.getId());
			Arbeitsplan ap2 = fertigungRepo.findArbeitsplan(ap.getId());
			Bauteil bauteil2 = fertigungRepo.findBauteil(bauteil.getId());
			
			assertEquals(fertigungsauftrag.getId(), fertigungsauftrag2.getId());
			assertEquals(auftrag.getId(), auftrag2.getId());
			assertEquals(ap2.getId(), ap.getId());
			assertEquals(bauteil.getId(), bauteil2.getId());
			
			assertEquals(true, fertigungServices.sendToFertigungssystem(ap));
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
