package org.nice.ase.tdd.sentiment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SentimentAnalyticsTest {

    private SentimentAnalytics sut;
    private InteractionGateway interactionGateway;

    @BeforeEach
    void setUp() {
        interactionGateway = new InteractionGatewayFake();
        sut = new SentimentAnalytics(interactionGateway);
    }

    @Test
    public void emptyResultOnNonExistingInteractionTest() {
        //act
        Optional<AnalyticsResult> result = sut.analyze("NON-EXISTING-INTERACTION");
        //assert
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void resultOfInteractionWithoutSegmentsTest() {
        //arrange
        Interaction interaction = new Interaction();
        String id = interactionGateway.create(interaction);
        //act
        AnalyticsResult result = sut.analyze(id).get();
        //assert
        assertEquals(Optional.empty(), result.getSentiment());
    }

    @Test
    public void resultOfInteractionWithSingleSegmentTest() {
        //arrange
        TextSegment segment = segmentForSentiment(0.7, 0.1, 0.2);
        Interaction interaction = new Interaction();
        interaction.addSegment(segment);
        String id = interactionGateway.create(interaction);
        //act
        AnalyticsResult result = sut.analyze(id).get();
        Sentiment sentiment = result.getSentiment().get();
        //assert
        assertSentimentResult(sentiment, 0.7, 0.1, 0.2);

    }

    //@Test
    public void resultOfInteractionWithMultipleSegmentTest() {
        //arrange
        TextSegment segment1 = segmentForSentiment(0.5, 0.2, 0.3);
        TextSegment segment2 = segmentForSentiment(0.7, 0.1, 0.2);
        Interaction interaction = new Interaction();
        interaction.addSegment(segment1);
        interaction.addSegment(segment2);
        String id = interactionGateway.create(interaction);
        //act
        AnalyticsResult result = sut.analyze(id).get();
        Sentiment sentiment = result.getSentiment().get();
        //assert
        assertSentimentResult(sentiment, 0.6, 0.15, 0.25);
    }

    @Test
    public void resultOfInteractionWithVoiceSegmentTest() {
        //arrange
        VoiceSegment voiceSegment = generateVoiceSentiment(0.9, 0.04, 0.06);
        Interaction interaction = new Interaction();
        interaction.addSegment(voiceSegment);
        String id = interactionGateway.create(interaction);
        //act
        AnalyticsResult result = sut.analyze(id).get();
        Sentiment sentiment = result.getSentiment().get();
        //assert
        assertSentimentResult(sentiment, 0.9, 0.04, 0.06);
    }

    @Test
    public void resultOfInteractionWithVoiceAndTextSegmentTest() {
        //arrange
        VoiceSegment voiceSegment = generateVoiceSentiment(0.4, 0.5, 0.1);
        TextSegment textSegment = segmentForSentiment(0.6, 0.3, 0.1);
        Interaction interaction = new Interaction();
        interaction.addSegment(voiceSegment);
        interaction.addSegment(textSegment);
        String id = interactionGateway.create(interaction);
        //act
        AnalyticsResult result = sut.analyze(id).get();
        Sentiment sentiment = result.getSentiment().get();
        //assert
        assertSentimentResult(sentiment, 0.5, 0.4, 0.1);
    }

    private void assertSentimentResult(Sentiment sentiment, double positive, double neutral, double negative) {
        assertEquals(positive, sentiment.getPositive(), "Positive");
        assertEquals(neutral, sentiment.getNeutral(), "Neutral");
        assertEquals(negative, sentiment.getNegative(), "Negative");
    }

    private TextSegment segmentForSentiment(double positive, double neutral, double negative) {
        List<String> text = new ArrayList<>(3);
        text.addAll(generateStringList(sizeOfScore(positive), "Positive"));
        text.addAll(generateStringList(sizeOfScore(neutral), "Neutral"));
        text.addAll(generateStringList(sizeOfScore(negative), "Negative"));
        return new TextSegment(text);
    }

    private List<String> generateStringList(int sizeOfScore, String word) {
        List<String> score = new ArrayList<>(sizeOfScore);
        for (int i = 0; i < sizeOfScore; i++) {
            score.add(word);
        }
        return score;
    }

    private int sizeOfScore(double positive) {
        return (int) (100 * positive);
    }

    private VoiceSegment generateVoiceSentiment(double positive, double neutral, double negative) {
        List<Integer> peaks = new ArrayList<>(3);
        peaks.addAll((generatePeaks(sizeOfScore(positive), 75, 100)));
        peaks.addAll((generatePeaks(sizeOfScore(neutral), 50, 74)));
        peaks.addAll((generatePeaks(sizeOfScore(negative), 1, 49)));
        return new VoiceSegment(peaks);
    }

    private List<Integer> generatePeaks(int size, int from, int to) {
        List<Integer> result = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            result.add(random.nextInt(to - from) + from);
        }
        return result;
    }
}
