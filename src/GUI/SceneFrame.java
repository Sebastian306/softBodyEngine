package GUI;

import Physic.Canvas.SpacePanel;
import Physic.GameEngine;
import Physic.Telemetry.Telemetry;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.BiConsumer;


public class SceneFrame extends JFrame {
    private int width = 600;
    private int height = 600;
    private Telemetry tel = new Telemetry();
    private GameEngine ge = new GameEngine();
    private SpacePanel sp1 = new SpacePanel(ge);
    private SpacePanel sp2 = new SpacePanel(ge);
    private JPanel mainPanel = new JPanel();
    private double startTime;

    public SceneFrame(BiConsumer<SpacePanel, GameEngine> scene) {
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        sp2.setDrawMode(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        sp1.setTelemetry(tel);
        sp2.setTelemetry(tel);
        ge.setTelemetry(tel);

        scene.accept(sp1, ge);

        mainPanel.add(sp1);
        mainPanel.add(sp2);

        this.add(mainPanel);
        this.setSize(1200, 600);
        this.setLocationRelativeTo(null);

        final double startTime = System.currentTimeMillis();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                ge.setRunning(false);
                EndFunctions.windowClosing(tel, startTime);
            }
        });

        Thread gameTread = new Thread(ge);
        gameTread.start();
        this.setVisible(true);
    }

    @Override
    public void dispose() {
        EndFunctions.windowClosing(tel, startTime);
        super.dispose();
    }
}
