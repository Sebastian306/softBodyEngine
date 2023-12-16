package Physic.Telemetry;

import java.util.Map;
import java.util.TreeMap;

/**
 * A telemetry class for collecting and managing time-related data.
 */
public class Telemetry {

    // Map to store time-related data for different tasks
    private Map<String, TimeData> timeUsesInfo = new TreeMap<String, TimeData>();

    // Singleton instance for the main telemetry
    public static Telemetry mainTelemetry = new Telemetry();

    /**
     * Adds time information for a specific task.
     *
     * @param taskName The name of the task.
     * @param time     The time spent on the task.
     */
    public void addTimeInfo(String taskName, double time) {
        // If the task is not in the map, add it with a new TimeData instance
        if (!timeUsesInfo.containsKey(taskName))
            timeUsesInfo.put(taskName, new TimeData());

        // Try to add the time record to the TimeData instance for the task
        try {
            timeUsesInfo.get(taskName).addRecord(time);
        } catch (Exception e) {
            // Handle exceptions (empty catch block in the original code)
        }
    }

    /**
     * Gets the map of time-related data for different tasks.
     *
     * @return The map of time-related data.
     */
    public Map<String, TimeData> getTimeUsesInfo() {
        return timeUsesInfo;
    }
}
