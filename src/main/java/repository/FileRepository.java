package repository;

import model.File;
import repository.common.GenericRepository;

import java.util.List;

public interface FileRepository extends GenericRepository<File, Integer> {
    List<File> findByEventId(Integer id);
}
