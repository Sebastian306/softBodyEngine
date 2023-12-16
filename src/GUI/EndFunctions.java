package GUI;

import Physic.Telemetry.Telemetry;
import Physic.Telemetry.TimeData;

import java.awt.event.WindowEvent;
import java.util.Map;

/**
 * A utility class containing static methods for handling end-of-program events and generating plots based on telemetry data.
 */
public class EndFunctions {

    /**
     * Handles the window closing event, printing telemetry data.
     *
     * @param telemetry The Telemetry object containing time data.
     * @param StartTime The start time of the program.
     */
    public static void windowClosing(Telemetry telemetry, double StartTime) {
        Map<String, TimeData> data = telemetry.getTimeUsesInfo();
        String collPlot = null;
        String accPlot = null;

        // Print time information for each telemetry category
        for (String tut : data.keySet()) {
            TimeData inf = data.get(tut);
            System.out.println(String.format("%s | sum : %f ms, mean : %f ms, min : %f ms, max: %f ms",
                    tut, inf.getTimeSum(), inf.getMeanTime(), inf.getMinRecord(), inf.getMaxRecord()));
        }

        // Print the total time taken by the program
        System.out.println(String.format("total time : %f ms\n", System.currentTimeMillis() - StartTime));

    }

}