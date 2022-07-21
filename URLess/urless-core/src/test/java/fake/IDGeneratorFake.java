package fake;

import generator.IDGenerator;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Getter
public class IDGeneratorFake implements IDGenerator {
    List<String> ids = new ArrayList<>();
    int current = 0;
    Set<String> collisions;

    @Override
    public String generate(String url, Set<String> collisions) {
        String result = ids.get(current);
        current = (current + 1) % ids.size();
        this.collisions = collisions;
        return result;
    }

    @Override
    public String generate(List<String> urls, Set<String> collisions) throws IOException {
        String result = ids.get(current);
        current = (current + 1) % ids.size();
        this.collisions = collisions;
        return result;
    }

    public void add(String... inputIds) {
        ids.addAll(Arrays.asList(inputIds));
    }
}
