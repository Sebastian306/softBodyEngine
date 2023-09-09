package Physic.CollisionDetectors;

import Physic.Objects.Point;
import Physic.Vec2d;

import java.awt.*;

import static Physic.MathFunctions.TriangleColisionData;
import static Physic.MathFunctions.rescale;

public class CollisionTriangleBox implements CollisionDetector{

    private final Vec2d[] points = new Vec2d[3];
    double friction;

    public CollisionTriangleBox(Vec2d poA, Vec2d poB, Vec2d poC, double friction){
        points[0] = poA;
        points[1] = poB;
        points[2] = poC;
        this.friction = friction;
        if(this.friction < 0)
            throw new RuntimeException("friction cannot be negative");
        if(this.friction > 1)
            throw new RuntimeException("friction cannot be greater than 1");
    }

    @Override
    public boolean checkCollision(Point po) {
        Vec2d A = points[0];
        Vec2d B = points[1];
        Vec2d C = points[2];
        Vec2d P = po.getPosition();

        CollisionData cd = TriangleColisionData(A, B, C, P);

        if(!cd.colisionOccured)
            return false;

        po.setPosition(P.sub(cd.delta));
        po.setVelocity(po.getVelocity().mult(this.friction));

        return true;
    }

    @Override
    public void Draw(Graphics g, double scale, Vec2d center) {
        g.setColor(Color.blue);
        Vec2d p1 = new Vec2d(points[0].getX(),points[0].getY());
        Vec2d p2 = new Vec2d(points[1].getX(),points[1].getY());
        Vec2d p3 = new Vec2d(points[2].getX(),points[2].getY());

        p1 = rescale(p1, scale, center);
        p2 = rescale(p2, scale, center);
        p3 = rescale(p3, scale, center);

        g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
        g.drawLine((int)p3.getX(), (int)p3.getY(), (int)p2.getX(), (int)p2.getY());
        g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p3.getX(), (int)p3.getY());
    }

}
