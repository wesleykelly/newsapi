package com.example.newsapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.cache.annotation.Cacheable;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/api")
public class NewsController {

    private final RestTemplate restTemplate;

    // Inject the RestTemplate bean
    @Autowired
    public NewsController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "articles", key = "{#q, #lang, #country, #max, #in, #nullable, #from, #to, #sortby, #page, #expand}")
    @GetMapping("/search")
    public ResponseEntity<String> searchArticles(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String lang,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer max,
            @RequestParam(required = false) String in,
            @RequestParam(required = false) String nullable,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to,
            @RequestParam(required = false) String sortby,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String expand) {

        // Construct the URL with query parameters
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("https://gnews.io/api/v4/search")
                .queryParam("apikey", "d1d67fe845e62a049fcc395a350cd772")
                .queryParam("q", q)
                .queryParam("lang", lang)
                .queryParam("country", country)
                .queryParam("max", max)
                .queryParam("in", in)
                .queryParam("nullable", nullable)
                .queryParam("from", from != null ? from.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) : null)
                .queryParam("to", to != null ? to.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) : null)
                .queryParam("sortby", sortby)
                .queryParam("page", page)
                .queryParam("expand", expand);

        String urlToCall = uriBuilder.toUriString();

        // Make the call to GNews API
        ResponseEntity<String> response = restTemplate.getForEntity(urlToCall, String.class);

        // Set headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        // Return the response from GNews API with the 'Content-Type' header set
        return ResponseEntity.ok().headers(headers).body(response.getBody());
    }

}