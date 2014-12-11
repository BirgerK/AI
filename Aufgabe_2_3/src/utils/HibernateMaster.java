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
	
	/**Stellt die Verbindung zum SQL-Server her, mit den Einstellungen wie es in der hibernate.cf.xml im Ordner "utils" konfiguriert ist
	 * Muss ausgefuehrt werden, bevor Operationen auf der Datenbank durchgefuehrt werden koennen!
	 */
	public static void initializeHibernate(){
		hibernateConfig=new Configuration();
		hibernateConfig.configure(pathToHibernateConfig);
		hibernateFactory=hibernateConfig.buildSessionFactory();
		hibernateSession=hibernateFactory.openSession();
		hibernateSession.setFlushMode(FlushMode.AUTO);
	}
	
	/**Erstellt oder aktualisiert ein Objekt in der SQL-Datenbank. Es werden alle mit dem Objekt zusammenhaengenden Objekte aktualisiert!
	 * @param object zu aktualisierendes Objekt
	 */
	public static void persistObject(Object object){
		if(!(hibernateSession.isOpen())){
			hibernateFactory.openSession();
		}
//		Transaction hibernateTransaction = hibernateSession.beginTransaction();
		hibernateSession.saveOrUpdate(object);
//		hibernateTransaction.commit();
	}
	/**Laedt ein Objekt aus der Datenbank
	 * @param entityClassName Class-Objekt der zu ladenden Entitaet, z.B. "Kundenauftrag.class"
	 * @param id Eindeutige id des zu ladenden Objekts
	 * @return
	 */
	public static Object loadObject(Class entityClassName,Serializable id){
		if(!(hibernateSession.isOpen())){
			hibernateFactory.openSession();
		}
		return hibernateSession.load(entityClassName, id);
	}
}
