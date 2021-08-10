package repository.impl;

import bootstrap.SessionRunner;
import model.File;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.FileRepository;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class FileRepositoryImpl implements FileRepository {

    private final SessionFactory sessionFactory;

    public FileRepositoryImpl() {
        sessionFactory = SessionRunner.initSessionFactory();
    }

    @Override
    public List<File> findByEventId(Integer id) {
        Transaction transaction = null;
        List<File> res = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            res = session.createQuery("from File f where f.event.id=:id", File.class)
                    .setParameter("id", id).getResultList();
        } catch (IllegalStateException e) {
            Optional.ofNullable(transaction).ifPresent(EntityTransaction::rollback);
        }
        return res;
    }

    @Override
    public File save(File file) {
        Transaction transaction = null;
        File res = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            res = session.get(File.class, session.save(file));
            transaction.commit();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public File update(File file) {
        Transaction transaction = null;
        File res = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(file);
            res = file;
            transaction.commit();
        } catch (IllegalStateException e) {
            if (transaction != null && transaction.isActive() && transaction.getStatus().canRollback())
                transaction.rollback();
        }
        return res;
    }

    @Override
    public boolean delete(Integer id) {
        Transaction transaction = null;
        boolean res = false;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            res = session.createQuery("delete from File f where f.id =:fid")
                    .setParameter("fid", id)
                    .executeUpdate() >= 1;
            transaction.commit();
        } catch (IllegalStateException e) {
            if (transaction != null && transaction.isActive() && transaction.getStatus().canRollback())
                transaction.rollback();
        }
        return res;
    }

    @Override
    public File findById(Integer integer) {
        return null;
    }

    @Override
    public List<File> findAll() {
        return null;
    }
}
