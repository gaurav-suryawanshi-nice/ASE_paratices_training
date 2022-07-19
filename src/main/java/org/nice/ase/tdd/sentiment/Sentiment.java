package org.nice.ase.tdd.sentiment;

public class Sentiment {
    private double positive;
    private double neutral;
    private double negative;

    public Sentiment(double positive, double neutral, double negative) {
        this.positive = positive;
        this.neutral = neutral;
        this.negative = negative;
    }

    public double getPositive() {
        return this.positive;
    }

    public double getNeutral() {
        return this.neutral;
    }

    public double getNegative() {
        return this.negative;
    }
}
