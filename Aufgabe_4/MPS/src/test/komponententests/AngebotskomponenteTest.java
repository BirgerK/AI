package test.komponententests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import to.mps.angebotskomponente.accesslayer.AngebotFacade;
import to.mps.angebotskomponente.accesslayer.AngebotServices;
import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.angebotskomponente.dataaccesslayer.AngebotRepo;
import to.mps.auftragskomponente.dataaccesslayer.Auftrag;

public class AngebotskomponenteTest {
	AngebotServices angebotServices = new AngebotFacade();
	AngebotRepo angebotRepo = new AngebotRepo();
	
	@Test
	public void testErstelleAngebot() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse("21/12/2012");
			
			Angebot angebot = new Angebot(date, date, 1000.0, null);			
			angebot = angebotServices.erstelleAngebot(angebot);
			
			Angebot angebot2 = angebotRepo.findAngebot(angebot.getId());

			assertEquals(angebot.getId(), angebot2.getId());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
