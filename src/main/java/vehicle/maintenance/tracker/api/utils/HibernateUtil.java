package vehicle.maintenance.tracker.api.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import vehicle.maintenance.tracker.api.dao.SessionResult;

/**
 * Util for creating and using hibernate JPA
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory = HibernateUtil.createSessionFactory();

    private static final SessionFactory createSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionResult use(HibernateSession sessionUser){
        if(HibernateUtil.sessionFactory != null && !HibernateUtil.sessionFactory.isClosed()){
            Session session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            SessionResult result = sessionUser.dispatch(session);
            session.getTransaction().commit();
            session.close();
            return result;
        }
        return null;
    }

    public static final void close(){
        sessionFactory.close();
    }

    public static final boolean isOpen(){
        return ! HibernateUtil.sessionFactory.isClosed();
    }

    public interface HibernateSession {
        SessionResult<?> dispatch(Session session);
    }

}