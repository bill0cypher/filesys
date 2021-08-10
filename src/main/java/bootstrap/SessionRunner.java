package bootstrap;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionRunner {

    private static SessionFactory sessionFactory;

    public static SessionFactory initSessionFactory() throws HibernateException {
        if (sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }
}
