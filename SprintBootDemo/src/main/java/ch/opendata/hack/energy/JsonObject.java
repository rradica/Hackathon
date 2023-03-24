package ch.opendata.hack.energy;

import ch.opendata.hack.energy.model.DatabaseObject;
import ch.opendata.hack.energy.model.DateValue;
import ch.opendata.hack.energy.model.DoubleValue;
import ch.opendata.hack.energy.model.IntegerValue;
import ch.opendata.hack.energy.model.StringValue;
import java.util.HashMap;
import java.util.Map;

public class JsonObject {

    private final Map<String, Object> attributes = new HashMap<>();

    private final String source;

    public JsonObject(final DatabaseObject databaseObject) {

        this.source = databaseObject.getDatasource();

        for(DateValue dateValue : databaseObject.getDateValueMap()) {
            attributes.put(dateValue.getName(), dateValue.getValue());
        }

        for(StringValue stringValue : databaseObject.getStringValueMap()) {
            attributes.put(stringValue.getName(), stringValue.getValue());
        }

        for(DoubleValue doubleValue : databaseObject.getDoubleValueMap()) {
            attributes.put(doubleValue.getName(), doubleValue.getValue());
        }

        for(IntegerValue integerValue : databaseObject.getIntegerValueMap()) {
            attributes.put(integerValue.getName(), integerValue.getValue());
        }
    }
}
