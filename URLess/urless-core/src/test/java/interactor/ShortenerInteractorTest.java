package interactor;

import fake.IDGeneratorFake;
import gateway.URLGateway;
import gateway.URLGatewayFake;
import generator.IDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shortener.ShortenURL;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ShortenerInteractorTest {

    private ShortenerInteractor sut;
    private URLGateway gateway;
    private IDGeneratorFake idGeneratorFake;


    @BeforeEach
    public void setUp() {
        gateway = new URLGatewayFake();
        idGeneratorFake = new IDGeneratorFake();
        sut = new ShortenerInteractor(gateway, idGeneratorFake);
    }


    @Test
    public void shouldReturnEmptyOnNonExistingURLTest() {
        //arrange & act
        Optional<ShortenURL> result = sut.getById("NON-EXISTING-URL");
        //assert
        assertEquals(Optional.empty(), result);
    }


    @Test
    public void shouldReturnOnExistingURLTest() {
        //arrange
        gateway.create("http://unit.test", "12vfvf");
        //act
        ShortenURL result = sut.getById("12vfvf").get();
        //assert
        assertEquals("12vfvf", result.getId());
    }

    @Test
    public void shouldReturnCreatedURLTest() {
        //arrange
        idGeneratorFake.add("avcDEc");
        //act
        ShortenURL result = sut.create("http://test.com");
        //assert
        assertEquals("avcDEc", result.getId());
        assertEquals("http://test.com", result.getUrl());
    }

    @Test
    public void shouldCreateDifferentShortenedURLsTest() {
        //arrange
        idGeneratorFake.add("abcdef", "abcdef", "abcdef", "ghijkl");
        //act
        ShortenURL result1 = sut.create("http://duplication.test");
        ShortenURL result2 = sut.create("http://duplication.test");
        //assert
        assertNotEquals(result1.getId(), result2.getId());
        assertEquals("ghijkl", result2.getId());
        assertEquals(1, idGeneratorFake.getCollisions().size());
        assertTrue(idGeneratorFake.getCollisions().contains("abcdef"));
    }
}
