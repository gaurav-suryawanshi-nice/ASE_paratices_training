package usecases;

import shortener.ShortenURL;

import java.util.Optional;

public interface ShortenerUseCase {
    Optional<ShortenURL> getById(String id);

    ShortenURL create(String url);
}
