package to.mps.hibernate.test;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import to.mps.angebotskomponente.accesslayer.AngebotFacade;
import to.mps.angebotskomponente.accesslayer.AngebotServices;
import to.mps.angebotskomponente.dataaccesslayer.Angebot;
import to.mps.angebotskomponente.dataaccesslayer.AngebotRepo;
import to.mps.auftragskomponente.dataaccesslayer.Auftrag;
import to.mps.fertigungskomponente.dataaccesslayer.Arbeitsplan;
import to.mps.fertigungskomponente.dataaccesslayer.Bauteil;
import to.mps.fertigungskomponente.dataaccesslayer.Fertigungsauftrag;
import to.mps.fertigungskomponente.dataaccesslayer.Stueckliste;
import to.mps.fertigungskomponente.dataaccesslayer.StuecklistenPosition;
import to.mps.fertigungskomponente.dataaccesslayer.Vorgang;
import to.mps.fertigungskomponente.datatypes.VorgangArtType;
import to.mps.hibernate.SessionFactoryUtil;

public class TestHibernate {

	Session session;
	@Before
	public void setUp(){
		session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
	}
	
	@After
	public void tearDown(){
		session.getTransaction().commit();
	}
	
	@Test
	public void testComplexObjects() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse("21/12/2012");
			Bauteil bauteil2 = new Bauteil("Metall", null);
			session.save(bauteil2);
			Bauteil bauteil3 = new Bauteil("K", null);
			session.save(bauteil3);
			Bauteil bauteil4 = new Bauteil("Bla", null);
			session.save(bauteil4);
			
			StuecklistenPosition stuecklistenPosition1 = new StuecklistenPosition(5, bauteil2);
			StuecklistenPosition stuecklistenPosition2 = new StuecklistenPosition(1, bauteil3);
			StuecklistenPosition stuecklistenPosition3 = new StuecklistenPosition(1, bauteil4);
			Set<StuecklistenPosition> list = new HashSet<StuecklistenPosition>();
			list.add(stuecklistenPosition1);
			list.add(stuecklistenPosition2);
			list.add(stuecklistenPosition3);
			
			Stueckliste stueckliste = new Stueckliste(new Date(), new Date(), list);
			Bauteil bauteil = new Bauteil("Kupplung", stueckliste);
			Vorgang vorgang = new Vorgang(VorgangArtType.Bereitstellung, 10, 10, 10);
			Arbeitsplan arbeitsplan = new Arbeitsplan(bauteil, Arrays.asList(vorgang));
			
			session.save(arbeitsplan);
			session.save(bauteil);
			session.save(stueckliste);
			
			Angebot angebot = new Angebot(date, date, 1000, bauteil);
			session.save(angebot);
			
			Auftrag auftrag = new Auftrag(false, date, angebot);
			session.save(auftrag);
			
			Fertigungsauftrag fertigungsauftrag = new Fertigungsauftrag(bauteil, auftrag.getId());
			session.save(fertigungsauftrag);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

}
