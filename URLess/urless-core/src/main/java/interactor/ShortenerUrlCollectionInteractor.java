package interactor;

import exception.FailedToCreatURLException;
import exception.URLAlreadyExists;
import gateway.URLCollectionGateway;
import generator.IDGenerator;
import shortener.ShortenURL;
import usecases.ShortenerCollectionUseCase;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ShortenerUrlCollectionInteractor implements ShortenerCollectionUseCase {
    private URLCollectionGateway urlGateway;
    private IDGenerator idGenerator;

    public ShortenerUrlCollectionInteractor(URLCollectionGateway urlGateway, IDGenerator idGenerator) {
        this.urlGateway = urlGateway;
        this.idGenerator = idGenerator;
    }

    @Override
    public Optional<Map<String, List<ShortenURL>>> getById(String id) {
        return this.urlGateway.getById(id);

    }

    @Override
    public Map<String, List<ShortenURL>> create(List<String> urls) {
        Map<String, String> urlCollection = new HashMap<>();
        shortenUrlFromCollection(urls, urlCollection);
        if (!urlCollection.isEmpty()) {
            boolean collision = false;
            Set<String> collisions = new HashSet<>();
            do {
                try {
                    List<String> collection = urlCollection.values().stream().collect(Collectors.toList());
                    String colId = idGenerator.generate(collection, collisions);
                    return urlGateway.create(collection, colId);
                } catch (URLAlreadyExists urlAlreadyExists) {
                    collision = true;
                    collisions.add(urlAlreadyExists.getUrl());
                } catch (IOException ioException) {
                    throw new FailedToCreatURLException();
                }
            } while (collision);
        }
        throw new FailedToCreatURLException();

    }

    private void shortenUrlFromCollection(List<String> urls, Map<String, String> urlCollection) {
        for (String url : urls) {
            boolean collision = false;
            Set<String> collisions = new HashSet<>();
            do {
                try {
                    String id = idGenerator.generate(url, collisions);
                    if (!urlCollection.containsKey(id)) {
                        urlCollection.put(id, url);
                    } else {
                        throw new URLAlreadyExists(url);
                    }
                } catch (URLAlreadyExists urlAlreadyExists) {
                    collision = true;
                    collisions.add(urlAlreadyExists.getUrl());
                } catch (FailedToCreatURLException failedToCreatURLException) {
                    throw new FailedToCreatURLException();
                }
            } while (collision);

        }
    }
}
