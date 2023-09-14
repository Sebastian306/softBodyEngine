package GUI;

import Physic.Telemetry.Telemetry;
import Physic.Telemetry.TimeData;

import java.awt.event.WindowEvent;
import java.util.Map;

public class EndFunctions {
    public static void windowClosing(Telemetry telemetry, double StartTime) {
        Map<String, TimeData> data = telemetry.getTimeUsesInfo();
        String collPlot = null;
        String accPlot = null;
        for(String tut: data.keySet()){
            TimeData inf = data.get(tut);
            System.out.println(String.format("%s | sum : %f ms, mean : %f ms, min : %f ms, max: %f ms",
                    tut, inf.getTimeSum(), inf.getMeanTime(), inf.getMinRecord(), inf.getMaxRecord()));
            if(tut == "collisionCalculations"){
                collPlot = plot("Collision_Plot", inf, new double[]{0., 0.25, 0.5, 1., 1.5, 2.5, 4., 5., 100000.});
            }
            if(tut == "accelerationCalculations"){
                accPlot = plot("Acc_Time_Plot", inf, new double[]{0., 0.0000001, 0.25, 0.5, 1., 1.5, 2.5, 5., 100000.});
            }
        }
        System.out.println(String.format("total time : %f ms", System.currentTimeMillis() - StartTime));
        if(collPlot != null)System.out.println(collPlot);
        if(accPlot != null)System.out.println(accPlot);
    }

    public static String plot(String title, TimeData inf, double range[]){
        StringBuilder plot = new StringBuilder();
        plot.append("______________________________" + title + "______________________________\n");
        double scale = 100;
        for(int i = 0; i<range.length-1; i++){
            final int ind = i;
            double count = inf.getRecords().stream().filter(v -> v >= range[ind] && v < range[ind+1]).count();
            plot.append(String.format("  |%s -> [%f ; %f) : %d\n",
                    "*".repeat((int)(scale * count/inf.getRecords().size())),
                    range[i], range[i+1], (int)count));
        }
        plot.append("____________________________________________________________" + "_".repeat(title.length()) + "\n");

        return plot.toString();
    }
}
