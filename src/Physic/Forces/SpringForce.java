package Physic.Forces;

import Physic.Connections.SpringConnection;
import Physic.Objects.PhysicalObjects;
import Physic.Telemetry.Telemetry;
import Physic.Vec2d;

public class SpringForce extends ForceAbstract{

    private SpringConnection spring;

    public SpringForce(SpringConnection spring){
        this.spring = spring;
    }

    public SpringConnection getSpring() {
        return spring;
    }

    @Override
    public Vec2d getForce(PhysicalObjects po) {
        if(!spring.getObjects()[0].equals(po) && !spring.getObjects()[1].equals(po))
            return new Vec2d(0,0);
        else {
            double dist = spring.getObjects()[0].getPosition().distance(spring.getObjects()[1].getPosition());

            if(dist < 0.0000001){
                return new Vec2d(0,0);
            }
            double force;
            if(dist < spring.getCriticalSize()){
                force = (spring.getSize() - dist)*spring.getCriticalK();
            }else{
                force = (spring.getSize() - dist)*spring.getK();
            }

            Vec2d direction = spring.getObjects()[0].getPosition().sub(spring.getObjects()[1].getPosition()).div(dist);
            Vec2d springVel = spring.getObjects()[0].getVelocity().sub(spring.getObjects()[1].getVelocity());

            if(spring.getObjects()[1].equals(po)){
                direction = direction.mult(-1);
                springVel = springVel.mult(-1);
            }
            //System.out.println(String.format(" point 1: %b, force: %f, dierction: %s", spring.getObjects()[1].equals(po), force, direction.mult(force).toString()));

            double dampingRate = springVel.dot(direction) * spring.getB();
            dampingRate = Math.min(dist, Math.abs(dampingRate)) * Math.signum(dampingRate);

            Vec2d damping = direction.mult(dampingRate);

            return direction.mult(force).sub(damping);
        }
    }
}
