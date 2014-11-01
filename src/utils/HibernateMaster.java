package utils;

import java.io.Serializable;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateMaster {
	private static Configuration hibernateConfig = null;
	private static SessionFactory hibernateFactory = null;
	private static Session hibernateSession = null;
	private static String pathToHibernateConfig = "utils/hibernate.cfg.xml";
	private HibernateMaster(){}
	
	public static void initializeHibernate(){
		hibernateConfig=new Configuration();
		hibernateConfig.configure(pathToHibernateConfig);
		hibernateFactory=hibernateConfig.buildSessionFactory();
		hibernateSession=hibernateFactory.openSession();
		hibernateSession.setFlushMode(FlushMode.AUTO);
	}
	
	public static void persistObject(Object object){
		if(!(hibernateSession.isOpen())){
			hibernateFactory.openSession();
		}
//		Transaction hibernateTransaction = hibernateSession.beginTransaction();
		hibernateSession.saveOrUpdate(object);
//		hibernateTransaction.commit();
	}
	public static Object loadObject(Class entityClassName,Serializable id){
		if(!(hibernateSession.isOpen())){
			hibernateFactory.openSession();
		}
		return hibernateSession.load(entityClassName, id);
	}
}
