package repository.common;

import java.util.List;

public interface GenericRepository<T, ID> {
    T save(T t);
    T update(T t);
    boolean delete(ID id);
    T findById(ID id);
    List<T> findAll();
}
