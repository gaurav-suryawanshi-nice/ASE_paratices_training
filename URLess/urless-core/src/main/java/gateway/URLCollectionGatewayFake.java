package gateway;

import exception.URLAlreadyExists;
import shortener.ShortenURL;

import java.util.*;

public class URLCollectionGatewayFake implements URLCollectionGateway {
    private Map<String, List<ShortenURL>> urls = new HashMap<>();

    @Override
    public Map<String, List<ShortenURL>> create(List<String> urls, String id) throws URLAlreadyExists {
        List<ShortenURL> urlCollection = new ArrayList<>();
        for (String url : urls) {
            ShortenURL shortenURL = new ShortenURL();
            shortenURL.setOriginalUrl(url);
            shortenURL.setUrl(url + id);
            urlCollection.add(shortenURL);
        }
        this.urls.put(id, urlCollection);
        return this.urls;
    }

    @Override
    public Optional<Map<String, List<ShortenURL>>> getById(String id) {
        return Optional.ofNullable(this.urls);
    }

    @Override
    public Map<String, List<ShortenURL>> getAll() {
        return urls;
    }
}
