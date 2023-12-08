package Physic.CollisionDetectors;

import Physic.Field;
import Physic.Objects.Point;
import Physic.Objects.PhysicalObjects;
import Physic.Vec2d;

import java.awt.*;

import static Physic.MathFunctions.*;

/**
 * A collision detector for a triangle defined by three physical objects.
 */
public class CollisionTriangle implements CollisionDetector {

    // The three physical objects defining the triangle
    private final PhysicalObjects[] objects = new PhysicalObjects[3];

    // Points that should be considered out of the triangle
    private Point outOf[];

    /**
     * Constructs a CollisionTriangle with three physical objects and specified points that should be considered out of the triangle.
     *
     * @param poA   The first physical object defining the triangle.
     * @param poB   The second physical object defining the triangle.
     * @param poC   The third physical object defining the triangle.
     * @param outOf Points that should be considered out of the triangle.
     */
    public CollisionTriangle(PhysicalObjects poA, PhysicalObjects poB, PhysicalObjects poC, Point[] outOf) {
        objects[0] = poA;
        objects[1] = poB;
        objects[2] = poC;
        this.outOf = outOf;
    }

    /**
     * Gets the bounding field of the triangle.
     *
     * @return The bounding field of the triangle.
     */
    @Override
    public Field getField() {
        Vec2d A = objects[0].getPosition();
        Vec2d B = objects[1].getPosition();
        Vec2d C = objects[2].getPosition();
        double minX = Math.min(Math.min(A.getX(), B.getX()), C.getX());
        double maxX = Math.max(Math.max(A.getX(), B.getX()), C.getX());
        double minY = Math.min(Math.min(A.getY(), B.getY()), C.getY());
        double maxY = Math.max(Math.max(A.getY(), B.getY()), C.getY());

        return new Field(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Checks for collision with a point and updates positions accordingly.
     *
     * @param po The point to check for collision.
     * @return True if collision occurred, false otherwise.
     */
    @Override
    public boolean checkCollision(Point po) {
        for (Point of : outOf)
            if (of.equals(po))
                return false;
        Vec2d A = objects[0].getPosition();
        Vec2d B = objects[1].getPosition();
        Vec2d C = objects[2].getPosition();
        Vec2d P = po.getPosition();
        Vec2d An, Bn, Cn;

        // Obtain collision data using the TriangleCollisionData method
        CollisionData cd = TriangleColisionData(A, B, C, P);

        if (!cd.colisionOccured)
            return false;

        double bodyProp = 1.01;
        double pointProp = 0;

        // Adjust proportions based on mass if the point is a PhysicalObject
        if (po instanceof PhysicalObjects) {
            double pm = ((PhysicalObjects) po).getMass();
            double tm = objects[0].getMass() + objects[1].getMass() + objects[2].getMass();
            bodyProp = pm / (tm + pm) * 1.01;
            pointProp = tm / (tm + pm) * 1.01;
        }

        // Update positions based on collision data
        if (Double.isNaN(cd.c1))
            return false;
        An = A.add(cd.delta.mult(cd.c1 * cd.l * bodyProp));
        Bn = B.add(cd.delta.mult(cd.c2 * cd.l * bodyProp));
        Cn = C.add(cd.delta.mult(cd.c3 * cd.l * bodyProp));
        po.setPosition(P.sub(cd.delta.mult(pointProp)));

        objects[0].setPosition(An);
        objects[1].setPosition(Bn);
        objects[2].setPosition(Cn);

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
        Vec2d p1 = new Vec2d(objects[0].getPosition().getX(), objects[0].getPosition().getY());
        Vec2d p2 = new Vec2d(objects[1].getPosition().getX(), objects[1].getPosition().getY());
        Vec2d p3 = new Vec2d(objects[2].getPosition().getX(), objects[2].getPosition().getY());

        p1 = rescale(p1, scale, center);
        p2 = rescale(p2, scale, center);
        p3 = rescale(p3, scale, center);

        g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
        g.drawLine((int) p3.getX(), (int) p3.getY(), (int) p2.getX(), (int) p2.getY());
        g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p3.getX(), (int) p3.getY());
    }
}
