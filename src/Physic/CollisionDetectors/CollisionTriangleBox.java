package Physic.CollisionDetectors;

import Physic.Field;
import Physic.Objects.Point;
import Physic.Vec2d;

import java.awt.*;

import static Physic.MathFunctions.TriangleColisionData;
import static Physic.MathFunctions.rescale;

/**
 * A collision detector for a triangle interacting with a box.
 */
public class CollisionTriangleBox implements CollisionDetector {

    // The three points defining the triangle
    private final Vec2d[] points = new Vec2d[3];

    // Friction coefficient for collision response
    private double friction;

    /**
     * Constructs a CollisionTriangleBox with three points defining the triangle and a specified friction coefficient.
     *
     * @param poA      The first point defining the triangle.
     * @param poB      The second point defining the triangle.
     * @param poC      The third point defining the triangle.
     * @param friction The friction coefficient for collision response.
     */
    public CollisionTriangleBox(Vec2d poA, Vec2d poB, Vec2d poC, double friction) {
        points[0] = poA;
        points[1] = poB;
        points[2] = poC;
        this.friction = friction;
        if (this.friction < 0)
            throw new RuntimeException("Friction cannot be negative");
        if (this.friction > 1)
            throw new RuntimeException("Friction cannot be greater than 1");
    }

    /**
     * Gets the bounding field of the triangle.
     *
     * @return The bounding field of the triangle.
     */
    @Override
    public Field getField() {
        Vec2d A = points[0];
        Vec2d B = points[1];
        Vec2d C = points[2];
        double minX = Math.min(Math.min(A.getX(), B.getX()), C.getX());
        double maxX = Math.max(Math.max(A.getX(), B.getX()), C.getX());
        double minY = Math.min(Math.min(A.getY(), B.getY()), C.getY());
        double maxY = Math.max(Math.max(A.getY(), B.getY()), C.getY());

        return new Field(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Checks for collision with a point and updates position and velocity accordingly.
     *
     * @param po The point to check for collision.
     * @return True if collision occurred, false otherwise.
     */
    @Override
    public boolean checkCollision(Point po) {
        Vec2d A = points[0];
        Vec2d B = points[1];
        Vec2d C = points[2];
        Vec2d P = po.getPosition();

        // Obtain collision data using the TriangleCollisionData method
        CollisionData cd = TriangleColisionData(A, B, C, P);

        if (!cd.colisionOccured)
            return false;

        // Update position and apply friction to velocity
        po.setPosition(P.sub(cd.delta));
        po.setVelocity(po.getVelocity().mult(this.friction));

        return true;
    }

    /**
     * Draws the visual representation of the triangle.
     *
     * @param g     The Graphics object used for drawing.
     * @param scale The scaling factor applied to the drawing.
     * @param center The central point around which the drawing is centered.
     */
    @Override
    public void Draw(Graphics g, double scale, Vec2d center) {
        g.setColor(Color.blue);
        Vec2d p1 = new Vec2d(points[0].getX(), points[0].getY());
        Vec2d p2 = new Vec2d(points[1].getX(), points[1].getY());
        Vec2d p3 = new Vec2d(points[2].getX(), points[2].getY());

        p1 = rescale(p1, scale, center);
        p2 = rescale(p2, scale, center);
        p3 = rescale(p3, scale, center);

        g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
        g.drawLine((int) p3.getX(), (int) p3.getY(), (int) p2.getX(), (int) p2.getY());
        g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p3.getX(), (int) p3.getY());
    }
}
