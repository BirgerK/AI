package to.mps.fertigungskomponente.dataaccesslayer;

import to.mps.hibernate.HibernateAdapter;

public class FertigungRepo {
	
	public void save(Stueckliste stueckliste){
		HibernateAdapter.save(stueckliste);
	}
	
	public Stueckliste findStueckliste(int id){
		return HibernateAdapter.getById(Stueckliste.class, id);
	}
	
	public void save(Bauteil bauteil) {
		HibernateAdapter.save(bauteil);
	}
	
	public Bauteil findBauteil(int id) {
		return HibernateAdapter.getById(Bauteil.class, id);
	}
	

	public void save(Arbeitsplan ap) {
		HibernateAdapter.save(ap);
	}

	public Arbeitsplan findArbeitsplan(int id) {
		return HibernateAdapter.getById(Arbeitsplan.class, id);
	}

	public void save(Fertigungsauftrag fertigungsauftrag) {
		HibernateAdapter.save(fertigungsauftrag);
	}

	public Fertigungsauftrag findFertigungsauftrag(int id) {
		return HibernateAdapter.getById(Fertigungsauftrag.class, id);
	}

}
