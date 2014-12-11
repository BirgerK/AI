package test.komponententests;

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

import to.mps.fertigungskomponente.accesslayer.FertigungFacade;
import to.mps.fertigungskomponente.accesslayer.FertigungServices;
import to.mps.fertigungskomponente.dataaccesslayer.Arbeitsplan;
import to.mps.fertigungskomponente.dataaccesslayer.Bauteil;
import to.mps.fertigungskomponente.dataaccesslayer.FertigungRepo;
import to.mps.fertigungskomponente.dataaccesslayer.Fertigungsauftrag;
import to.mps.fertigungskomponente.dataaccesslayer.Stueckliste;
import to.mps.fertigungskomponente.dataaccesslayer.StuecklistenPosition;
import to.mps.fertigungskomponente.dataaccesslayer.Vorgang;
import to.mps.fertigungskomponente.datatypes.VorgangArtType;


public class FertigungskomponenteTest {
	FertigungServices fertigungServices = new FertigungFacade();
	FertigungRepo fertigungRepo = new FertigungRepo();
	
	@Test
	public void testSaveAndFindFertigungsplanSuccess() {			
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse("21/12/2012");
		
			Bauteil bauteil2 = new Bauteil("Motor", null);
			fertigungRepo.save(bauteil2);
			Bauteil bauteil3 = new Bauteil("Karosserie", null);
			fertigungRepo.save(bauteil3);
			
			StuecklistenPosition motor = new StuecklistenPosition(1, bauteil2);
			StuecklistenPosition karosserie = new StuecklistenPosition(1, bauteil3);
			
			Set<StuecklistenPosition> set = new HashSet<StuecklistenPosition>();
			set.add(motor);
			set.add(karosserie);
			
			Stueckliste stueckliste = new Stueckliste(date, date, set);
			fertigungRepo.save(stueckliste);
			Bauteil bauteil = new Bauteil("Mähdrescher", stueckliste);
			fertigungRepo.save(bauteil);
			Fertigungsauftrag fertigungsauftrag = new Fertigungsauftrag(bauteil, -1);
			fertigungRepo.save(fertigungsauftrag);
			
			Fertigungsauftrag fertigungsauftrag2 = fertigungRepo.findFertigungsauftrag(fertigungsauftrag.getId());
			
			assertEquals(fertigungsauftrag.getId(), fertigungsauftrag2.getId());
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStuecklisteSuccess() {		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse("21/12/2012");
		
			Date date2 = sdf.parse("21/12/2013");
			
			Bauteil bauteil2 = new Bauteil("Motor", null);
			fertigungRepo.save(bauteil2);
			Bauteil bauteil3 = new Bauteil("Karosserie", null);
			fertigungRepo.save(bauteil3);
			
			StuecklistenPosition motor = new StuecklistenPosition(1, bauteil2);
			StuecklistenPosition karosserie = new StuecklistenPosition(1, bauteil3);
			
			Set<StuecklistenPosition> list = new HashSet<StuecklistenPosition>();
			list.add(motor);
			list.add(karosserie);
			
			Stueckliste stueckliste = new Stueckliste(date, date2, list);
			fertigungRepo.save(stueckliste);
			Bauteil bauteil = new Bauteil("Mähdrescher", stueckliste);
			fertigungRepo.save(bauteil);
			
			Bauteil bauteil4 = fertigungRepo.findBauteil(bauteil.getId());
			assertEquals(stueckliste.getId(), bauteil4.getStueckliste().getId());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testArbeitsplanKomplexesBauteilSuccess(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse("21/12/2012");
		
			Date date2 = sdf.parse("21/12/2013");
		
			Bauteil bauteil2 = new Bauteil("Motor", null);
			fertigungRepo.save(bauteil2);
			Bauteil bauteil3 = new Bauteil("Karosserie", null);
			fertigungRepo.save(bauteil3);
			
			StuecklistenPosition motor = new StuecklistenPosition(1, bauteil2);
			StuecklistenPosition karosserie = new StuecklistenPosition(1, bauteil3);
			
			Set<StuecklistenPosition> list = new HashSet<StuecklistenPosition>();
			list.add(motor);
			list.add(karosserie);
			
			Stueckliste stueckliste = new Stueckliste(date, date2, list);
			fertigungRepo.save(stueckliste);
			
			Bauteil bauteil = new Bauteil("Mähdrescher", stueckliste);
			fertigungRepo.save(bauteil);		
			
			//Vorgang v1 = Vorgang(VorgangArtType.Bereitstellung, new DauerInMinuten(100), DauerInMinuten(200), DauerInMinuten(300));
			//Vorgang v2 = Vorgang(VorgangArtType.Bereitstellung, new DauerInMinuten(100), DauerInMinuten(200), DauerInMinuten(300));				
			
			Vorgang v1 = new Vorgang(VorgangArtType.Bereitstellung, 100, 200, 300);
			Vorgang v2 = new Vorgang(VorgangArtType.Bereitstellung, 100, 200, 300);
			
			List<Vorgang> vorgaenge = new ArrayList<Vorgang>(Arrays.asList(v1, v2));
			
			Arbeitsplan ap = new Arbeitsplan(bauteil, vorgaenge);
			
			fertigungRepo.save(ap);
			
			Arbeitsplan ap2 = fertigungRepo.findArbeitsplan(ap.getId());
			for(int i = 0; i < vorgaenge.size(); i++){
				assertEquals(ap2.getVorgang().get(i).getId(), vorgaenge.get(i).getId());
			}
				
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
