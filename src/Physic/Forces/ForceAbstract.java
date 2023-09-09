package Physic.Forces;

import Physic.Objects.PhysicalObjects;
import Physic.Vec2d;

public abstract class ForceAbstract {
    public abstract Vec2d getForce(PhysicalObjects po);
}
