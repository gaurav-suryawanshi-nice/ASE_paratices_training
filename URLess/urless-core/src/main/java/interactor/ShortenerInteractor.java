package interactor;

import exception.FailedToCreatURLException;
import exception.URLAlreadyExists;
import gateway.URLGateway;
import generator.IDGenerator;
import shortener.ShortenURL;
import usecases.ShortenerUseCase;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ShortenerInteractor implements ShortenerUseCase {
    private URLGateway urlGateway;
    private IDGenerator idGenerator;

    public ShortenerInteractor(URLGateway urlGateway, IDGenerator idGenerator) {
        this.urlGateway = urlGateway;
        this.idGenerator = idGenerator;
    }

    @Override
    public Optional<ShortenURL> getById(String id) {
        return this.urlGateway.getById(id);
    }

    @Override
    public ShortenURL create(String url) {
        boolean collision = false;
        Set<String> collisions = new HashSet<>();
        do {
            try {
                String id = idGenerator.generate(url, collisions);
                return urlGateway.create(url, id);
            } catch (URLAlreadyExists urlAlreadyExists) {
                collision = true;
                collisions.add(urlAlreadyExists.getUrl());
            }
        } while (collision);
        throw new FailedToCreatURLException();
    }
}
