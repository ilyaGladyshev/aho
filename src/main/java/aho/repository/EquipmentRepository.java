package aho.repository;

import aho.model.Equipment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Integer> {
    @Query(value = "SELECT * from equipment e where e.category = :category", nativeQuery = true)
    List<Equipment> findAllByCategory(int category);

    @Query(value = "SELECT * from equipment e where e.category = " +
            "(Select e1.id from equipment e1 where e1.id = :id) " +
            "order by e.name", nativeQuery = true)
    List<Equipment> findEquipmentInCategoryById(String id);

    List<Equipment> findAllByName(String name);
}
