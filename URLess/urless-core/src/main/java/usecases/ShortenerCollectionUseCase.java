package usecases;

import shortener.ShortenURL;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ShortenerCollectionUseCase {
    Optional<Map<String, List<ShortenURL>>> getById(String id);

    Map<String, List<ShortenURL>> create(List<String> url);
}
