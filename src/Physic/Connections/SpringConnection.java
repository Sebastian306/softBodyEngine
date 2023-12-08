package Physic.Connections;

import Physic.ColorLinearTransition;
import Physic.Forces.SpringForce;
import Physic.Objects.PhysicalObjects;
import Physic.Vec2d;

import java.awt.*;

import static Physic.MathFunctions.rescale;

/**
 * Represents a spring connection between two physical objects.
 */
public class SpringConnection extends Connection {

    private double size;
    private double k;
    private double b;
    private SpringForce springForce;
    private double dotDistance = 8;
    private int radius = 4;
    private double maxForce = 100;
    private double criticalSize = 0;
    private double criticalK = 100;
    private double maxDrawingForce = 5;

    /**
     * Constructs a SpringConnection with specified physical objects, size, spring constant (k), and damping coefficient (b).
     *
     * @param poA         The first physical object connected by the spring.
     * @param poB         The second physical object connected by the spring.
     * @param size        The size of the spring.
     * @param k           The spring constant.
     * @param b           The damping coefficient.
     * @param criticalSize The critical size for the spring.
     * @param criticalK   The critical spring constant.
     */
    public SpringConnection(PhysicalObjects poA, PhysicalObjects poB, double size, double k, double b, double criticalSize, double criticalK) {
        this.size = size;
        this.k = k;
        this.b = b;
        this.objects = new PhysicalObjects[]{poA, poB};
        this.springForce = new SpringForce(this);
        poA.addForce(this.springForce);
        poB.addForce(this.springForce);
        this.criticalSize = criticalSize;
        this.criticalK = criticalK;
    }

    /**
     * Constructs a SpringConnection with specified physical objects, size, spring constant (k), and default damping coefficient (b = 0).
     *
     * @param poA The first physical object connected by the spring.
     * @param poB The second physical object connected by the spring.
     * @param size The size of the spring.
     * @param k    The spring constant.
     */
    public SpringConnection(PhysicalObjects poA, PhysicalObjects poB, double size, double k){
        this.size = size;
        this.k = k;
        this.b = 0;
        this.objects = new PhysicalObjects[]{poA,poB};
        this.springForce = new SpringForce(this);
        poA.addForce(this.springForce);
        poB.addForce(this.springForce);
    }

    public double getK() {
        return k;
    }

    public double getSize() {
        return size;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getMaxForce() {
        return maxForce;
    }

    public double getCriticalK() {
        return criticalK;
    }

    public double getCriticalSize() {
        return criticalSize;
    }

    @Override
    public void Draw(Graphics g, double scale, Vec2d center) {

        double dist = objects[0].getPosition().distance(objects[1].getPosition());
        double force = Math.abs(springForce.getForce(objects[0]).length());

        ColorLinearTransition clt = new ColorLinearTransition(new Color[]{
                new Color(0,0,255),
                new Color(0, 255, 221),
                new Color(30, 255, 0),
                new Color(145, 145, 145),
                new Color(255,255,0),
                new Color(245, 167, 32),
                new Color(255,0,0),
        }, -maxDrawingForce, maxDrawingForce);

        g.setColor(clt.getColor(force));

        double num = Math.floor(objects[0].getPosition().distance(objects[1].getPosition()) / dotDistance);
        Vec2d diff = new Vec2d(
                (objects[1].getPosition().getX() - objects[0].getPosition().getX())/num,
                (objects[1].getPosition().getY() - objects[0].getPosition().getY())/num
        );
        Vec2d pos = objects[0].getPosition().add(diff);

        for(int i = 1; i < (int)num; i++){
            Vec2d p1 = new Vec2d(pos.getX(), pos.getY());
            p1 = rescale(p1, scale, center);

            g.fillOval((int)p1.getX() - radius, (int)p1.getY() - radius, (int)(2*radius), (int)(2*radius));
            pos = pos.add(diff);
        }
    }

    public SpringForce getSpringForce() {
        return springForce;
    }
}
