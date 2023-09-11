package Physic.CollisionDetectors;

import Physic.Field;
import Physic.Objects.Point;
import Physic.Objects.PhysicalObjects;
import Physic.Vec2d;

import java.awt.*;

import static Physic.MathFunctions.*;

public class CollisionTriangle implements CollisionDetector{

    private final PhysicalObjects[] objects = new PhysicalObjects[3];
    private Point outOf[];

    public CollisionTriangle(PhysicalObjects poA, PhysicalObjects poB, PhysicalObjects poC, Point[] outOf){
        objects[0] = poA;
        objects[1] = poB;
        objects[2] = poC;
        this.outOf = outOf;
    }

    @Override
    public Field getField() {
        Vec2d A = objects[0].getPosition();
        Vec2d B = objects[1].getPosition();
        Vec2d C = objects[2].getPosition();
        double minX = Math.min(Math.min(A.getX(), B.getX()), C.getX());
        double maxX = Math.max(Math.max(A.getX(), B.getX()), C.getX());
        double minY = Math.min(Math.min(A.getY(), B.getY()), C.getY());
        double maxY = Math.max(Math.max(A.getY(), B.getY()), C.getY());

        return new Field(minX, minY, maxX - minX, maxY - minY);
    }

    @Override
    public boolean checkCollision(Point po) {
        for(Point of : outOf)
            if(of.equals(po))
                return false;
        Vec2d A = objects[0].getPosition();
        Vec2d B = objects[1].getPosition();
        Vec2d C = objects[2].getPosition();
        Vec2d P = po.getPosition();
        Vec2d An, Bn, Cn;

        CollisionData cd = TriangleColisionData(A, B, C, P);

        if(!cd.colisionOccured)
            return false;

        double bodyProp = 0.6;
        double pointProp = 0.6;

        //System.out.println(cd);
        //System.out.println(String.format("%s, %s, %s, %s",A,B,C,P));
        if(Double.isNaN(cd.c1))
            return false;
        An = A.add(cd.delta.mult(cd.c1*cd.l * bodyProp));
        Bn = B.add(cd.delta.mult(cd.c2*cd.l * bodyProp));
        Cn = C.add(cd.delta.mult(cd.c3*cd.l * bodyProp));
        po.setPosition(P.sub(cd.delta.mult(pointProp)));

        objects[0].setPosition(An);
        objects[1].setPosition(Bn);
        objects[2].setPosition(Cn);

        return true;
    }

    @Override
    public void Draw(Graphics g, double scale, Vec2d center) {
        g.setColor(Color.blue);
        Vec2d p1 = new Vec2d(objects[0].getPosition().getX(), objects[0].getPosition().getY());
        Vec2d p2 = new Vec2d(objects[1].getPosition().getX(), objects[1].getPosition().getY());
        Vec2d p3 = new Vec2d(objects[2].getPosition().getX(), objects[2].getPosition().getY());

        p1 = rescale(p1, scale, center);
        p2 = rescale(p2, scale, center);
        p3 = rescale(p3, scale, center);

        g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
        g.drawLine((int)p3.getX(), (int)p3.getY(), (int)p2.getX(), (int)p2.getY());
        g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p3.getX(), (int)p3.getY());

    }
}
