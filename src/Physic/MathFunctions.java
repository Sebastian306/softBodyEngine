package Physic;

import Physic.CollisionDetectors.CollisionData;
import Physic.Vec2d;

import java.sql.Struct;

public class MathFunctions {

    public static boolean ccw(Vec2d A, Vec2d B, Vec2d C){
        return (C.getY()-A.getY()) * (B.getX()-A.getX()) > (B.getY()-A.getY()) * (C.getX()-A.getX());
    }

    public static boolean intersect(Vec2d A,Vec2d B,Vec2d C,Vec2d D) {
        if(A.equals(C) || A.equals(D) || B.equals(C) || B.equals(D)) return false;
        return (ccw(A, C, D) != ccw(B, C, D)) && (ccw (A, B, C) != ccw(A, B, D));
    }

    public static Vec2d ClosestPointOnLine(Vec2d points[], Vec2d p){
        Vec2d ab = points[1].sub(points[0]);
        Vec2d ap = p.sub(points[0]);

        double proj = ap.dot(ab);
        double ablen = ab.length();

        Vec2d np = points[0].add(ab.mult(proj/(ablen*ablen)));
        return np;
    }

    public static CollisionData TriangleColisionData(Vec2d A, Vec2d B, Vec2d C, Vec2d P){
        CollisionData cd = new CollisionData();
        cd.colisionOccured = false;
        Vec2d p, delta;
        double c1, c2, c3;
        double l;

        if (A.equals(P) || B.equals(P) || C.equals(P))
            return cd;

        double minX = Math.min(Math.min(A.getX(), B.getX()), C.getX());
        double maxX = Math.max(Math.max(A.getX(), B.getX()), C.getX());
        double minY = Math.min(Math.min(A.getY(), B.getY()), C.getY());
        double maxY = Math.max(Math.max(A.getY(), B.getY()), C.getY());

        if(P.getX() < minX || P.getX() > maxX || P.getY() < minY || P.getY() > maxY){
            return cd;
        }

        c2 = (A.getY() * C.getX() - A.getX() * C.getY() +
                P.getY() * (A.getX() - C.getX()) - P.getX() * (A.getY() - C.getY())) /
                (A.getY() * C.getX() - A.getX() * C.getY() +
                        B.getY() * (A.getX() - C.getX()) - B.getX() * (A.getY() - C.getY()));
        if (A.getX() - C.getX() != 0)
            c3 = (A.getX() - c2 * (A.getX() - B.getX()) - P.getX()) / (A.getX() - C.getX());
        else
            c3 = (A.getY() - c2 * (A.getY() - B.getY()) - P.getY()) / (A.getY() - C.getY());
        c1 = 1 - c2 - c3;

        if (c1 < 0 || c2 < 0 || c3 < 0)
            return cd;

        //System.out.println(String.format("c1: %f, c2: %f, c3: %f", c1, c2, c3));
        //System.out.println(String.format("%s | %s | %s | %s", A,B,C,P));

        l = 1 / (c1*c1 + c2*c2 + c3*c3);

        if(c1 < c2 && c1 < c3){
            p = ClosestPointOnLine(new Vec2d[]{B,C}, P);
        }else if(c2 < c1 && c2 < c3){
            p = ClosestPointOnLine(new Vec2d[]{A,C}, P);
        }else{
            p = ClosestPointOnLine(new Vec2d[]{A,B}, P);
        }

        delta = P.sub(p);

        cd.p = p;
        cd.c1 = c1;
        cd.c2 = c2;
        cd.c3 = c3;
        cd.l = l;
        cd.delta = delta;
        cd.colisionOccured = true;

        return cd;
    }

    public static Vec2d rescale(Vec2d v, double scale, Vec2d center){
        Vec2d res = new Vec2d(v.getX(), v.getY());
        res = res.sub(center);
        res = res.mult(scale);
        return res;
    }
}
