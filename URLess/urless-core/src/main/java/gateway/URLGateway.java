package gateway;

import exception.URLAlreadyExists;
import shortener.ShortenURL;

import java.util.List;
import java.util.Optional;

public interface URLGateway {
    public ShortenURL create(String url, String id) throws URLAlreadyExists;

    Optional<ShortenURL> getById(String id);

    public List<ShortenURL> getAll();
}
