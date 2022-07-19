package org.nice.ase.tdd.sentiment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SentimentAnalytics {
    private InteractionGateway interactionGateway;

    public SentimentAnalytics(InteractionGateway interactionGateway) {
        this.interactionGateway = interactionGateway;
    }

    public Optional<AnalyticsResult> analyze(String id) {
        return interactionGateway.getById(id).map(this::analyze);
    }

    private AnalyticsResult analyze(Interaction interaction) {
        if (interaction.getSegments().isEmpty()) {
            return AnalyticsResult.empty();
        }
        double positive = 0;
        double neutral = 0;
        double negative = 0;
        List<Sentiment> sentiments = getSentiments(interaction);
        for (Sentiment sentiment : sentiments) {
            positive += sentiment.getPositive();
            neutral += sentiment.getNeutral();
            negative += sentiment.getNegative();
        }
        long size = sentiments.size();
        return AnalyticsResult.of(positive / size, neutral / size, negative / size);
    }

    private List<Sentiment> getSentiments(Interaction interaction) {
        List<Sentiment> sentiments = interaction.getSegments().stream().map(Segment::calculateSentiment).collect(Collectors.toList());
        return sentiments;
    }

}
