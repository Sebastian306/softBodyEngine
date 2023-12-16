package Physic;

import java.awt.*;

/**
 * A utility class for transitioning between colors based on a linear scale.
 */
public class ColorLinearTransition {

    // Array of colors representing the transition
    private Color[] colors;

    // Minimum value of the scale
    private double minVal;

    // Maximum value of the scale
    private double maxVal;

    /**
     * Constructor to initialize ColorLinearTransition with colors, minVal, and maxVal.
     *
     * @param colors Array of colors representing the transition.
     * @param minVal Minimum value of the scale.
     * @param maxVal Maximum value of the scale.
     * @throws RuntimeException if minVal is greater than maxVal.
     */
    public ColorLinearTransition(Color[] colors, double minVal, double maxVal) {
        this.colors = colors;
        this.minVal = minVal;
        this.maxVal = maxVal;

        if (minVal > maxVal) {
            throw new RuntimeException("Minimal value cannot be greater than maximum value");
        }
    }

    /**
     * Gets the color corresponding to the given value on the scale.
     *
     * @param val The value on the scale.
     * @return The color corresponding to the value.
     */
    public Color getColor(double val) {
        // If the value is below the minimum, return the first color
        if (val <= minVal)
            return colors[0];
        // If the value is above the maximum, return the last color
        if (val >= maxVal)
            return colors[colors.length - 1];

        // Calculate the scale based on the given value
        double scale = (val - minVal) / (maxVal - minVal) * (colors.length - 1);
        double c1 = Math.floor(scale);
        double c2 = Math.ceil(scale);

        // If c1 and c2 are the same, return the corresponding color
        if (c1 == c2) {
            return colors[(int) c1];
        }

        // Interpolate between the two colors based on the fractional part of the scale
        double r = (1 - (scale - c1)) * colors[(int) c1].getRed() + (1 - (c2 - scale)) * colors[(int) c2].getRed();
        double g = (1 - (scale - c1)) * colors[(int) c1].getGreen() + (1 - (c2 - scale)) * colors[(int) c2].getGreen();
        double b = (1 - (scale - c1)) * colors[(int) c1].getBlue() + (1 - (c2 - scale)) * colors[(int) c2].getBlue();

        // Create a new Color object with the interpolated RGB values
        return new Color((int) r, (int) g, (int) b);
    }
}
