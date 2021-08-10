package repository.impl;

import bootstrap.SessionRunner;
import model.Event;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.EventRepository;

import java.util.List;

public class EventRepositoryImpl implements EventRepository {

    private static final Logger logger = Logger.getLogger(EventRepositoryImpl.class.getName());
    private final SessionFactory sessionFactory;

    public EventRepositoryImpl() {
        this.sessionFactory = SessionRunner.initSessionFactory();
    }

    @Override
    public List<Event> findEventsByUserId(Integer id) {
        return null;
    }

    @Override
    public Event save(Event event) {
        Transaction transaction = null;
        Event res = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            res = session.get(Event.class, session.save(event));
            transaction.commit();
        } catch (IllegalStateException e) {
            if (transaction != null && transaction.isActive())
                transaction.rollback();
            else
                logger.error("Impossible to abort transaction due to " + e.getMessage());
        }
        return res;
    }

    @Override
    public Event update(Event event) {
        Transaction transaction = null;
        Event res = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(event);
            res = event;
            transaction.commit();
        } catch (IllegalStateException e) {
            if (transaction != null && transaction.getStatus().canRollback())
                transaction.rollback();
            else
                logger.error("Impossible to abort transaction due to " + e.getMessage());
        }
        return res;
    }

    @Override
    public boolean delete(Integer integer) {
        //TODO: History deletion is currently impossible.
        return false;
    }

    @Override
    public Event findById(Integer id) {
        Transaction transaction = null;
        Event res = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            res = session.get(Event.class, id);
            transaction.commit();
        } catch (IllegalStateException e) {
            if (transaction != null && transaction.isActive())
                transaction.rollback();
            else
                logger.error("Impossible to abort transaction due to " + e.getMessage());
        }
        return res;
    }

    @Override
    public List<Event> findAll() {
        Transaction transaction = null;
        List<Event> res = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            res = session.createQuery("from Event", Event.class).getResultList();
            transaction.commit();
        } catch (IllegalStateException e) {
            if (transaction != null && transaction.isActive())
                transaction.rollback();
            else
                logger.error("Impossible to abort transaction due to " + e.getMessage());
        }
        return res;
    }
}
