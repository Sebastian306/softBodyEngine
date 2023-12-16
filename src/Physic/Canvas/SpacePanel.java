package Physic.Canvas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

import Physic.ChunkBox;
import Physic.CollisionDetectors.*;
import Physic.Connections.Connection;
import Physic.Connections.SpringConnection;
import Physic.Forces.GravityForce;
import Physic.GameEngine;
import Physic.Mesh.Mesh;
import Physic.Objects.*;
import Physic.Objects.Point;
import Physic.Telemetry.Telemetry;
import Physic.Vec2d;

/**
 * A custom JPanel for rendering physical objects, connections, and collision detectors in a physics simulation.
 */
public class SpacePanel extends JPanel {

    private int physicFrameCount = 40;  // Number of frames for physics calculations
    private int fps = 60;  // Frames per second for rendering
    private double lastFrameTime = System.currentTimeMillis();  // Time of the last frame
    private boolean drawMode = true;  // Flag to determine whether to draw physics or rendered objects
    private Telemetry telemetry = new Telemetry();  // Telemetry for tracking time and performance
    private GameEngine ge;  // Reference to the game engine

    /**
     * Constructor for SpacePanel.
     *
     * @param ge The GameEngine associated with this SpacePanel.
     */
    public SpacePanel(GameEngine ge) {
        this.ge = ge;
    }

    /**
     * Overrides the setSize method to provide additional functionality when the panel size changes.
     *
     * @param width  The new width of the panel.
     * @param height The new height of the panel.
     */
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
    }

    /**
     * Overrides the paint method to handle custom rendering.
     *
     * @param g The Graphics context.
     */
    @Override
    public void paint(Graphics g) {
        double usedTime = System.currentTimeMillis();
        super.paint(g);

        // Choose between drawing physics or rendered objects based on drawMode
        if (drawMode) {
            draw(g);
        } else {
            drawPhysics(g);
        }

        // Calculate and display frame time information
        telemetry.addTimeInfo("frame - drawing", System.currentTimeMillis() - usedTime);

        try {
            // Introduce a delay to achieve the desired frames per second
            Thread.sleep((int) Math.max(0, 1000 / fps - (System.currentTimeMillis() - usedTime)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Record the time spent on drawing and sleeping
        telemetry.addTimeInfo("frame - drawing and sleep", System.currentTimeMillis() - usedTime);
        repaint();  // Request a repaint to continue the animation loop
    }

    /**
     * Custom method to draw rendered objects.
     *
     * @param g The Graphics context.
     */
    public void draw(Graphics g) {
        double lastTime = System.currentTimeMillis();

        // Draw each mesh in the game engine
        for (Mesh m : ge.getMeshs()) {
            m.Draw(g, ge.getScale(), ge.getCenter());
        }

        // Calculate and display drawing time information
        telemetry.addTimeInfo("drawing", System.currentTimeMillis() - lastTime);
    }

    /**
     * Custom method to draw physics-related objects (physical objects, connections, and collision detectors).
     *
     * @param g The Graphics context.
     */
    public void drawPhysics(Graphics g) {
        double lastTime = System.currentTimeMillis();

        // Draw each physical object in the game engine
        for (PhysicalObjects po : ge.getPhysicalObjects()) {
            po.Draw(g, ge.getScale(), ge.getCenter());
        }

        // Draw each connection in the game engine
        for (Connection co : ge.getConnections()) {
            co.Draw(g, ge.getScale(), ge.getCenter());
        }

        // Draw each collision detector in the game engine
        for (CollisionDetector cd : ge.getCollisionDetectors()) {
            cd.Draw(g, ge.getScale(), ge.getCenter());
        }

        // Calculate and display drawing time information
        telemetry.addTimeInfo("drawing", System.currentTimeMillis() - lastTime);
    }

    /**
     * Overrides the setBackground method to provide additional functionality when setting the background color.
     *
     * @param bg The Color to set as the background.
     */
    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
    }

    /**
     * Sets the telemetry for tracking time and performance.
     *
     * @param telemetry The Telemetry object to set.
     */
    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    /**
     * Gets the current telemetry object.
     *
     * @return The Telemetry object.
     */
    public Telemetry getTelemetry() {
        return telemetry;
    }

    /**
     * Sets the draw mode to determine whether to draw physics or rendered objects.
     *
     * @param drawMode The draw mode flag.
     */
    public void setDrawMode(boolean drawMode) {
        this.drawMode = drawMode;
    }
}
