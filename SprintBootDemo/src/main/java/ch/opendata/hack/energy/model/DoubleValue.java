package ch.opendata.hack.energy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DoublesValues")
public class DoubleValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="object_id")
    private DatabaseObject object;

    public DoubleValue(DatabaseObject object, String name, Double value) {
        this.object = object;
        this.value = value;
        this.name = name;
    }

    public DoubleValue() {

    }

    private Double value;

    private String name;
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
