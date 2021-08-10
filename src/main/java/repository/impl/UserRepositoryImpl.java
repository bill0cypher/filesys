package repository.impl;

import bootstrap.SessionRunner;
import model.File;
import model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.EventRepository;
import repository.FileRepository;
import repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private final Logger logger = Logger.getLogger(UserRepositoryImpl.class);
    private final SessionFactory sessionFactory;
    private final FileRepository fileRepository;
    private final EventRepository eventRepository;

    public UserRepositoryImpl() {
        this.sessionFactory = SessionRunner.initSessionFactory();
        this.fileRepository = new FileRepositoryImpl();
        this.eventRepository = new EventRepositoryImpl();
    }

    @Override
    public List<File> findUserFiles(Integer id) {
        return this.fileRepository.findByEventId(id);
    }

    @Override
    public User save(User user) {
        EntityTransaction transaction = null;
        User res = null;
        try {
            EntityManager em = sessionFactory.createEntityManager();
            em.setFlushMode(FlushModeType.AUTO);
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(user);

            User finalRes = user;
            user.getFiles().forEach(file -> {
                file.getUser().setId(finalRes.getId());
                em.persist(file);
            });
            user.getEvents().forEach(event -> {
                event.getUser().setId(finalRes.getId());
                em.persist(event);
            });
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
    public User update(User user) {
        Transaction transaction = null;
        User res = null;
        if (!user.getFiles().isEmpty()) {
            user.getFiles().forEach(fileRepository::update);
        }
        if (!user.getEvents().isEmpty()) {
            user.getEvents().forEach(eventRepository::update);
        }
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            int updated = session.createQuery("update User u set u.fullName=:fullName, u.email=:email where u.id=:id")
                    .setParameter("fullName", user.getFullName())
                    .setParameter("email", user.getEmail())
                    .setParameter("id", user.getId())
                    .executeUpdate();
            if (updated >= 1)
                res = user;
            transaction.commit();
        } catch (IllegalStateException e) {
            Optional.ofNullable(transaction).ifPresent(EntityTransaction::rollback);
        }
        return res;
    }

    @Override
    public boolean delete(Integer id) {
        Transaction transaction = null;
        boolean res = false;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            res = true;
        } catch (IllegalStateException e) {
            if (transaction != null && transaction.isActive())
                transaction.rollback();
            else
                logger.error("Impossible to abort transaction due to " + e.getMessage());
        }
        return res;
    }

    @Override
    public User findById(Integer id) {
        Transaction transaction = null;
        User res = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            res = session.get(User.class, id);
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
    public List<User> findAll() {

        Transaction transaction = null;
        List<User> res = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            res = session.createQuery("from User", User.class).getResultList();
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
