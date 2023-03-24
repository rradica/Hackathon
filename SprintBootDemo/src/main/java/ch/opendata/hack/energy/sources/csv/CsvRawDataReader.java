package ch.opendata.hack.energy.sources.csv;

import ch.opendata.hack.energy.sources.RawDataObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CsvRawDataReader {

    private CsvRawDataReader() {
    }

    public static List<RawDataObject> read(final String sourceName,List<String> linesOfCsv) {

        final List<RawDataObject> ret = new ArrayList<>();
        final String[] columnNames = linesOfCsv.get(0).split(",");
        final Map<Integer, String> indexToName = new HashMap<>();

        for(int i = 0; i < columnNames.length; i++) {
            final String columnName = columnNames[i];
            indexToName.put(i,columnName);
        }

        for(int i = 1; i < linesOfCsv.size();i++) {
            final String[] values = linesOfCsv.get(i).split(",");
            RawDataObject rawDataObject = new RawDataObject(sourceName);
            for(int j = 0; j < values.length; j++){
                rawDataObject.setAttribute(indexToName.get(j), values[j]);
            }
            ret.add(rawDataObject);
        }
        return ret;
    }
}
