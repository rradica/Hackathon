package ch.opendata.hack.energy.repository;


import ch.opendata.hack.energy.model.DatabaseObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjectRepository extends JpaRepository<DatabaseObject,Long> {



}
