package test.komponententests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import to.mps.auftragskomponente.accesslayer.AuftragFacade;
import to.mps.auftragskomponente.accesslayer.AuftragServices;
import to.mps.auftragskomponente.dataaccesslayer.Auftrag;
import to.mps.auftragskomponente.dataaccesslayer.AuftragRepo;
import to.mps.fertigungskomponente.dataaccesslayer.Bauteil;
import to.mps.fertigungskomponente.dataaccesslayer.Stueckliste;

public class AuftragskomponenteTest {
	AuftragServices auftragservices = new AuftragFacade();
	AuftragRepo auftragRepo = new AuftragRepo();
	
	@Test
	public void testErstelleAuftrag() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse("21/12/2012");
			
			Auftrag auftrag = new Auftrag(false, date, null);			
			auftrag = auftragservices.erstelleAuftrag(auftrag);
			
			Auftrag auftrag2 = auftragRepo.findAuftrag(auftrag.getId());

			assertEquals(auftrag.getId(), auftrag2.getId());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testErstelleAuftrag2() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse("21/12/2012");
			Auftrag auftrag = new Auftrag(false, date, null);
			
			auftrag = auftragservices.erstelleAuftrag(auftrag);
			
			Auftrag auftrag2 = auftragRepo.findAuftrag(auftrag.getId());
			assertEquals(auftrag.getId(), auftrag2.getId());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
