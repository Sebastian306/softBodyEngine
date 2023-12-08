package Physic.Connections;

import Physic.Objects.PhysicalObjects;
import Physic.Canvas.SpacePanel;
import Physic.Vec2d;

import java.awt.*;

/**
 * Abstract class representing a connection between two physical objects in a space.
 */
public abstract class Connection {

    // Array to store the two connected physical objects
    protected PhysicalObjects objects[] = new PhysicalObjects[2];

    // Reference to the SpacePanel associated with this connection
    protected SpacePanel space;

    /**
     * Gets the SpacePanel associated with this connection.
     *
     * @return The associated SpacePanel.
     */
    public SpacePanel getSpace() {
        return space;
    }

    /**
     * Sets the SpacePanel associated with this connection.
     *
     * @param space The SpacePanel to be associated with this connection.
     */
    public void setSpace(SpacePanel space) {
        this.space = space;
    }

    /**
     * Gets the connected physical objects.
     *
     * @return An array containing the two connected physical objects.
     */
    public PhysicalObjects[] getObjects() {
        return objects;
    }

    /**
     * Abstract method for drawing the connection.
     *
     * @param g     The Graphics object used for drawing.
     * @param scale The scaling factor applied to the drawing.
     * @param center The central point around which the drawing is centered.
     */
    public abstract void Draw(Graphics g, double scale, Vec2d center);
}
