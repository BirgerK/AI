package to.mps.angebotskomponente.dataaccesslayer;

import to.mps.hibernate.HibernateAdapter;

public class AngebotRepo {
	public void saveAngebot(Angebot angebot){
		HibernateAdapter.save(angebot);
	}
	
	public Angebot findAngebot(int id){
		return HibernateAdapter.getById(Angebot.class, id);
	}
}
