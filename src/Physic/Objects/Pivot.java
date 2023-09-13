package Physic.Objects;

import Physic.Forces.ForceAbstract;
import Physic.Vec2d;

import java.awt.*;

import static Physic.MathFunctions.rescale;

public class Pivot extends PhysicalObjects implements Point {

    private int radious = 6;
    private Color color = Color.black;
    private double forceDrawingScale = 5;

    public Pivot(double x, double y, double mass){
        if(mass <= 0){
            throw new RuntimeException("Mass cannot be negative or equal zero");
        }
        this.mass = mass;
        this.position[0] = new Vec2d(x, y);
        this.position[1] = this.position[0];
    }

    @Override
    public void Draw(Graphics g, double scale, Vec2d center){
        g.setColor(Color.BLACK);

        Vec2d p = new Vec2d((position[0].getX()),(position[0].getY()));
        p = rescale(p, scale, center);

        g.fillOval( (int)p.getX() - radious, (int)p.getY() - radious, 2*radious, 2*radious);

        //DrawForces(g, scale, center);
    }

    public void DrawForces(Graphics g, double scale, Vec2d center){
        g.setColor(new Color(89, 21, 138));

        for(ForceAbstract f : forces) {
            Vec2d p1 = new Vec2d((position[0].getX()), (position[0].getY()));
            Vec2d p2 = new Vec2d((position[0].add(f.getForce(this).mult(forceDrawingScale)).getX()),
                    (position[0].add(f.getForce(this).mult(forceDrawingScale)).getY()));
            p1 = rescale(p1, scale, center);
            p2 = rescale(p2, scale, center);
            g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
        }
    }


    public double getX(){
        return getPosition().getX();
    }

    public double getY(){
        return getPosition().getY();
    }

}
