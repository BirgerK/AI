package test;

import static org.junit.Assert.*;
import static utils.HibernateMaster.*;

import java.util.HashSet;
import java.util.Set;

import models.Angebot;
import models.Komponente;
import models.Kundenauftrag;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class KundenauftragTest {
	private static Kundenauftrag dummy;
	private static int dummyId;
	private static Angebot dummyAngebot;
	private static Set<Komponente> komponenten = new HashSet<Komponente>();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		komponenten.add(new Komponente("Nasenbohrer",5));
		dummyAngebot = new Angebot(komponenten);
		
		//Instanzen fuer die DB gebraucht wird
		initializeHibernate();
		
		dummy = new Kundenauftrag(dummyAngebot);
		persistObject(dummy);
		dummyId = dummy.getKundenauftragNr();		
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
	public void testGetKundenauftragNr() {
		Kundenauftrag temp = (Kundenauftrag) loadObject(Kundenauftrag.class, dummyId);
		assertEquals(dummyId,temp.getKundenauftragNr());
	}

}
