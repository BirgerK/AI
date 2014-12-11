package to.mps.auftragskomponente.dataaccesslayer;

import to.mps.hibernate.HibernateAdapter;

public class AuftragRepo {

	public Auftrag findAuftrag(int id) {
		return HibernateAdapter.getById(Auftrag.class, id);
	}
	
	public void saveAuftrag(Auftrag auftrag){
		HibernateAdapter.save(auftrag);
	}

}
