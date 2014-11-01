package test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import models.Angebot;
import models.Fertigungsauftrag;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fertigung.*;

public class AngebotTest {
	static Angebot dummyAngebot;
	static Angebot dummyWithFertigung;
	static Fertigungsauftrag dummyFertigung;
	static Set<Komponente> komponenten;
	private static Configuration hibernateConfig = null;
	private static SessionFactory hibernateFactory = null;
	private static Session hibernateSession = null;
	private static String pathToHibernateConfig = "test/hibernate.cfg.xml";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		komponenten = new HashSet<Komponente>();
		komponenten.add(new Komponente("Nasenbohrer",5));
		
		dummyAngebot = new Angebot(komponenten);
		
		//Instanzen fuer die DB gebraucht wird
		hibernateConfig=new Configuration();
		hibernateConfig.configure(pathToHibernateConfig);//populates the data of the configuration file
		//creating seession factory object
		hibernateFactory=hibernateConfig.buildSessionFactory();	//creating session object
		hibernateSession=hibernateFactory.openSession();	//creating transaction object
		
		dummyWithFertigung = new Angebot(komponenten);
		persistObject(dummyWithFertigung);
		dummyFertigung = new Fertigungsauftrag(dummyWithFertigung);
		persistObject(dummyFertigung);
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
		assertEquals(dummyAngebot.getKomponenten(), komponenten);
	}

	@Test
	public void testGetKundenauftrag() {
		assertEquals(dummyAngebot.getKundenauftrag(),null);
	}

	@Test
	public void testGetFertigungsauftrag() {
		assertEquals(dummyAngebot.getFertigungsauftrag(),null);
		assertEquals(dummyWithFertigung.getFertigungsauftrag(),dummyFertigung);
	}

	@Test
	public void testGetTransportauftrag() {
		assertEquals(dummyAngebot.getTransportauftrag(),null);
	}
	
	private static void persistObject(Object object){
		if(!(hibernateSession.isOpen())){
			hibernateFactory.openSession();
		}
		Transaction hibernateTransaction = hibernateSession.beginTransaction();
		hibernateSession.persist(object);
		hibernateTransaction.commit();
	}

}
