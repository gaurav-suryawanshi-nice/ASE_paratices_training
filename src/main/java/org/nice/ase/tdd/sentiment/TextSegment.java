package org.nice.ase.tdd.sentiment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextSegment extends Segment {
    private final List<String> text;

    public TextSegment(List<String> text) {
        this.text = text;
    }

    public List<String> getText() {
        return this.text;
    }

    @Override
    public int getSize() {
        return text.size();
    }

    @Override
    public Sentiment calculateSentiment() {
        Map<String, Integer> frequency = new HashMap<>();
        text.stream().forEach(string -> frequency.compute(string, (k, count) -> (count == null) ? 1 : (count + 1)));
        return getSentiment(frequency);

    }

    @Override
    protected List<String> getSentimentString() {
        return text;
    }


}
