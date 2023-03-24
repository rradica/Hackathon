package ch.opendata.hack.energy.sources.rest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestReaderService {

    private final RestTemplate restTemplate;

    public RestReaderService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String send(String url) {
        return restTemplate.getForObject(url, String.class);
    }
}