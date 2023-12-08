package Physic;

import Physic.CollisionDetectors.CollisionData;
import Physic.Vec2d;

import java.sql.Struct;

public class MathFunctions {

    public static boolean ccw(Vec2d A, Vec2d B, Vec2d C){
        return (C.getY()-A.getY()) * (B.getX()-A.getX()) > (B.getY()-A.getY()) * (C.getX()-A.getX());
    }

    /**
     * Checks whether two lines represented by 4 points intersect
     * @param A starting point of first line
     * @param B ending point of first line
     * @param C starting point of second line
     * @param D ending point of second line
     * @return true if lines intersect, false in other cases
    **/
    public static boolean intersect(Vec2d A, Vec2d B,Vec2d C,Vec2d D) {
        if(A.equals(C) || A.equals(D) || B.equals(C) || B.equals(D)) return false;
        return (ccw(A, C, D) != ccw(B, C, D)) && (ccw (A, B, C) != ccw(A, B, D));
    }

    /**
     * Returns the point on a given line that is closest to a given point p
     * @param A starting point of line
     * @param B ending point of line
     * @param p the point for which we want to find the closest point on the straight line
     * @return the point on a given line that is closest to a given point p
     **/
    public static Vec2d ClosestPointOnLine(Vec2d A, Vec2d B, Vec2d p){
        Vec2d ab = B.sub(A);
        Vec2d ap = p.sub(A);

        double proj = ap.dot(ab);
        double ablen = ab.length();

        Vec2d np = A.add(ab.mult(proj/(ablen*ablen)));
        return np;
    }

    /**
     * Computes collision data for a point P with a triangle defined by vertices A, B, and C.
     *
     * @param A The vertex A of the triangle.
     * @param B The vertex B of the triangle.
     * @param C The vertex C of the triangle.
     * @param P The point for which collision data is calculated.
     * @return A CollisionData object containing information about the collision.
     */
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


        l = 1 / (c1*c1 + c2*c2 + c3*c3);

        if(c1 < c2 && c1 < c3){
            p = ClosestPointOnLine(B,C, P);
        }else if(c2 < c1 && c2 < c3){
            p = ClosestPointOnLine(A,C, P);
        }else{
            p = ClosestPointOnLine(A,B, P);
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

    /**
     * Scales a 2D vector with a specified scaling factor, relative to a given central point.
     *
     * @param v      The 2D vector to be scaled.
     * @param scale  The scaling factor by which the vector should be multiplied.
     * @param center The central point relative to which the scaling should be performed.
     * @return A new 2D vector after scaling.
     */
    public static Vec2d rescale(Vec2d v, double scale, Vec2d center){
        Vec2d res = new Vec2d(v.getX(), v.getY());
        res = res.sub(center);
        res = res.mult(scale);
        return res;
    }
}
