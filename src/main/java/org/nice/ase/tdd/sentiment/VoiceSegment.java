package org.nice.ase.tdd.sentiment;

import java.util.List;
import java.util.stream.Collectors;

public class VoiceSegment extends Segment {
    private final List<Integer> peaks;

    public VoiceSegment(List<Integer> peaks) {
        this.peaks = peaks;
    }

    @Override
    public int getSize() {
        return peaks.size();
    }

    @Override
    protected List<String> getSentimentString() {
        return peaks.stream().map(this::toStringRepresentation).collect(Collectors.toList());
    }

    private String toStringRepresentation(Integer integer) {
        if (integer >= 1 && integer < 50) {
            return "Negative";
        } else if (integer >= 50 && integer < 75) {
            return "Neutral";
        } else {
            return "Positive";
        }
    }
}

