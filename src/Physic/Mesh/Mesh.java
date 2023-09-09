package Physic.Mesh;

import Physic.Objects.Point;
import Physic.Vec2d;

import java.awt.*;

import static Physic.MathFunctions.rescale;

public class Mesh {
    protected Point[] points;
    protected Color color;

    public Mesh(Point[] points, Color color){
        this.points = points;
        this.color = color;
    }

    public void Draw(Graphics g, double scale, Vec2d center){
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];

        for(int i = 0; i< points.length; i++){
            Vec2d np = rescale(points[i].getPosition(), scale, center);
            xPoints[i] = (int)np.getX();
            yPoints[i] = (int)np.getY();
        }
        g.setColor(color);
        g.fillPolygon(xPoints, yPoints, points.length);
        g.setColor(Color.black);
        g.drawPolygon(xPoints, yPoints, points.length);
    }
}
