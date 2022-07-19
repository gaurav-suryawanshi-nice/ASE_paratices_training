package gateway;

import exception.URLAlreadyExists;
import shortener.ShortenURL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class URLGatewayFake implements URLGateway {
    Map<String, ShortenURL> urls = new HashMap<>();

    @Override
    public ShortenURL create(String url, String id) {
        if (urls.containsKey(id)) {
            throw new URLAlreadyExists(id);
        }
        ShortenURL result = new ShortenURL(id, url);
        urls.put(id, result);
        return result;
    }

    @Override
    public Optional<ShortenURL> getById(String id) {
        return Optional.ofNullable(urls.get(id));
    }

    @Override
    public List<ShortenURL> getAll() {
        return urls.values().stream().collect(Collectors.toList());
    }
}
