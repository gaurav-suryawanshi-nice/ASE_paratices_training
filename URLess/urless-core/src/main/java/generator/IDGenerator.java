package generator;

import java.util.Set;

public interface IDGenerator {
    String generate(String url, Set<String> collisions);
}
