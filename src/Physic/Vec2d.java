package Physic;

/**
 * A 2D vector class with basic vector operations.
 */
public class Vec2d {

    // Coordinates of the vector
    private double x;
    private double y;

    /**
     * Constructs a Vec2d object with specified x and y coordinates.
     *
     * @param x The x-coordinate of the vector.
     * @param y The y-coordinate of the vector.
     */
    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Default constructor for Vec2d, initializes vector with (0, 0).
     */
    public Vec2d() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Adds another vector to this vector.
     *
     * @param v The vector to be added.
     * @return A new Vec2d representing the sum of the two vectors.
     */
    public Vec2d add(Vec2d v) {
        return new Vec2d(x + v.getX(), y + v.getY());
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param v The vector to be subtracted.
     * @return A new Vec2d representing the difference between the two vectors.
     */
    public Vec2d sub(Vec2d v) {
        return new Vec2d(x - v.getX(), y - v.getY());
    }

    /**
     * Gets the x-coordinate of the vector.
     *
     * @return The x-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the vector.
     *
     * @return The y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the coordinates of the vector.
     *
     * @param x The new x-coordinate.
     * @param y The new y-coordinate.
     */
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Computes the dot product with another vector.
     *
     * @param v The vector for the dot product.
     * @return The dot product of the two vectors.
     */
    public double dot(Vec2d v) {
        return x * v.getX() + y * v.getY();
    }

    /**
     * Multiplies the vector by a scalar.
     *
     * @param v The scalar for multiplication.
     * @return A new Vec2d representing the scaled vector.
     */
    public Vec2d mult(double v) {
        return new Vec2d(x * v, y * v);
    }

    /**
     * Divides the vector by a scalar.
     *
     * @param v The scalar for division.
     * @return A new Vec2d representing the scaled vector.
     */
    public Vec2d div(double v) {
        return new Vec2d(x / v, y / v);
    }

    /**
     * Computes the distance between this vector and another vector.
     *
     * @param v The other vector.
     * @return The Euclidean distance between the two vectors.
     */
    public double distance(Vec2d v) {
        return Math.sqrt(Math.pow(x - v.getX(), 2) + Math.pow(y - v.getY(), 2));
    }

    /**
     * Checks if this vector is equal to another vector.
     *
     * @param v The other vector.
     * @return true if the vectors are equal, false otherwise.
     */
    public boolean equals(Vec2d v) {
        return (v.getX() == x) && (v.getY() == y);
    }

    /**
     * Computes the length (magnitude) of the vector.
     *
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Returns a string representation of the vector.
     *
     * @return A string in the format "(x, y)" representing the vector.
     */
    @Override
    public String toString() {
        return String.format("( %f, %f )", x, y);
    }
}
