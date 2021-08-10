package repository;

import model.File;
import model.User;
import repository.common.GenericRepository;

import java.util.List;

public interface UserRepository extends GenericRepository<User, Integer> {
    List<File> findUserFiles(Integer integer);
}
