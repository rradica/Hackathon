package ch.opendata.hack.energy.repository;


import ch.opendata.hack.energy.model.DatabaseObject;
import ch.opendata.hack.energy.model.StringValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StringValueRepository extends JpaRepository<StringValue,Long> {



}
