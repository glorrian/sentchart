package dev.sentchart.io;

public record SignalPoint(Double time, Double signal) implements Comparable<SignalPoint> {
    public boolean containsNull() {
        return time == null || signal == null;
    }

    @Override
    public int compareTo(SignalPoint point) {
        if (!point.containsNull()) {
            return Double.compare(time, point.time);
        }
        return 1;
    }
}
