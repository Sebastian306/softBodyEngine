package Physic.Canvas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Physic.ChunkBox;
import Physic.CollisionDetectors.*;
import Physic.Connections.Connection;
import Physic.Connections.SpringConnection;
import Physic.Forces.GravityForce;
import Physic.Mesh.Mesh;
import Physic.Objects.*;
import Physic.Objects.Point;
import Physic.Telemetry.Telemetry;
import Physic.Vec2d;

public class SpacePanel extends JPanel {

    private int width = 600;
    private int height = 600;
    private double timeRate = 0.01;
    private int physicFrameCount = 40;
    private int fps = 30;
    private double lastFrameTime = System.currentTimeMillis();
    private ArrayList<PhysicalObjects> physicalObjects = new ArrayList<PhysicalObjects>();
    private ArrayList<Connection> connections = new ArrayList<Connection>();
    private ArrayList<CollisionDetector> collisionDetectors = new ArrayList<CollisionDetector>();
    private ArrayList<Physic.Objects.Point> collisionPoints = new ArrayList<Physic.Objects.Point>();
    private ArrayList<Mesh> meshs = new ArrayList<Mesh>();
    private GravityForce gravity = new GravityForce(new Vec2d(0,0));
    private double scale = 1.5;
    private Vec2d center = new Vec2d(0,0);
    private boolean drawMode = true;
    private Telemetry telemetry = new Telemetry();
    private ChunkBox chbox = new ChunkBox(-100, -100, 1000, 1000, 50, 50);


    public SpacePanel(){
    }

    @Override
    public void paint(Graphics g){
        double usedTime = System.currentTimeMillis();
        for(int i = 0; i<physicFrameCount; i++){
            calculateFrame();
        }
        telemetry.addTimeInfo("frame - computing", System.currentTimeMillis() - usedTime);
        if(drawMode){
            Draw(g);
        }else{
            DrawPhysic(g);
        }

        telemetry.addTimeInfo("frame - drawing and computing", System.currentTimeMillis() - usedTime);
        try{
            Thread.sleep((int)Math.max(0, 1000/fps - (System.currentTimeMillis() - usedTime)));
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        telemetry.addTimeInfo("frame", System.currentTimeMillis() - usedTime);
        repaint();
    }

    public void calculateFrame(){
        double lastTime = System.currentTimeMillis();
        for(PhysicalObjects po : physicalObjects){
            po.accelerationUpdate();
        }
        telemetry.addTimeInfo("accelerationCalculations", System.currentTimeMillis() - lastTime);
        lastTime = System.currentTimeMillis();

        for(PhysicalObjects po : physicalObjects){
            po.positionUpdate(timeRate);

        }
        for(Point p : collisionPoints){
            chbox.setObject(p);
        }

        telemetry.addTimeInfo("physicsCalculations", System.currentTimeMillis() - lastTime);
        lastTime = System.currentTimeMillis();

        boolean collisionDetected = true;
        int count = 0;
        while(collisionDetected && count < 10) {
            count++;
            collisionDetected = false;
            for (CollisionDetector cd : collisionDetectors) {
                //System.out.println(cd.getField());
                int indCount = 0;
                for (Physic.Objects.Point cp : chbox.getCollisionCandidates(cd.getField())){
                    indCount ++;
                    if(cd.checkCollision(cp)){
                        collisionDetected = true;
                    };
                }
                telemetry.addTimeInfo("collision candidates", indCount);
            }
        }

        telemetry.addTimeInfo("collisionCalculations", System.currentTimeMillis() - lastTime);
    }

    public void Draw(Graphics g){
        double lastTime = System.currentTimeMillis();
        super.paint(g);
        for(Mesh m : meshs){
            m.Draw(g, scale, center);
        }

        telemetry.addTimeInfo("drawing", System.currentTimeMillis() - lastTime);
    }

    public void DrawPhysic(Graphics g){
        double lastTime = System.currentTimeMillis();
        super.paint(g);

        for(PhysicalObjects po : physicalObjects){
            po.Draw(g, scale, center);
        }
        for(Connection co : connections){
            co.Draw(g, scale, center);
        }
        for (CollisionDetector cd : collisionDetectors) {
            cd.Draw(g, scale, center);
        }

        telemetry.addTimeInfo("drawing", System.currentTimeMillis() - lastTime);
    }

    public void resetSpace(){
        telemetry = new Telemetry();
        physicalObjects = new ArrayList<PhysicalObjects>();
        connections = new ArrayList<Connection>();
        collisionDetectors = new ArrayList<CollisionDetector>();
        collisionPoints = new ArrayList<Physic.Objects.Point>();
        meshs = new ArrayList<Mesh>();
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
    }

    public double getTimeRate() {
        return timeRate;
    }

    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }

    public void setGravity(GravityForce gravity) {
        this.gravity.setConst(gravity.getConst());
    }

    public void setCenter(double x, double y){
        center = new Vec2d(x,y);
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setDrawMode(boolean drawMode) {
        this.drawMode = drawMode;
    }

    public Pivot createPivot(double x, double y, double mass){
        Pivot newPivot = new Pivot(x, y, mass);
        newPivot.setSpace(this);
        newPivot.addForce(gravity);
        physicalObjects.add(newPivot);
        collisionPoints.add(newPivot);
        return newPivot;
    }

    public FixedPivot createFixedPivot(double x, double y){
        FixedPivot newPivot = new FixedPivot(x, y);
        newPivot.setSpace(this);
        newPivot.addForce(gravity);
        physicalObjects.add(newPivot);
        collisionPoints.add(newPivot);
        return newPivot;
    }

    public FixedPivot createNoCollisionFixedPivot(double x, double y){
        FixedPivot newPivot = new FixedPivot(x, y);
        newPivot.setSpace(this);
        newPivot.addForce(gravity);
        physicalObjects.add(newPivot);
        return newPivot;
    }

    public void createSpring(PhysicalObjects p1, PhysicalObjects p2, double size, double k){
        SpringConnection s = new SpringConnection(p1,p2,size, k);
        connections.add(s);
    }

    public void createSpring(PhysicalObjects p1, PhysicalObjects p2, double size, double k, double b){
        SpringConnection s = new SpringConnection(p1, p2, size, k, b, 2, 100);
        connections.add(s);
    }

    public void createSpring(PhysicalObjects p1, PhysicalObjects p2, double size, double k, double b, double critSize, double critK){
        SpringConnection s = new SpringConnection(p1, p2, size, k, b, critSize, critK);
        connections.add(s);
    }

    public void createCollisionTriangle(PhysicalObjects poA, PhysicalObjects poB, PhysicalObjects poC, Physic.Objects.Point[] of){
        CollisionDetector nc = new CollisionTriangle(poA, poB, poC, of);
        collisionDetectors.add(nc);
    }

    public void createCollisionTriangleBox(double Ax, double Ay, double Bx, double By, double Cx, double Cy, double f){
        CollisionDetector nc = new CollisionTriangleBox(new Vec2d(Ax,Ay), new Vec2d(Bx,By), new Vec2d(Cx,Cy), f);
        collisionDetectors.add(nc);
    }

    public void createMesh(Physic.Objects.Point points[], Color color){
        Mesh m = new Mesh(points,color);
        meshs.add(m);
    }

    public void createQuadrangle(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, Color color, double f){
        createCollisionTriangleBox(x1, y1, x2, y2, x3, y3, f);
        createCollisionTriangleBox(x2, y2, x3, y3, x4, y4, f);
        createCollisionTriangleBox(x3, y3, x4, y4, x1, y1, f);
        createCollisionTriangleBox(x4, y4, x1, y1, x2, y2, f);
        createMesh(new Physic.Objects.Point[]{
                new FixedPoints(x1,y1), new FixedPoints(x2, y2), new FixedPoints(x3, y3), new FixedPoints(x4, y4)
        }, color);
    }

    public Pivot[] createSquareBlob(double x, double y, double size, double k, double b, double mass, Color color){
        mass /= 40;
        Pivot[] p = new Pivot[8];
        p[0] = createPivot(x,y,mass);
        p[1] = createPivot(x + size/2,y,mass);
        p[2] = createPivot(x + size,y,mass);
        p[3] = createPivot(x + size,y + size/2,mass);
        p[4] = createPivot(x,y + size,mass);
        p[5] = createPivot(x + size/2,y + size,mass);
        p[6] = createPivot(x + size,y + size,mass);
        p[7] = createPivot(x,y + size/2,mass);

        createMesh(new Point[]{p[0], p[1], p[2], p[3], p[6], p[5], p[4], p[7]}, color);

        createSpring(p[1],p[0],p[1].getPosition().distance(p[0].getPosition()),k, b, 2, 100);
        createSpring(p[2],p[1],p[2].getPosition().distance(p[1].getPosition()),k, b, 2, 100);
        createSpring(p[7],p[0],p[7].getPosition().distance(p[0].getPosition()),k, b, 2, 100);
        createSpring(p[4],p[7],p[4].getPosition().distance(p[7].getPosition()),k, b, 2, 100);
        createSpring(p[3],p[2],p[2].getPosition().distance(p[3].getPosition()),k, b, 2, 100);
        createSpring(p[6],p[3],p[3].getPosition().distance(p[6].getPosition()),k, b, 2, 100);
        createSpring(p[5],p[6],p[6].getPosition().distance(p[5].getPosition()),k, b, 2, 100);
        createSpring(p[4],p[5],p[5].getPosition().distance(p[4].getPosition()),k, b, 2, 100);

        createSpring(p[6],p[0],p[6].getPosition().distance(p[0].getPosition()),k + 0.6, b, 2, 100);
        createSpring(p[4],p[2],p[2].getPosition().distance(p[4].getPosition()),k + 0.6, b, 2, 100);

        createSpring(p[1],p[3],p[3].getPosition().distance(p[1].getPosition()),k + 0.2, b, 2, 100);
        createSpring(p[3],p[5],p[5].getPosition().distance(p[3].getPosition()),k + 0.2, b, 2, 100);
        createSpring(p[5],p[7],p[5].getPosition().distance(p[7].getPosition()),k + 0.2, b, 2, 100);
        createSpring(p[7],p[1],p[1].getPosition().distance(p[7].getPosition()),k + 0.2, b, 2, 100);

        createSpring(p[0],p[2],p[0].getPosition().distance(p[2].getPosition()),k + 0.2, b, 2, 100);
        createSpring(p[2],p[4],p[2].getPosition().distance(p[4].getPosition()),k + 0.2, b, 2, 100);
        createSpring(p[4],p[6],p[4].getPosition().distance(p[6].getPosition()),k + 0.2, b, 2, 100);
        createSpring(p[6],p[0],p[6].getPosition().distance(p[0].getPosition()),k + 0.2, b, 2, 100);

        createSpring(p[1],p[5],p[1].getPosition().distance(p[5].getPosition()),k + 0.2, b, 2, 100);
        createSpring(p[3],p[7],p[3].getPosition().distance(p[7].getPosition()),k + 0.2, b, 2, 100);

        createCollisionTriangle(p[0],p[2],p[6], p);
        createCollisionTriangle(p[2],p[6],p[4], p);
        createCollisionTriangle(p[6],p[4],p[0], p);
        createCollisionTriangle(p[4],p[0],p[2], p);

        //createCollisionConnection(p[0],p[2]);
        //createCollisionConnection(p[2],p[6]);
        //createCollisionConnection(p[6],p[4]);
        //createCollisionConnection(p[4],p[0]);

        return new Pivot[]{p[0],p[2],p[6],p[4]};
    }

    public Pivot[] createRoundBlob(double x, double y, double radious, double kb, double kc, double b, double mass, Color color){
        final int elemCount = 16;
        mass /= 2*elemCount;
        Pivot[] p = new Pivot[elemCount + 1];
        p[0] = createPivot(x,y,mass);

        Pivot[] border = new Pivot[elemCount];
        for(int i = 1; i<=elemCount; i++){
            p[i] = createPivot(
                    x + Math.cos((double)i/elemCount * 2 * Math.PI) * radious,
                    y + Math.sin((double)i/elemCount * 2 * Math.PI) * radious,mass);
            border[i-1] = p[i];
            createSpring(p[0], p[i], p[0].getPosition().distance(p[i].getPosition()), kc, b, 1., 10);
        }
        createMesh(border, color);

        for(int i = 0; i<elemCount; i++){
            int ind = (i+1)%elemCount;
            createSpring(border[i], border[ind], border[ind].getPosition().distance(border[i].getPosition()), kb/2., b, 1., 100.);

            ind = (i+4)%elemCount;
            createSpring(border[i], border[ind], border[ind].getPosition().distance(border[i].getPosition()), kc/2., b, 1., 100.);

            ind = (i+elemCount/2)%elemCount;
            createSpring(border[i], border[ind], border[ind].getPosition().distance(border[i].getPosition()), kc, b, 1., 100.);

            ind = (i+1)%elemCount;
            createCollisionTriangle(border[i], border[ind], p[0], p);

            ind = (i+2)%elemCount;
            createCollisionTriangle(border[i], border[ind], p[0], p);
        }

        return p;
    }
}
