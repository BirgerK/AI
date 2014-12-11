package to.mps.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import to.mps.common.AbstractEntity;

/**
 * Hibernate Adapter
 * One session per request
 * @author khangnghi
 *
 */

public class HibernateAdapter {
	private static Transaction transaction;
	private static Session start(){
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();	
		transaction = session.beginTransaction();
		return session;
	}
	
	private static void end(Session session){
		try{
			session.getTransaction().commit();
		}
	   catch (Exception ex) {
		    // Log the exception here
		    transaction.rollback();
		    throw ex;
		} 		
	}
	
	public static void save(AbstractEntity obj){
		Session session = start();
		session.save(obj);
		end(session);
	}
	
	public static <T> T getById(Class<T> clazz, int ID){
		Session session = start();
		Object obj = session.get(clazz, ID);
		end(session);
		return clazz.cast(obj);
	}
}