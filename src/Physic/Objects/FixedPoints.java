package Physic.Objects;

import Physic.Vec2d;

public class FixedPoints implements Point{
    double x;
    double y;

    public FixedPoints(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public Vec2d getPosition() {
        return new Vec2d(x,y);
    }

    @Override
    public Vec2d getVelocity() {
        return new Vec2d(0,0);
    }

    @Override
    public void setPosition(Vec2d v) {
        // Nothing
    }

    @Override
    public void setVelocity(Vec2d v) {
        // Nothing
    }
}
