package Physic.Telemetry;

import java.util.*;

/**
 * A class for collecting and analyzing time-related data.
 */
public class TimeData {

    // Sum of recorded times
    private double timeSum;

    // Count of recorded times
    private double recordsCount;

    // Maximum number of records to collect
    private double maxRecordToCollect = 100;

    // Minimum recorded time
    private double minRecord = Double.POSITIVE_INFINITY;

    // Maximum recorded time
    private double maxRecord = Double.NEGATIVE_INFINITY;

    // List to store recorded times
    private Set<Double> records;

    private Map<Double, Double> recordsMap;

    /**
     * Constructs a TimeData object.
     */
    public TimeData() {
        this.timeSum = 0;
        this.recordsMap = new TreeMap<>();
        this.records = new TreeSet<>();
        this.recordsCount = 0;
    }

    /**
     * Adds a time record to the data.
     *
     * @param time The time to be recorded.
     */
    public void addRecord(double time) {
        minRecord = Math.min(minRecord, time);
        maxRecord = Math.max(maxRecord, time);
        timeSum += time;
        recordsCount++;
    }

    /**
     * Gets the sum of recorded times.
     *
     * @return The sum of recorded times.
     */
    public double getTimeSum() {
        return timeSum;
    }

    /**
     * Gets the list of recorded times.
     *
     * @return The list of recorded times.
     */
    public Set<Double> getRecords() {
        return records;
    }

    /**
     * Gets the mean time (average time) of recorded times.
     *
     * @return The mean time of recorded times.
     */
    public double getMeanTime() {
        return timeSum / recordsCount;
    }

    /**
     * Gets the maximum recorded time.
     *
     * @return The maximum recorded time.
     */
    public double getMaxRecord() {
        return maxRecord;
    }

    /**
     * Gets the minimum recorded time.
     *
     * @return The minimum recorded time.
     */
    public double getMinRecord() {
        return minRecord;
    }
}
