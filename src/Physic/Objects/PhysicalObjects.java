package Physic.Objects;

import Physic.Forces.ForceAbstract;
import Physic.Canvas.SpacePanel;
import Physic.Forces.SpringForce;
import Physic.GameEngine;
import Physic.Telemetry.Telemetry;
import Physic.Vec2d;

import java.awt.*;
import java.util.HashSet;

/**
 * An abstract class representing physical objects in a two-dimensional space.
 */
public abstract class PhysicalObjects {

    // Array to store the current and previous positions of the object
    protected Vec2d position[] = new Vec2d[2];

    // Mass of the object
    protected double mass;

    // Set of forces acting on the object
    protected HashSet<ForceAbstract> forces = new HashSet<>();

    // Acceleration vector
    protected Vec2d acceleration;

    // Reference to the game engine
    protected GameEngine gameEngine;

    /**
     * Gets the current position of the object.
     *
     * @return The current position as a Vec2d object.
     */
    public Vec2d getPosition() {
        return position[0];
    }

    /**
     * Gets the current and previous positions of the object.
     *
     * @return An array containing the current and previous positions.
     */
    public Vec2d[] getPositions() {
        return position;
    }

    /**
     * Gets the mass of the object.
     *
     * @return The mass of the object.
     */
    public double getMass() {
        return mass;
    }

    /**
     * Gets the velocity of the object.
     *
     * @return The velocity of the object as a Vec2d object.
     */
    public Vec2d getVelocity() {
        return position[0].sub(position[1]).div(gameEngine.getTimeRate());
    }

    /**
     * Sets the velocity of the object.
     *
     * @param velocity The new velocity as a Vec2d object.
     */
    public void setVelocity(Vec2d velocity) {
        position[1] = position[0].sub(velocity.mult(gameEngine.getTimeRate()));
    }

    /**
     * Sets the mass of the object.
     *
     * @param mass The new mass of the object.
     */
    public void setMass(double mass) {
        this.mass = mass;
    }

    /**
     * Sets the position of the object, keeping its speed.
     *
     * @param position The new position as a Vec2d object.
     */
    public void setPositionKeepingSpeed(Vec2d position) {
        Vec2d delta = position.sub(this.position[0]);
        this.position[0].add(delta);
        this.position[1].add(delta);
    }

    /**
     * Sets the position of the object.
     *
     * @param position The new position as a Vec2d object.
     */
    public void setPosition(Vec2d position) {
        this.position[0] = position;
    }

    /**
     * Adds a force to the set of forces acting on the object.
     *
     * @param f The force to be added.
     */
    public void addForce(ForceAbstract f) {
        forces.add(f);
    }

    /**
     * Removes a force from the set of forces acting on the object.
     *
     * @param f The force to be removed.
     */
    public void removeForce(ForceAbstract f) {
        forces.remove(f);
    }

    /**
     * Sets the game engine for the object.
     *
     * @param gameEngine The game engine to be set.
     */
    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * Gets the game engine associated with the object.
     *
     * @return The game engine associated with the object.
     */
    public GameEngine getGameEngine() {
        return gameEngine;
    }

    /**
     * Gets the total acceleration vector acting on the object due to all forces.
     *
     * @return The total acceleration as a Vec2d object.
     */
    public Vec2d getAcceleration() {
        Vec2d acc = new Vec2d(0, 0);
        for (ForceAbstract f : forces) {
            double lastTime = System.nanoTime();
            acc = acc.add(f.getForce(this).div(mass));
        }
        return acc;
    }

    /**
     * Updates the acceleration of the object.
     */
    public void accelerationUpdate() {
        this.acceleration = getAcceleration();
    }

    /**
     * Updates the position of the object based on the calculated acceleration.
     */
    public void positionUpdate() {
        double t = gameEngine.getTimeRate();
        Vec2d pos = position[0].add(position[0].sub(position[1]).mult(gameEngine.getTimeRateDiff())).add(acceleration.mult(t * t));
        position[1] = position[0];
        position[0] = pos;
    }

    /**
     * Abstract method for drawing the object.
     *
     * @param g     The Graphics object used for drawing.
     * @param scale The scaling factor applied to the drawing.
     * @param center The central point around which the drawing is centered.
     */
    public abstract void Draw(Graphics g, double scale, Vec2d center);
}
