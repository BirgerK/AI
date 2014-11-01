package fertigung;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import models.Angebot;
import models.Fertigungsauftrag;
import models.Kundenauftrag;
import models.Transportauftrag;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Auftragsverwalter {
	private Configuration hibernateConfig = null;
	private SessionFactory hibernateFactory = null;
	private Session hibernateSession = null;
	
	//Constructor
	public Auftragsverwalter(){
		hibernateConfig=new Configuration();
		hibernateConfig.configure("model/hibernate.cfg.xml");//populates the data of the configuration file
		//creating seession factory object
		hibernateFactory=hibernateConfig.buildSessionFactory();	//creating session object
		hibernateSession=hibernateFactory.openSession();	//creating transaction object
	}
	//######Methoden ########
	
	public Fertigungsauftrag erstelleFertigungsauftrag(Angebot angebot) {
		Fertigungsauftrag temp = new Fertigungsauftrag(angebot);
		persistObject(temp);
		return temp;
	}
	
	public Kundenauftrag erstelleKundensauftrag(Angebot angebot) {
		Kundenauftrag temp = new Kundenauftrag(angebot);
		persistObject(temp);
		return temp;
	}
	
	public Transportauftrag erstelleTransportauftrag(Angebot angebot) {
		Transportauftrag temp = new Transportauftrag(angebot);
		persistObject(temp);
		return temp;
	}
	
	//######Fertigungsauftrag Setter Getter########
	
	public Date getFertigungsdauer(int fertigungsAuftragId){
		Fertigungsauftrag receivedAuftrag = (Fertigungsauftrag) loadObject(Fertigungsauftrag.class, fertigungsAuftragId);
		return receivedAuftrag.getFertigungsEnde();
	}
	
	
	//#######Helper#########
	private void persistObject(Object object){
		if(!(hibernateSession.isOpen())){
			hibernateFactory.openSession();
		}
		Transaction hibernateTransaction = hibernateSession.beginTransaction();
		hibernateSession.persist(object);
		hibernateTransaction.commit();
	}
	private Object loadObject(Class entityClassName,Serializable id){
		if(!(hibernateSession.isOpen())){
			hibernateFactory.openSession();
		}
		return hibernateSession.load(entityClassName, id);
	}
}
