package fertigung;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import models.Angebot;
import models.Fertigungsauftrag;
import models.Komponente;
import models.Kundenauftrag;
import models.Transportauftrag;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static utils.HibernateMaster.*;

public class AuftragsverwalterTest {
	private static Fertigungsauftrag dummy;
	private static int dummyId;
	private static Angebot dummyAngebot;
	private static Set<Komponente> komponenten = new HashSet<Komponente>();
	private static Auftragsverwalter averwalter;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		komponenten = new HashSet<Komponente>();
		komponenten.add(new Komponente("Nasenbohrer",5));
		
		dummyAngebot = new Angebot(komponenten);
		
		averwalter = new Auftragsverwalter();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testErstelleFertigungsauftrag() {
		Fertigungsauftrag fauftrag = averwalter.erstelleFertigungsauftrag(dummyAngebot);
		Fertigungsauftrag fauftragLoaded = (Fertigungsauftrag) loadObject(Fertigungsauftrag.class,fauftrag.getFertigungsauftragNr());
		assertEquals(fauftrag,fauftragLoaded);
	}

	@Test
	public void testErstelleKundenauftrag() {
		Kundenauftrag kauftrag = averwalter.erstelleKundenauftrag(dummyAngebot);
		Kundenauftrag kauftragLoaded = (Kundenauftrag) loadObject(Kundenauftrag.class,kauftrag.getKundenauftragNr());
		assertEquals(kauftrag,kauftragLoaded);
	}

	@Test
	public void testErstelleTransportauftrag() {
		Transportauftrag tauftrag = averwalter.erstelleTransportauftrag(dummyAngebot);
		Transportauftrag tauftragLoaded = (Transportauftrag) loadObject(Transportauftrag.class,tauftrag.getTransportauftragNr());
		assertEquals(tauftrag,tauftragLoaded);
	}

}
