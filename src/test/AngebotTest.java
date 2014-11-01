package test;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import models.*;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static test.HibernateMaster.*;

import fertigung.*;

public class AngebotTest {
	static Angebot dummyAngebot;
	static Angebot dummyWithFertigung;
	static int dummyWithFertigungId;
	static Angebot dummyWithAuftrag;
	static int dummyWithAuftragId;
	static Angebot dummyWithTransport;
	static int dummyWithTransportId;
	
	static Fertigungsauftrag dummyFertigung;
	static Kundenauftrag dummyAuftrag;
	static Transportauftrag dummyTransport;
	
	static Set<Komponente> komponenten;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		komponenten = new HashSet<Komponente>();
		komponenten.add(new Komponente("Nasenbohrer",5));
		
		dummyAngebot = new Angebot(komponenten);
		
		//Instanzen fuer die DB gebraucht wird
		HibernateMaster.initialize();
		
		dummyWithFertigung = new Angebot(komponenten);
		dummyFertigung = new Fertigungsauftrag(dummyWithFertigung);
		dummyWithFertigung.setFertigungsauftrag(dummyFertigung);
		persistObject(dummyWithFertigung);
		dummyWithFertigungId = dummyWithFertigung.getAngebotNr();
		
		dummyWithAuftrag = new Angebot(komponenten);
		dummyAuftrag = new Kundenauftrag(dummyWithAuftrag);
		dummyWithAuftrag.setKundenauftrag(dummyAuftrag);
		persistObject(dummyWithAuftrag);
		dummyWithAuftragId = dummyWithAuftrag.getAngebotNr();
		
		dummyWithTransport = new Angebot(komponenten);
		dummyTransport = new Transportauftrag(dummyWithTransport);
		dummyWithTransport.setTransportauftrag(dummyTransport);
		persistObject(dummyWithTransport);
		dummyWithTransportId = dummyWithTransport.getAngebotNr();
		
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
	public void testGetKomponenten() {
		assertEquals(komponenten,dummyAngebot.getKomponenten());
	}

	@Test
	public void testGetKundenauftrag() {
		assertEquals(null,dummyAngebot.getKundenauftrag());
		
		Angebot angebotFromPersistence = (Angebot) loadObject(Angebot.class,dummyWithAuftragId);
		assertEquals(dummyAuftrag, angebotFromPersistence.getKundenauftrag());
	}

	@Test
	public void testGetFertigungsauftrag() {
		assertEquals(null,dummyAngebot.getFertigungsauftrag());
		
		Angebot angebotFromPersistence = (Angebot) loadObject(Angebot.class,dummyWithFertigungId);
		assertEquals(dummyFertigung, angebotFromPersistence.getFertigungsauftrag());
	}

	@Test
	public void testGetTransportauftrag() {
		assertEquals(null,dummyAngebot.getTransportauftrag());
		
		Angebot angebotFromPersistence = (Angebot) loadObject(Angebot.class,dummyWithTransportId);
		assertEquals(dummyTransport, angebotFromPersistence.getTransportauftrag());
	}
	
	

}
