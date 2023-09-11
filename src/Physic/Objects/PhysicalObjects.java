package Physic.Objects;

import Physic.Forces.ForceAbstract;
import Physic.Canvas.SpacePanel;
import Physic.Vec2d;

import java.awt.*;
import java.util.HashSet;

public abstract class PhysicalObjects {

    protected Vec2d position[] = new Vec2d[2];
    protected double mass;
    protected HashSet<ForceAbstract> forces = new HashSet<ForceAbstract>();
    protected Vec2d accelation;
    protected SpacePanel space;

    public Vec2d getPosition(){
        return position[0];
    };

    public Vec2d[] getPositions(){
        return position;
    };

    public double getMass(){
        return mass;
    };

    public Vec2d getVelocity() {
        return position[0].sub(position[1]).div(space.getTimeRate());
    }

    public void setVelocity(Vec2d velocity) {
        position[1] = position[0].sub(velocity.mult(space.getTimeRate()));
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setPositionKeepingSpeed(Vec2d position) {
        Vec2d delt = position.sub(this.position[0]);
        this.position[0].add(delt);
        this.position[1].add(delt);
    }

    public void setPosition(Vec2d position) {
        this.position[0] = position;
    }

    public void addForce(ForceAbstract f){
        forces.add(f);
    };

    public void removeForce(ForceAbstract f){
        forces.remove(f);
    };

    public void setSpace(SpacePanel space) {
        this.space = space;
    }

    public SpacePanel getSpace() {
        return space;
    }

    public Vec2d getAcceleration() {
        Vec2d acc = new Vec2d(0,0);
        for(ForceAbstract f: forces){
            acc = acc.add(f.getForce(this).div(mass));
        }
        return acc;
    }

    public void accelerationUpdate(){
        this.accelation = getAcceleration();
    }

    public void positionUpdate(double t){
        //System.out.println(String.format("_______accL : %f, acc : %s", accelation.length(), accelation.toString()));
        Vec2d pos = position[0].mult(2).sub(position[1]).add(accelation.mult(t*t));
        position[1] = position[0];
        position[0] = pos;
    }
    public abstract void Draw(Graphics g, double scale, Vec2d center);
}
