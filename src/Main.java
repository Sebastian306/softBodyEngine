import Physic.Connections.Connection;
import Physic.Connections.SpringConnection;
import Physic.Forces.Force;
import Physic.Forces.GravityForce;
import Physic.Objects.Pivot;
import Physic.SpacePanel;
import Physic.Telemetry.Telemetry;
import Physic.Telemetry.TimeData;
import Physic.Vec2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;
import java.util.Arrays;
import java.util.Map;

import static Physic.Scenes.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void createBlob1(SpacePanel sp, double blobX, double blobY, double blobSize){

    }

    public static void main(String[] args) {

        JFrame MainFrame = new JFrame();
        MainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        SpacePanel sp = new SpacePanel();

        final Telemetry tel = new Telemetry();
        sp.setTelemetry(tel);

        Scene2(sp);

        MainFrame.add(sp);
        MainFrame.setSize(600,600);
        MainFrame.setLocationRelativeTo(null);

        final double lastTime = System.currentTimeMillis();
        MainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                Map<String, TimeData> data = tel.getTimeUsesInfo();
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
                System.out.println(String.format("total time : %f ms", System.currentTimeMillis() - lastTime));
                System.out.println(collPlot.toString());
                System.exit(0);
            }
        });

        MainFrame.setVisible(true);
    }
}