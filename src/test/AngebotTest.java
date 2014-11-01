package test;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import models.Angebot;
import models.Fertigungsauftrag;
import models.Komponente;

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
		hibernateConfig.configure(pathToHibernateConfig);
		hibernateFactory=hibernateConfig.buildSessionFactory();
		hibernateSession=hibernateFactory.openSession();
		hibernateSession.setFlushMode(FlushMode.AUTO);
		
		dummyWithFertigung = new Angebot(komponenten);
		
		dummyFertigung = new Fertigungsauftrag(dummyWithFertigung);
		dummyWithFertigung.setFertigungsauftrag(dummyFertigung);
		persistObject(dummyWithFertigung);
		
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
	}

	@Test
	public void testGetFertigungsauftrag() {
		assertEquals(null,dummyAngebot.getFertigungsauftrag());
		
		Angebot angebotFromPersistence = (Angebot) loadObject(Angebot.class,1);
		assertEquals(dummyFertigung, angebotFromPersistence.getFertigungsauftrag());
	}

	@Test
	public void testGetTransportauftrag() {
		assertEquals(null,dummyAngebot.getTransportauftrag());
	}
	
	private static void persistObject(Object object){
//		if(!(hibernateSession.isOpen())){
//			hibernateFactory.openSession();
//		}
//		Transaction hibernateTransaction = hibernateSession.beginTransaction();
		hibernateSession.saveOrUpdate(object);
//		hibernateTransaction.commit();
	}
	private static void updateObject(Object object){
		if(!(hibernateSession.isOpen())){
			hibernateFactory.openSession();
		}
		Transaction hibernateTransaction = hibernateSession.beginTransaction();
		hibernateSession.update(object);
		hibernateTransaction.commit();
	}
	private static Object loadObject(Class entityClassName,Serializable id){
		if(!(hibernateSession.isOpen())){
			hibernateFactory.openSession();
		}
		return hibernateSession.load(entityClassName, id);
	}

}
