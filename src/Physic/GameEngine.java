package Physic;

import Physic.CollisionDetectors.*;
import Physic.Connections.*;
import Physic.Forces.GravityForce;
import Physic.Mesh.Mesh;
import Physic.Objects.*;
import Physic.Objects.Point;
import Physic.Telemetry.Telemetry;

import java.awt.*;
import java.util.ArrayList;

/**
 * The main game engine for the physics simulation.
 */
public class GameEngine implements Runnable {
    // Lists to store physical objects, connections, collision detectors, etc.
    private ArrayList<PhysicalObjects> physicalObjects = new ArrayList<>();
    private ArrayList<Connection> connections = new ArrayList<>();
    private ArrayList<CollisionDetector> collisionDetectors = new ArrayList<>();
    private ArrayList<Physic.Objects.Point> collisionPoints = new ArrayList<>();
    private ArrayList<Mesh> meshs = new ArrayList<>();

    // Gravity force acting on the objects
    private GravityForce gravity = new GravityForce(new Vec2d(0, 0));

    // Simulation pgit branch -m main masterarameters
    private double scale = 1.5;
    private Vec2d center = new Vec2d(0, 0);
    private ChunkBox chbox = new ChunkBox(-100, -100, 600, 600, 70, 70);
    private double maxTimeRate = 0.01;
    private double minTimeRate = 0.000000001;
    private double timeRate = minTimeRate;
    private double timeRateDiff = 1;
    private double timeSpeed = 0.01;
    private Telemetry telemetry;
    private boolean running = true;
    private double currentFrame = -1;

    /**
     * Main simulation loop.
     */
    public void run() {
        while (running) {
            double estimatedTime = (System.nanoTime() / 1000000. - currentFrame);

            currentFrame = (double) System.nanoTime() / 1000000.;

            double oldTimeRate = timeRate;
            timeRate = Math.min(maxTimeRate, Math.max(minTimeRate, estimatedTime * timeSpeed));
            timeRateDiff = timeRate / oldTimeRate;
            telemetry.addTimeInfo("timeRate", timeRate);

            calculateFrame();
        }
    }

    /**
     * Set the running state of the game engine.
     *
     * @param running The running state.
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Set the telemetry system for collecting performance data.
     *
     * @param telemetry The telemetry system.
     */
    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    /**
     * Get the time rate difference.
     *
     * @return The time rate difference.
     */
    public double getTimeRateDiff() {
        return timeRateDiff;
    }

    /**
     * Perform calculations for each frame of the simulation.
     */
    public void calculateFrame() {
        double fullTime = System.nanoTime();
        double lastTime = System.nanoTime();
        double loopTime = lastTime;

        // Update accelerations for physical objects
        for (PhysicalObjects po : physicalObjects) {
            po.accelerationUpdate();
        }
        telemetry.addTimeInfo("accelerationCalculations", (System.nanoTime() - lastTime) / 1000000);
        lastTime = System.nanoTime();

        // Update positions for physical objects
        for (PhysicalObjects po : physicalObjects) {
            po.positionUpdate();
        }
        telemetry.addTimeInfo("physicsCalculations", (System.nanoTime() - lastTime) / 1000000);
        lastTime = System.nanoTime();

        // Update the chunk box
        for (Point p : collisionPoints) {
            chbox.setObject(p);
        }

        telemetry.addTimeInfo("chunk box update", (System.nanoTime() - lastTime) / 1000000);

        lastTime = System.nanoTime();

        // Perform collision detection
        boolean collisionDetected = true;
        int count = 0;
        while (collisionDetected && count < 10) {
            count++;
            collisionDetected = false;
            for (CollisionDetector cd : collisionDetectors) {
                int indCount = 0;
                for (Physic.Objects.Point cp : chbox.getCollisionCandidates(cd.getField())) {
                    indCount++;
                    if (cd.checkCollision(cp)) {
                        collisionDetected = true;
                    }
                    ;
                }
                //telemetry.addTimeInfo("collision candidates", indCount);
            }
        }

        telemetry.addTimeInfo("collisionCalculations", (System.nanoTime() - lastTime) / 1000000);
        telemetry.addTimeInfo("frame - game", (System.nanoTime() - fullTime) / 1000000);
    }

    /**
     * Reset the game by clearing lists of objects and connections.
     */
    public void resetGame() {
        physicalObjects = new ArrayList<>();
        connections = new ArrayList<>();
        collisionDetectors = new ArrayList<>();
        collisionPoints = new ArrayList<>();
        meshs = new ArrayList<>();
    }

    /**
     * Get the current time rate.
     *
     * @return The current time rate.
     */
    public double getTimeRate() {
        return timeRate;
    }

    /**
     * Set the gravity force acting on the objects.
     *
     * @param gravity The gravity force.
     */
    public void setGravity(GravityForce gravity) {
        this.gravity.setConst(gravity.getConst());
    }

    /**
     * Set the center of the simulation.
     *
     * @param x X-coordinate of the center.
     * @param y Y-coordinate of the center.
     */
    public void setCenter(double x, double y) {
        center = new Vec2d(x, y);
    }

    /**
     * Set the scale of the simulation.
     *
     * @param scale The scale factor.
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    /**
     * Get the list of physical objects in the simulation.
     *
     * @return List of physical objects.
     */
    public ArrayList<PhysicalObjects> getPhysicalObjects() {
        return physicalObjects;
    }

    /**
     * Get the list of connections in the simulation.
     *
     * @return List of connections.
     */
    public ArrayList<Connection> getConnections() {
        return connections;
    }

    /**
     * Get the list of collision detectors in the simulation.
     *
     * @return List of collision detectors.
     */
    public ArrayList<CollisionDetector> getCollisionDetectors() {
        return collisionDetectors;
    }

    /**
     * Get the list of collision points in the simulation.
     *
     * @return List of collision points.
     */
    public ArrayList<Point> getCollisionPoints() {
        return collisionPoints;
    }

    /**
     * Get the list of meshes in the simulation.
     *
     * @return List of meshes.
     */
    public ArrayList<Mesh> getMeshs() {
        return meshs;
    }

    /**
     * Get the current scale of the simulation.
     *
     * @return The current scale.
     */
    public double getScale() {
        return scale;
    }

    /**
     * Get the center of the simulation.
     *
     * @return The center coordinates.
     */
    public Vec2d getCenter() {
        return center;
    }

    /**
     * Get the chunk box used for collision detection.
     *
     * @return The chunk box.
     */
    public ChunkBox getChbox() {
        return chbox;
    }


    /**
     * Creates a pivot object with specified coordinates and mass, adds it to the physical objects list,
     * attaches gravity force, and includes it in collision detection.
     *
     * @param x    X-coordinate of the pivot.
     * @param y    Y-coordinate of the pivot.
     * @param mass Mass of the pivot.
     * @return The created pivot.
     */
    public Pivot createPivot(double x, double y, double mass) {
        Pivot newPivot = new Pivot(x, y, mass);
        newPivot.setGameEngine(this);
        newPivot.addForce(gravity);
        physicalObjects.add(newPivot);
        collisionPoints.add(newPivot);
        return newPivot;
    }

    /**
     * Creates a fixed pivot object with specified coordinates, adds it to the physical objects list,
     * attaches gravity force, and includes it in collision detection.
     *
     * @param x X-coordinate of the fixed pivot.
     * @param y Y-coordinate of the fixed pivot.
     * @return The created fixed pivot.
     */
    public FixedPivot createFixedPivot(double x, double y) {
        FixedPivot newPivot = new FixedPivot(x, y);
        newPivot.setGameEngine(this);
        newPivot.addForce(gravity);
        physicalObjects.add(newPivot);
        collisionPoints.add(newPivot);
        return newPivot;
    }

    /**
     * Creates a fixed pivot object with specified coordinates, adds it to the physical objects list,
     * and attaches gravity force. Excludes it from collision detection.
     *
     * @param x X-coordinate of the fixed pivot.
     * @param y Y-coordinate of the fixed pivot.
     * @return The created fixed pivot without collision detection.
     */
    public FixedPivot createNoCollisionFixedPivot(double x, double y) {
        FixedPivot newPivot = new FixedPivot(x, y);
        newPivot.setGameEngine(this);
        newPivot.addForce(gravity);
        physicalObjects.add(newPivot);
        return newPivot;
    }

    /**
     * Creates a spring connection between two physical objects with specified size and spring constant,
     * and adds it to the connections list.
     *
     * @param p1   First physical object.
     * @param p2   Second physical object.
     * @param size Rest length of the spring.
     * @param k    Spring constant.
     */
    public void createSpring(PhysicalObjects p1, PhysicalObjects p2, double size, double k) {
        SpringConnection s = new SpringConnection(p1, p2, size, k);
        connections.add(s);
    }

    /**
     * Creates a spring connection between two physical objects with specified size, spring constant, and damping,
     * and adds it to the connections list.
     *
     * @param p1   First physical object.
     * @param p2   Second physical object.
     * @param size Rest length of the spring.
     * @param k    Spring constant.
     * @param b    Damping factor.
     */
    public void createSpring(PhysicalObjects p1, PhysicalObjects p2, double size, double k, double b) {
        SpringConnection s = new SpringConnection(p1, p2, size, k, b, 2, 100);
        connections.add(s);
    }

    /**
     * Creates a spring connection between two physical objects with specified size, spring constant, damping,
     * critical size, and critical spring constant, and adds it to the connections list.
     *
     * @param p1       First physical object.
     * @param p2       Second physical object.
     * @param size     Rest length of the spring.
     * @param k        Spring constant.
     * @param b        Damping factor.
     * @param critSize Critical size for the spring.
     * @param critK    Critical spring constant.
     */
    public void createSpring(PhysicalObjects p1, PhysicalObjects p2, double size,
                             double k, double b, double critSize, double critK) {
        SpringConnection s = new SpringConnection(p1, p2, size, k, b, critSize, critK);
        connections.add(s);
    }

    /**
     * Creates a collision triangle between three physical objects and associated collision points,
     * and adds it to the collision detectors list.
     *
     * @param poA First physical object.
     * @param poB Second physical object.
     * @param poC Third physical object.
     * @param of  Associated collision points.
     */
    public void createCollisionTriangle(PhysicalObjects poA, PhysicalObjects poB,
                                        PhysicalObjects poC, Physic.Objects.Point[] of) {
        CollisionDetector nc = new CollisionTriangle(poA, poB, poC, of);
        collisionDetectors.add(nc);
    }

    /**
     * Creates a collision triangle box between three specified points and a factor, and adds it to the collision detectors list.
     *
     * @param Ax X-coordinate of the first point.
     * @param Ay Y-coordinate of the first point.
     * @param Bx X-coordinate of the second point.
     * @param By Y-coordinate of the second point.
     * @param Cx X-coordinate of the third point.
     * @param Cy Y-coordinate of the third point.
     * @param f  Factor for the collision triangle box.
     */
    public void createCollisionTriangleBox(double Ax, double Ay, double Bx, double By,
                                           double Cx, double Cy, double f) {
        CollisionDetector nc = new CollisionTriangleBox(new Vec2d(Ax, Ay), new Vec2d(Bx, By), new Vec2d(Cx, Cy), f);
        collisionDetectors.add(nc);
    }

    /**
     * Creates a mesh with specified points and color, and adds it to the meshes list.
     *
     * @param points Array of points forming the mesh.
     * @param color  Color of the mesh.
     */
    public void createMesh(Physic.Objects.Point points[], Color color) {
        Mesh m = new Mesh(points, color);
        meshs.add(m);
    }


    /**
     * Create a quadrangle and associated collision triangles.
     *
     * @param x1    X-coordinate of the first point.
     * @param y1    Y-coordinate of the first point.
     * @param x2    X-coordinate of the second point.
     * @param y2    Y-coordinate of the second point.
     * @param x3    X-coordinate of the third point.
     * @param y3    Y-coordinate of the third point.
     * @param x4    X-coordinate of the fourth point.
     * @param y4    Y-coordinate of the fourth point.
     * @param color Color of the quadrangle.
     * @param f     Factor for the collision triangles.
     */
    public void createQuadrangle(double x1, double y1, double x2, double y2,
                                 double x3, double y3, double x4, double y4,
                                 Color color, double f) {
        // Create collision triangles
        createCollisionTriangleBox(x1, y1, x2, y2, x3, y3, f);
        createCollisionTriangleBox(x2, y2, x3, y3, x4, y4, f);
        createCollisionTriangleBox(x3, y3, x4, y4, x1, y1, f);
        createCollisionTriangleBox(x4, y4, x1, y1, x2, y2, f);

        // Create mesh for the quadrangle
        createMesh(new Physic.Objects.Point[]{
                new FixedPoints(x1, y1), new FixedPoints(x2, y2),
                new FixedPoints(x3, y3), new FixedPoints(x4, y4)
        }, color);
    }


    /**
     * Create a square blob of interconnected pivots.
     *
     * @param x     X-coordinate of the blob center.
     * @param y     Y-coordinate of the blob center.
     * @param size  Size of the blob.
     * @param k     Spring constant.
     * @param b     Damping factor.
     * @param mass  Mass of individual pivots.
     * @param color Color of the mesh.
     * @return Array of created pivots.
     */
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

        createCollisionTriangle(p[1],p[2],p[4], p);
        createCollisionTriangle(p[3],p[4],p[6], p);
        createCollisionTriangle(p[5],p[6],p[0], p);
        createCollisionTriangle(p[7],p[0],p[2], p);

        createCollisionTriangle(p[0],p[2],p[6], p);
        createCollisionTriangle(p[2],p[6],p[4], p);
        createCollisionTriangle(p[6],p[4],p[0], p);
        createCollisionTriangle(p[4],p[0],p[2], p);

        return new Pivot[]{p[0],p[2],p[6],p[4]};
    }

    /**
     * Create a round blob of interconnected pivots.
     *
     * @param x       X-coordinate of the blob center.
     * @param y       Y-coordinate of the blob center.
     * @param radious Radius of the blob.
     * @param kb      Spring constant for border springs.
     * @param kc      Spring constant for diagonal springs.
     * @param b       Damping factor.
     * @param mass    Mass of individual pivots.
     * @param color   Color of the mesh.
     * @return Array of created pivots.
     */
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
