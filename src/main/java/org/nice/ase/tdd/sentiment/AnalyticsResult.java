package org.nice.ase.tdd.sentiment;

import java.util.Optional;

public class AnalyticsResult {
    private Sentiment sentiment;

    public static AnalyticsResult empty() {
        return new AnalyticsResult(null);
    }

    public static AnalyticsResult of(double positive, double neutral, double negative) {
        return new AnalyticsResult(new Sentiment(positive, neutral, negative));
    }

    public AnalyticsResult(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public Optional<Sentiment> getSentiment() {
        return Optional.ofNullable(this.sentiment);
    }
}
