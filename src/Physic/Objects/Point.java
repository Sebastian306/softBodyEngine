package Physic.Objects;

import Physic.Vec2d;

public interface Point {
    Vec2d getPosition();
    void setPosition(Vec2d v);
    Vec2d getVelocity();
    void setVelocity(Vec2d v);
    double getX();
    double getY();
}
