package generator;

import exception.IDGenerationFailureException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IDGenerator {
    String generate(String url, Set<String> collisions);

    String generate(List<String> urls, Set<String> collisions) throws IDGenerationFailureException;
}
