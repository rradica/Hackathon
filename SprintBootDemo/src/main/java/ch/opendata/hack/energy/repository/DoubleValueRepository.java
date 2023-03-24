package ch.opendata.hack.energy.repository;


import ch.opendata.hack.energy.model.DoubleValue;
import ch.opendata.hack.energy.model.StringValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoubleValueRepository extends JpaRepository<DoubleValue,Long> {

}
