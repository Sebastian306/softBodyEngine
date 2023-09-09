package Physic.Forces;

import Physic.Objects.PhysicalObjects;
import Physic.Vec2d;

public class Force extends ForceAbstract{

    private Vec2d force;

    public Force(Vec2d force){
        this.force = force;
    }

    @Override
    public Vec2d getForce(PhysicalObjects po) {
        return force;
    }

    public void setForce(Vec2d force) {
        this.force = force;
    }
}
