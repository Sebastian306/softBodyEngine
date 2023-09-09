package Physic.CollisionDetectors;

import Physic.Objects.Point;
import Physic.Vec2d;

import java.awt.*;

public interface CollisionDetector {
    boolean checkCollision(Point po);
    void Draw(Graphics g, double scale, Vec2d center);
}
