package test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import models.Angebot;
import models.Fertigungsauftrag;
import models.Komponente;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static test.HibernateMaster.*;

public class FertigungsauftragTest {
	private static Fertigungsauftrag dummy;
	private static int dummyId;
	private static Angebot dummyAngebot;
	private static Set<Komponente> komponenten = new HashSet<Komponente>();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		komponenten.add(new Komponente("Nasenbohrer",5));
		dummyAngebot = new Angebot(komponenten);
		
		//Instanzen fuer die DB gebraucht wird
		initializeHibernate();
		
		dummy = new Fertigungsauftrag(dummyAngebot);
		persistObject(dummy);
		dummyId = dummy.getFertigungsauftragNr();
		
		
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
	public void testGetFertigungsauftragNr() {
		Fertigungsauftrag temp = (Fertigungsauftrag) loadObject(Fertigungsauftrag.class, dummyId);
		assertEquals(dummyId,temp.getFertigungsauftragNr());
	}

	@Test
	public void testGetAngebot() {
		Fertigungsauftrag temp = (Fertigungsauftrag) loadObject(Fertigungsauftrag.class, dummyId);
		assertEquals(dummyAngebot,temp.getAngebot());
	}

	@Test
	public void testGetFertigungsdauer() {
		assertEquals(300000,dummy.getFertigungsdauer(komponenten));
	}

}
