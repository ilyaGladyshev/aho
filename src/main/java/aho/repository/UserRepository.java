package aho.repository;

import aho.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findAll();
    @Query(value = "SELECT * from users u where u.role in (1,3)", nativeQuery = true)
    List<User> findAllCreators();
    @Query(value = "SELECT * from users u where u.role in (2,3)", nativeQuery = true)
    List<User> findAllContrillers();
    List<User> findByFio(String fio);
}
