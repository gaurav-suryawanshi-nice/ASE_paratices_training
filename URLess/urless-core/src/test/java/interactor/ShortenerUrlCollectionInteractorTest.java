package interactor;

import fake.IDGeneratorFake;
import gateway.URLCollectionGateway;
import gateway.URLCollectionGatewayFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shortener.ShortenURL;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShortenerUrlCollectionInteractorTest {
    private ShortenerUrlCollectionInteractor sut;
    private URLCollectionGateway gateway;
    private IDGeneratorFake idGenerator;

    @BeforeEach
    public void setUp() {
        gateway = new URLCollectionGatewayFake();
        idGenerator = new IDGeneratorFake();
        sut = new ShortenerUrlCollectionInteractor(gateway, idGenerator);
    }


    @Test
    public void shouldReturnEmptyOnNonExistingURLTest() {
        //arrange & act
        Optional<Map<String, List<ShortenURL>>> result = sut.getById("NON-EXISTING-URL");
        //assert
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void shouldReturnOnExistingURLTest() {
        //arrange
        List<String> urlCollection = Arrays.asList("http://unit.test");
        gateway.create(urlCollection, "12vfv");
        //act
        Map<String, List<ShortenURL>> result = sut.getById("12vfv").get();
        //assert
        assertNotNull(result.get("12vfv"));
    }

    @Test
    public void shouldReturnCreatedURLTest() {
        //arrange
        idGenerator.add("avcDE");
        List<String> urlCollection = Arrays.asList("http://test.com");
        //act
        Map<String, List<ShortenURL>> result = sut.create(urlCollection);
        //assert
        assertNotNull(result.get("avcDE"));
        assertEquals("http://test.com", result.get("avcDE").get(0).getOriginalUrl());
    }

}
