package Physic.Objects;

import Physic.Vec2d;

/**
 * An interface representing a point in two-dimensional space.
 */
public interface Point {

    /**
     * Gets the position of the point as a Vec2d object.
     *
     * @return The position of the point.
     */
    Vec2d getPosition();

    /**
     * Sets the position of the point.
     *
     * @param v The new position as a Vec2d object.
     */
    void setPosition(Vec2d v);

    /**
     * Gets the velocity of the point as a Vec2d object.
     *
     * @return The velocity of the point.
     */
    Vec2d getVelocity();

    /**
     * Sets the velocity of the point.
     *
     * @param v The new velocity as a Vec2d object.
     */
    void setVelocity(Vec2d v);

    /**
     * Gets the x-coordinate of the point.
     *
     * @return The x-coordinate of the point.
     */
    double getX();

    /**
     * Gets the y-coordinate of the point.
     *
     * @return The y-coordinate of the point.
     */
    double getY();
}
