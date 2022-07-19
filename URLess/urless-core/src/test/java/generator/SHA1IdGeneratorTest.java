package generator;

import gateway.URLGatewayFake;
import interactor.ShortenerInteractor;
import org.junit.jupiter.api.Test;
import shortener.ShortenURL;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SHA1IdGeneratorTest {

    public static final String ID_REGEX = "^[a-zA-Z0-9-_]{6}$";

    @Test
    public void shouldResolveCollisionTest() {
        URLGatewayFake urlGatewayFake = new URLGatewayFake();
        SHA1IdGenerator generator = new SHA1IdGenerator();
        ShortenerInteractor interactor = new ShortenerInteractor(urlGatewayFake, generator);
        //act
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9-=]{6}$");
        ShortenURL result1 = interactor.create("htt://some.url");
        ShortenURL result2 = interactor.create("htt://some.url");
        //assert
        assertNotEquals(result1.getId(), result2.getId());
        assertTrue(result1.getId().matches(ID_REGEX), () -> idMessage(result1.getId()));
        assertTrue(result2.getId().matches(ID_REGEX), () -> idMessage(result2.getId()));
    }

    private String idMessage(String id) {
        return id + "does not match regex" + ID_REGEX;
    }
}
