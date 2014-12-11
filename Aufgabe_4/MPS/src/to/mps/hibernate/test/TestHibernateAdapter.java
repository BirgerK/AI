package to.mps.hibernate.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.junit.Test;

import to.mps.fertigungskomponente.dataaccesslayer.Bauteil;
import to.mps.fertigungskomponente.dataaccesslayer.Stueckliste;
import to.mps.fertigungskomponente.dataaccesslayer.StuecklistenPosition;
import to.mps.hibernate.HibernateAdapter;

public class TestHibernateAdapter {

	@Test
	public void testSaveAndFind() {
		Bauteil bauteil2 = new Bauteil("Festplatte", null);
		HibernateAdapter.save(bauteil2);
		Bauteil expected = HibernateAdapter.getById(Bauteil.class, bauteil2.getId());
		
		assertEquals(expected.getId(), bauteil2.getId());
		assertEquals(expected.getName(), bauteil2.getName());
	}
	
	@Test
	public void testSaveComplex(){
		Bauteil bauteil2 = new Bauteil("Festplatte", null);
		HibernateAdapter.save(bauteil2);
		Stueckliste stueckliste = new Stueckliste(new Date(), new Date(), new HashSet<StuecklistenPosition>(Arrays.asList(new StuecklistenPosition(1, bauteil2))));
		HibernateAdapter.save(stueckliste);
		Bauteil bauteil = new Bauteil("Datenbank", stueckliste);
		HibernateAdapter.save(bauteil);
	}

}
