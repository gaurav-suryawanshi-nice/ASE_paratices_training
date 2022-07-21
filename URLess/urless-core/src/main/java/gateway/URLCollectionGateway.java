package gateway;

import exception.URLAlreadyExists;
import shortener.ShortenURL;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface URLCollectionGateway {
    public Map<String, List<ShortenURL>> create(List<String> url, String id) throws URLAlreadyExists;

    Optional<Map<String, List<ShortenURL>>> getById(String id);

    Map<String, List<ShortenURL>> getAll();
}
