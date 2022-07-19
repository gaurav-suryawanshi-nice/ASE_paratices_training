package org.nice.ase.tdd.sentiment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Segment {
    public abstract int getSize();

    protected abstract List<String> getSentimentString();

    public Sentiment calculateSentiment() {
        Map<String, Integer> frequency = new HashMap<>();
        getSentimentString().stream().forEach(string -> frequency.compute(string, (k, count) -> (count == null) ? 1 : (count + 1)));
        return getSentiment(frequency);
    }

    protected Sentiment getSentiment(Map<String, Integer> frequency) {
        long size = getSize();
        double positive = ((double) frequency.get("Positive") / size);
        double neutral = ((double) frequency.get("Neutral") / size);
        double negative = ((double) frequency.get("Negative") / size);
        return new Sentiment(positive, neutral, negative);
    }
}
