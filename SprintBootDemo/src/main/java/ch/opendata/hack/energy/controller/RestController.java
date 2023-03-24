package ch.opendata.hack.energy.controller;

import ch.opendata.hack.energy.SearchResult;
import ch.opendata.hack.energy.model.DatabaseObject;
import ch.opendata.hack.energy.repository.ObjectRepository;
import ch.opendata.hack.energy.sources.LocalDateAdapter;
import ch.opendata.hack.energy.sources.Sources;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private static final Logger logger = LoggerFactory.getLogger(RestController.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
    private final ObjectRepository repository;
    private final Sources sources;

    public RestController(@Autowired ObjectRepository repository, @Autowired Sources sources) {
        this.repository = repository;
        this.sources = sources;
    }

    @GetMapping("/getall")
    public ResponseEntity<String> getall(@RequestParam("filter") Optional<String> filter) throws JsonProcessingException {

        List<DatabaseObject> objects = repository.findAll();

        objects.forEach(object -> object.initDataypes());

        if(filter.isPresent()) {
            String[] params = filter.get().split(";");

            for(String param : params) {

                if(param.startsWith("hasAttribute:")) {
                    String criterias = param.substring(param.indexOf(":") + 1);
                    String[] sepCriterias = criterias.split(",");

                    objects = objects.stream().filter(object -> {

                        boolean found = true;

                        for(String val : sepCriterias) {
                            if(!object.hasAttribute(val)) {
                                found &= Boolean.FALSE;
                            }
                        }
                        return found;

                    }).collect(Collectors.toList());

                    System.out.println("Test");
                }

            }
        }


        final SearchResult searchResult = SearchResult.create(objects);
        final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class,new LocalDateAdapter()).setPrettyPrinting().create();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(gson.toJson(searchResult));
    }


    @GetMapping("/getFromSource")
    public ResponseEntity<String> getFromSource(@RequestParam("name") String name) throws JsonProcessingException {

        DatabaseObject objectExample = new DatabaseObject();
        objectExample.setDatasource(name);
        Example<DatabaseObject> example = Example.of(objectExample);

        List<DatabaseObject> objects = repository.findAll(example);

        objects.forEach(DatabaseObject::initDataypes);


        final SearchResult searchResult = SearchResult.create(objects);
        final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class,new LocalDateAdapter()).setPrettyPrinting().create();

        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(gson.toJson(searchResult));
    }

    @GetMapping("/getAllRegisteredDataSources")
    public ResponseEntity<String> getAllRegisteredDataSources() {

        final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class,new LocalDateAdapter()).setPrettyPrinting().create();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(gson.toJson(this.sources.getSources().stream().map(source -> source.getName()).collect(Collectors.toList())));
    }
}