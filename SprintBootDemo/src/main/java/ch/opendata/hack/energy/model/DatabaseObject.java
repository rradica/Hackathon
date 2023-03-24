package ch.opendata.hack.energy.model;

import ch.opendata.hack.energy.Datatype;
import ch.opendata.hack.energy.sources.ParseUtils;
import ch.opendata.hack.energy.sources.RawDataObject;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Entity
@Table(name = "Objects")
public class DatabaseObject {

    @Transient
    private final Map<String, Datatype> dataTypes = new HashMap<>();

    @OneToMany(targetEntity=IntegerValue.class, mappedBy="object",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<IntegerValue> integerValueMap = new ArrayList<>();

    @OneToMany(targetEntity=DoubleValue.class, mappedBy="object",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<DoubleValue> doubleValueMap = new ArrayList<>();

    @OneToMany(targetEntity=StringValue.class, mappedBy="object",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<StringValue> stringValueMap = new ArrayList<>();

    @OneToMany(targetEntity=DateValue.class, mappedBy="object",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<DateValue> dateValueMap = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String datasource;

    public DatabaseObject() {

    }

    public void initDataypes(RawDataObject rawDataObject) {
        dataTypes.putAll(ParseUtils.parseDataTypes(rawDataObject));
    }

    public void initDataypes() {

        for(IntegerValue value : integerValueMap ) {
            this.dataTypes.put(value.getName(), Datatype.INTEGER);
        }

        for(DoubleValue value : doubleValueMap ) {
            this.dataTypes.put(value.getName(), Datatype.DOUBLE);
        }

        for(StringValue value : stringValueMap ) {
            this.dataTypes.put(value.getName(), Datatype.STRING);
        }

        for(DateValue value : dateValueMap ) {
            this.dataTypes.put(value.getName(), Datatype.DATE);
        }

    }


    public void readValuesFromRawObject(RawDataObject rawDataObject) {
        this.initDataypes(rawDataObject);
        Map<String, Object> values = ParseUtils.parseRawDataObject(rawDataObject, dataTypes);

        for(Entry<String, Datatype> entry : dataTypes.entrySet()) {

            final Datatype datatype = entry.getValue();
            final String key = entry.getKey();

            switch (datatype) {
                case INTEGER -> integerValueMap.add(new IntegerValue(this, key, (Integer) values.get(key)));
                case DATE -> dateValueMap.add(new DateValue(this, key, (LocalDate) values.get(key)));
                case DOUBLE -> doubleValueMap.add(new DoubleValue(this, key, (Double) values.get(key)));
                case STRING -> stringValueMap.add(new StringValue(this, key,(String) values.get(key)));
            }
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<IntegerValue> getIntegerValueMap() {
        return Collections.unmodifiableList(integerValueMap);
    }

    public List<DoubleValue> getDoubleValueMap() {
        return Collections.unmodifiableList(doubleValueMap);
    }

    public List<StringValue> getStringValueMap() {
        return Collections.unmodifiableList(stringValueMap);
    }

    public List<DateValue> getDateValueMap() {
        return Collections.unmodifiableList(dateValueMap);
    }

    public boolean hasAttribute(String name) {
        return this.dataTypes.containsKey(name);
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }
}
