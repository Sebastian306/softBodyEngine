package Physic.Objects;

import Physic.Forces.ForceAbstract;
import Physic.Vec2d;

import java.awt.*;

import static Physic.MathFunctions.rescale;

/**
 * A class representing a fixed pivot point in a physics simulation.
 * Extends PhysicalObjects and implements the Point interface.
 */
public class FixedPivot extends PhysicalObjects implements Point {

    // Radius of the fixed pivot point for drawing
    private int radius = 8;

    // Color of the fixed pivot point for drawing
    private Color color = Color.black;

    // Scaling factor for drawing force vectors
    private double forceDrawingScale = 5;

    /**
     * Constructor to initialize a fixed pivot with specified coordinates.
     *
     * @param x The x-coordinate of the fixed pivot.
     * @param y The y-coordinate of the fixed pivot.
     */
    public FixedPivot(double x, double y) {
        this.mass = Double.POSITIVE_INFINITY;
        this.position[0] = new Vec2d(x, y);
        this.position[1] = this.position[0];
    }

    /**
     * Draws the fixed pivot point on the graphics context.
     *
     * @param g      The Graphics object used for drawing.
     * @param scale  The scaling factor applied to the drawing.
     * @param center The central point around which the drawing is centered.
     */
    @Override
    public void Draw(Graphics g, double scale, Vec2d center) {
        g.setColor(Color.BLACK);

        Vec2d p = new Vec2d((position[0].getX()), (position[0].getY()));
        p = rescale(p, scale, center);

        g.fillOval((int) p.getX() - radius, (int) p.getY() - radius, 2 * radius, 2 * radius);

        DrawForces(g, scale, center);
    }

    /**
     * Draws force vectors acting on the fixed pivot point.
     *
     * @param g      The Graphics object used for drawing.
     * @param scale  The scaling factor applied to the drawing.
     * @param center The central point around which the drawing is centered.
     */
    public void DrawForces(Graphics g, double scale, Vec2d center) {
        g.setColor(new Color(89, 21, 138));

        for (ForceAbstract f : forces) {
            Vec2d p1 = new Vec2d((position[0].getX()), (position[0].getY()));
            Vec2d p2 = new Vec2d((position[0].add(f.getForce(this).mult(forceDrawingScale)).getX()),
                    (position[0].add(f.getForce(this).mult(forceDrawingScale)).getY()));
            p1 = rescale(p1, scale, center);
            p2 = rescale(p2, scale, center);
            g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
        }
    }

    /**
     * Overrides the setVelocity method to do nothing for a fixed pivot.
     *
     * @param velocity The velocity to set.
     */
    @Override
    public void setVelocity(Vec2d velocity) {
        // Do nothing for a fixed pivot
    }

    /**
     * Overrides the getAcceleration method to return zero for a fixed pivot.
     *
     * @return A zero vector representing the acceleration.
     */
    @Override
    public Vec2d getAcceleration() {
        return new Vec2d(0, 0);
    }

    /**
     * Overrides the positionUpdate method to do nothing for a fixed pivot.
     */
    @Override
    public void positionUpdate() {
        // Do nothing for a fixed pivot
    }

    /**
     * Gets the x-coordinate of the fixed pivot point.
     *
     * @return The x-coordinate of the fixed pivot point.
     */
    public double getX() {
        return getPosition().getX();
    }

    /**
     * Gets the y-coordinate of the fixed pivot point.
     *
     * @return The y-coordinate of the fixed pivot point.
     */
    public double getY() {
        return getPosition().getY();
    }
}
