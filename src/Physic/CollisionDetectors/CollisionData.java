package Physic.CollisionDetectors;

import Physic.Vec2d;

public class CollisionData {
    public boolean colisionOccured;
    public double c1, c2, c3, l;
    public Vec2d delta, p;

    @Override
    public String toString(){
        return String.format("(colisionOccured: %b, p: %s, delta: %s, c1: %f, c2: %f, c3: %f, l: %f)",
                colisionOccured, p, delta, c1, c2, c3, l);
    }
}
