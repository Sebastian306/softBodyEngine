import Physic.Telemetry.Telemetry;
import Physic.Telemetry.TimeData;

import java.awt.event.WindowEvent;
import java.util.Map;

public class EndFunctions {
    public static void windowClosing(WindowEvent evt, Telemetry telemetry, double StartTime) {
        Map<String, TimeData> data = telemetry.getTimeUsesInfo();
        StringBuilder collPlot = new StringBuilder();
        for(String tut: data.keySet()){
            TimeData inf = data.get(tut);
            System.out.println(String.format("%s | sum : %f ms, mean : %f ms, min : %f ms, max: %f ms",
                    tut, inf.getTimeSum(), inf.getMeanTime(), inf.getMinRecord(), inf.getMaxRecord()));
            if(tut == "collisionCalculations"){
                collPlot.append("______________________________Collision_Plot______________________________\n");
                double times[] = new double[]{0., 0.25, 0.5, 1., 1.5, 2.5, 5., 10., 100000.};
                double scale = 100;
                for(int i = 0; i<times.length-1; i++){
                    final int ind = i;
                    double count = inf.getRecords().stream().filter(v -> v >= times[ind] && v < times[ind+1]).count();
                    collPlot.append(String.format("  |%s -> [%f ; %f) : %d\n",
                            "*".repeat((int)(scale * count/inf.getRecords().size())),
                            times[i], times[i+1], (int)count));
                }
                collPlot.append("__________________________________________________________________________\n");
            }
        }
        System.out.println(String.format("total time : %f ms", System.currentTimeMillis() - StartTime));
        System.out.println(collPlot.toString());
        System.exit(0);
    }
}
