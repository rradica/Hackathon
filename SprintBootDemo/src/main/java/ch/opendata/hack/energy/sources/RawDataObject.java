package ch.opendata.hack.energy.sources;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RawDataObject {

    private String sourceName;

    private Map<String, String> data = new HashMap<>();

    public RawDataObject(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getAttributeByName(final String name) {
        return data.get(name);
    }

    public void setAttribute(final String name, final String value) {
        this.data.put(name, value);
    }

    public String getSourceName() {
        return sourceName;
    }

    public Map<String, String> getData() {
        return Collections.unmodifiableMap(data);
    }
}
