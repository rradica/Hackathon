package ch.opendata.hack.energy.listener;

import ch.opendata.hack.energy.model.DatabaseObject;
import ch.opendata.hack.energy.repository.ObjectRepository;
import ch.opendata.hack.energy.sources.RawDataObject;
import ch.opendata.hack.energy.sources.Source;
import ch.opendata.hack.energy.sources.Sources;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class BootUpInitializer implements
        ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(BootUpInitializer.class);

    public static int counter;


    private final ObjectRepository repository;

    private final Sources sources;

    public BootUpInitializer(@Autowired ObjectRepository repository,@Autowired Sources sources) {

        this.repository = repository;
        this.sources = sources;
    }

    @Override public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.info("Increment counter");
        counter++;

        final List<RawDataObject> rawDataObjectList = new ArrayList<>();

        for(Source source : sources.getSources()) {
            rawDataObjectList.addAll(source.readSource());
        }

        for(RawDataObject rawDataObject : rawDataObjectList) {
            DatabaseObject databaseObject = new DatabaseObject();
            databaseObject = repository.save(databaseObject);
            databaseObject.readValuesFromRawObject(rawDataObject);
            databaseObject.setDatasource(rawDataObject.getSourceName());
            repository.save(databaseObject);
        }

    }
}