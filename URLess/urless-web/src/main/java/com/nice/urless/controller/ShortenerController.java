package com.nice.urless.controller;

import com.nice.urless.dto.CreateURLCollectionRequest;
import com.nice.urless.dto.CreateURLRequest;
import com.nice.urless.dto.CreateURLResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shortener.ShortenURL;
import usecases.ShortenerCollectionUseCase;
import usecases.ShortenerUseCase;

import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class ShortenerController {
    private ShortenerUseCase useCase;
    private ShortenerCollectionUseCase shortenerCollectionUserCase;

    public ShortenerController(ShortenerUseCase useCase, ShortenerCollectionUseCase shortenerCollectionUserCase) {
        this.useCase = useCase;
        this.shortenerCollectionUserCase = shortenerCollectionUserCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable(value = "id") String id) {
        if (id.length() > 5) {
            return useCase.getById(id)
                    .map(redirect())
                    .orElseGet(notFound());
        } else {
            Optional<Map<String, List<ShortenURL>>> urls = shortenerCollectionUserCase.getById(id);
            if (urls.isPresent()) {
                Optional<List<ShortenURL>> shortenUrlCollection = urls.get().values().stream().findFirst();
                List<CreateURLResponse> responses = shortenUrlCollection.get().stream().map
                        (url -> new CreateURLResponse(url.getUrl(), url.getOriginalUrl())).collect(Collectors.toList());
                return new ResponseEntity(responses, HttpStatus.OK);
            } else {
                notFoundCollection();
            }
        }
        return new ResponseEntity("Resource not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/collection")
    public ResponseEntity postUrlCollection(@RequestBody CreateURLCollectionRequest request) {
        Map<String, List<ShortenURL>> urlsMap = shortenerCollectionUserCase.create(request.getUrlCollection());
        String responseUrl = "http://urle.ss/" + urlsMap.keySet().stream().collect(Collectors.toList()).get(0);
        return ResponseEntity.created(URI.create(responseUrl))
                .body(populateCollectionResponse(urlsMap));
    }

    private Object populateCollectionResponse(Map<String, List<ShortenURL>> urlsMap) {
        List<CreateURLResponse> responses = new ArrayList<>();
        List<ShortenURL> shortenUrls = urlsMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        for (ShortenURL url : shortenUrls) {
            responses.add(new CreateURLResponse(url.getUrl(), url.getOriginalUrl()));
        }
        return responses;
    }

    @PostMapping
    public ResponseEntity post(@RequestBody CreateURLRequest request) {
        ShortenURL url = useCase.create(request.getUrl());
        String responseUrl = "http://urle.ss/" + url.getId();
        return ResponseEntity.created(URI.create(responseUrl))
                .body(new CreateURLResponse(responseUrl, url.getUrl()));
    }

    private Supplier<ResponseEntity<Object>> notFound() {
        return () -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("URL does not exists");
    }

    private Supplier<ResponseEntity<Object>> notFoundCollection() {
        return () -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("URL Collection does not exists");
    }

    private Function<ShortenURL, ResponseEntity<Object>> redirect() {
        return s -> ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", s.getUrl()).build();
    }
}
