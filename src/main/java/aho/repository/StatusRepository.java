package aho.repository;

import aho.model.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatusRepository extends CrudRepository<Status, Integer> {
    List<Status> findAll();
    List<Status> findAllByName(String name);;
}
