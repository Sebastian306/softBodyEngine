package Physic;

import java.util.Objects;

/**
 * A record representing a rectangular field with dimensions.
 */
public record Field(double x, double y, double w, double h) {

    /**
     * Constructs a new Field with specified coordinates and dimensions.
     *
     * @param x The X-coordinate of the field.
     * @param y The Y-coordinate of the field.
     * @param w The width of the field.
     * @param h The height of the field.
     */
    public Field(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        Objects.requireNonNull(w);
        Objects.requireNonNull(h);
    }

    /**
     * Returns a string representation of the Field.
     *
     * @return A string containing the X-coordinate, Y-coordinate, width, and height of the field.
     */
    public String toString() {
        return String.format("x: %f, y: %f, w: %f, h: %f", x, y, w, h);
    }
}
