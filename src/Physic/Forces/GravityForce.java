package Physic.Forces;

import Physic.Objects.PhysicalObjects;
import Physic.Vec2d;

public class GravityForce extends ForceAbstract{

    private Vec2d gravityConst;

    public GravityForce(Vec2d gravityConst){
        this.gravityConst = gravityConst;
    }

    @Override
    public Vec2d getForce(PhysicalObjects po){
        return gravityConst.mult(po.getMass());
    }

    public Vec2d getConst() {
        return gravityConst;
    }

    public void setConst(Vec2d gravityConst) {
        this.gravityConst = gravityConst;
    }
}
