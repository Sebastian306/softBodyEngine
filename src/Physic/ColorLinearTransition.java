package Physic;

import java.awt.*;

public class ColorLinearTransition {

    private Color[] colors;
    private double minVal;
    private double maxVal;

    public ColorLinearTransition(Color[] colors, double minVal, double maxVal){
        this.colors = colors;
        this.minVal = minVal;
        this.maxVal = maxVal;

        if(minVal > maxVal){
            throw new RuntimeException("minimal Value cannot be greater than maximum value");
        }

    }

    public Color getColor(double val){
        if(val <= minVal)
            return colors[0];
        if(val >= maxVal)
            return colors[colors.length - 1];
        double scale = (val - minVal) / (maxVal - minVal) * (colors.length - 1);
        double c1 = Math.floor(scale);
        double c2 = Math.ceil(scale);

        if(c1 == c2){
            return colors[(int)c1];
        }

        double r = (1 - (scale - c1)) * colors[(int)c1].getRed() + (1 - (c2 - scale)) * colors[(int)c2].getRed();
        double g = (1 - (scale - c1)) * colors[(int)c1].getGreen() + (1 - (c2 - scale)) * colors[(int)c2].getGreen();
        double b = (1 - (scale - c1)) * colors[(int)c1].getBlue() + (1 - (c2 - scale)) * colors[(int)c2].getBlue();

        return new Color((int)r, (int)g, (int)b);
    }

}
