package model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Test {	
	int i = 0;
	
	public static void main(String[] args) {
//		new StoreData().i = 5;	//creating configuration object
		
		Configuration cfg=new Configuration();
		cfg.configure("model/hibernate.cfg.xml");//populates the data of the configuration file
		//creating seession factory object
		SessionFactory factory=cfg.buildSessionFactory();	//creating session object
		Session session=factory.openSession();	//creating transaction object
		Transaction t=session.beginTransaction();
		Fertigungsauftrag e1=new Fertigungsauftrag("stuff");
		Fertigungsauftrag e2=new Fertigungsauftrag("ding");
		session.persist(e1);//persisting the object
		t.commit();//transaction is commited
		session.close();
		System.out.println("successfully saved");
		}
	}

