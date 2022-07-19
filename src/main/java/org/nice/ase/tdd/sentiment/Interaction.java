package org.nice.ase.tdd.sentiment;

import java.util.ArrayList;
import java.util.List;

public class Interaction {
    private List<Segment> segments = new ArrayList<>();

    public void addSegment(Segment segment) {
        segments.add(segment);
    }

    public List<Segment> getSegments() {
        return segments;
    }
}
