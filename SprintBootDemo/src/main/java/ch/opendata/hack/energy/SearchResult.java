package ch.opendata.hack.energy;

import ch.opendata.hack.energy.model.DatabaseObject;
import ch.opendata.hack.energy.sources.RawDataObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResult {

    private final Map<String, Object> metaData = new HashMap<>();

    private final List<JsonObject> data = new ArrayList<>();

    private SearchResult() {

    }

    public static SearchResult create(List<DatabaseObject> databaseObjectsList) {
        final SearchResult ret = new SearchResult();
        ret.data.addAll(databaseObjectsList.stream().map(databaseObject -> new JsonObject(databaseObject)).toList());
        ret.metaData.put("size", ret.data.size());
        return ret;
    }
}
