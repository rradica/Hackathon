package ch.opendata.hack.energy.sources;

import ch.opendata.hack.energy.sources.csv.CsvSource;
import ch.opendata.hack.energy.sources.rest.RestReaderService;
import ch.opendata.hack.energy.sources.rest.RestSource;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class Sources {
    @Transient
    private final RestReaderService restReaderService;
    private final List<Source> sources = new ArrayList<>();

    public Sources(@Autowired RestReaderService restReaderService) {
        this.restReaderService = restReaderService;
        sources.add(new RestSource("DailyGasFlowInAndOutOfCH", "https://bfeprototype.sh1.hidora.com/DailyGasFlowInAndOutOfCH", this.restReaderService));
        sources.add(new RestSource("EnergyConsumptionPrognoseSDSC", "https://bfeprototype.sh1.hidora.com/ElectricityConsumptionPrognoseSDSC", this.restReaderService));
        sources.add(new RestSource("GasNettoImport", "https://bfeprototype.sh1.hidora.com/GasNettoImport", this.restReaderService));
        sources.add(new CsvSource("energiedashboard.ch: Tägliche Flüsse in die und aus der Schweiz (Gas)","https://bfe-energy-dashboard-ogd.s3.amazonaws.com/ogd101_gas_import_export.csv"));
        sources.add(new CsvSource("Kennzahlen alternativ angetriebene PW","https://www.uvek-gis.admin.ch/BFE/ogd/92/data-cantons.csv"));
        sources.add(new CsvSource("energiedashboard.ch: Füllstände Gasspeicher EU","https://bfe-energy-dashboard-ogd.s3.amazonaws.com/ogd102_fuellstand_gasspeicher.csv"));
    }

    public List<Source> getSources() {
        return Collections.unmodifiableList(sources);
    }
}