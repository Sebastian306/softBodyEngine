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

public class SpacePanel extends JPanel {


    private int physicFrameCount = 40;
    private int fps = 60;
    private double lastFrameTime = System.currentTimeMillis();
    private boolean drawMode = true;
    private Telemetry telemetry = new Telemetry();
    private GameEngine ge;

    public SpacePanel(GameEngine ge){
        this.ge = ge;
    }
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
    }

    @Override
    public void paint(Graphics g){
        double usedTime = System.currentTimeMillis();
        super.paint(g);
        if(drawMode){
            Draw(g);
        }else{
            DrawPhysic(g);
        }

        telemetry.addTimeInfo("frame - drawing", System.currentTimeMillis() - usedTime);

        try{
            Thread.sleep((int)Math.max(0, 1000/fps - (System.currentTimeMillis() - usedTime)));
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        telemetry.addTimeInfo("frame", System.currentTimeMillis() - usedTime);
        repaint();
    }

    public void Draw(Graphics g){
        double lastTime = System.currentTimeMillis();

        for(Mesh m : ge.getMeshs()){
            m.Draw(g, ge.getScale(), ge.getCenter());
        }

        telemetry.addTimeInfo("drawing", System.currentTimeMillis() - lastTime);
    }

    public void DrawPhysic(Graphics g){
        double lastTime = System.currentTimeMillis();

        for(PhysicalObjects po : ge.getPhysicalObjects()){
            po.Draw(g, ge.getScale(), ge.getCenter());
        }
        for(Connection co : ge.getConnections()){
            co.Draw(g, ge.getScale(), ge.getCenter());
        }
        for (CollisionDetector cd : ge.getCollisionDetectors()) {
            cd.Draw(g, ge.getScale(), ge.getCenter());
        }

        telemetry.addTimeInfo("drawing", System.currentTimeMillis() - lastTime);
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
    }

    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }

    public void setDrawMode(boolean drawMode) {
        this.drawMode = drawMode;
    }


}
