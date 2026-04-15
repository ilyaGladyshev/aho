package aho.repository;

import aho.model.Settings;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SettingsRepository extends CrudRepository<Settings, Integer> {
    List<Settings> findAll();
    List<Settings> findAllByName(String name);
}
