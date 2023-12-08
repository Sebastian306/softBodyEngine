package Physic.CollisionDetectors;

import Physic.Field;
import Physic.Objects.Point;
import Physic.Vec2d;

import java.awt.*;

public interface CollisionDetector {

    /**
     * Checks if a collision occurs with the specified point.
     *
     * @param po The point to check for collision.
     * @return True if a collision occurs with the given point, false otherwise.
     */
    boolean checkCollision(Point po);

    /**
     * Draws the object represented by this class using the provided Graphics object,
     * scaling the drawing with a specified scale factor and considering a center point.
     *
     * @param g      The Graphics object used for drawing.
     * @param scale  The scaling factor applied to the drawing.
     * @param center The central point around which the drawing is centered.
     */
    void Draw(Graphics g, double scale, Vec2d center);

    /**
     * Retrieves the Field object associated with this instance.
     *
     * @return The Field object associated with this instance.
     */
    Field getField();
}
