package ch.opendata.hack.energy.sources.csv;

import ch.opendata.hack.energy.sources.RawDataObject;
import ch.opendata.hack.energy.sources.Source;
import java.io.IOException;
import java.util.List;

public class CsvSource extends Source {

    private final String url;

    public CsvSource(String sourceName, String url) {
        this.setName(sourceName);
        this.url = url;
    }

    @Override
    public List<RawDataObject> readSource() {
        List<String> lines = null;
        try {
            lines = CsvReader.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return CsvRawDataReader.read(this.getName(),lines);
    }
}
