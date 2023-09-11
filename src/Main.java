import Physic.Canvas.SpacePanel;
import Physic.GameEngine;
import Physic.Telemetry.Telemetry;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static Physic.Scenes.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void createBlob1(SpacePanel sp, double blobX, double blobY, double blobSize){

    }

    public static void main(String[] args) {

        JFrame MainFrame = new JFrame();
        MainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        GameEngine ge = new GameEngine();

        SpacePanel sp = new SpacePanel(ge);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        final Telemetry tel = new Telemetry();
        sp.setTelemetry(tel);
        ge.setTelemetry(tel);

        Scene3(sp, ge);

        mainPanel.add(sp);

        MainFrame.add(mainPanel);
        MainFrame.setSize(1200,600);
        MainFrame.setLocationRelativeTo(null);

        final double startTime = System.currentTimeMillis();
        MainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                ge.setRunning(false);
                EndFunctions.windowClosing(evt, tel, startTime);
            }
        });

        Thread gameTread = new Thread(ge);
        gameTread.start();
        MainFrame.setVisible(true);
    }
}