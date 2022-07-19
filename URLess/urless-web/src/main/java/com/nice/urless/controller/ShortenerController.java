package com.nice.urless.controller;

import com.nice.urless.dto.CreateURLRequest;
import com.nice.urless.dto.CreateURLResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shortener.ShortenURL;
import usecases.ShortenerUseCase;

import java.net.URI;
import java.util.function.Function;
import java.util.function.Supplier;

@RestController
@RequestMapping("")
public class ShortenerController {
    private ShortenerUseCase useCase;

    public ShortenerController(ShortenerUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable(value = "id") String id) {
        return useCase.getById(id)
                .map(redirect())
                .orElseGet(notFound());

    }

    @PostMapping(value = "/", consumes = {"*/*"})
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

    private Function<ShortenURL, ResponseEntity<Object>> redirect() {
        return s -> ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", s.getUrl()).build();
    }
}
