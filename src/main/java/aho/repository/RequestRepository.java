package aho.repository;

import aho.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<Request, Integer> {

    @Query(value = "SELECT * from Request r where r.status = 1", nativeQuery = true)
    List<Request> findAllUnclosing();
    @Query(value = "SELECT * from Request where id = (Select max(id) from request)", nativeQuery = true)
    List<Request> findLatInsertId();
}
