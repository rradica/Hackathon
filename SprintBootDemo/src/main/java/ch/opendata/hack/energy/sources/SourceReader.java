package ch.opendata.hack.energy.sources;

import java.util.List;

public interface SourceReader {
    List<RawDataObject> readSource();
}
