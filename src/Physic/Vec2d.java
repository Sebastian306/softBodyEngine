package Physic;

public class Vec2d {

    private double x;
    private double y;

    public Vec2d(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vec2d(){
        this.x = 0;
        this.y = 0;
    }

    public Vec2d add(Vec2d v){
        return new Vec2d( x + v.getX(), y + v.getY());
    }

    public Vec2d sub(Vec2d v){
        return new Vec2d( x - v.getX(), y - v.getY());
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void set(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double dot(Vec2d v){
        return x * v.getX() +  y * v.getY();
    }
    public Vec2d mult(double v){
        return new Vec2d( x * v, y * v);
    }

    public Vec2d div(double v){
        return new Vec2d( x / v, y / v);
    }

    public double distance(Vec2d v){
        return Math.sqrt(Math.pow(x - v.getX(),2) + Math.pow(y - v.getY(),2));
    }

    public boolean equals(Vec2d v){
        return (v.getX() == x) && (v.getY() == y);
    }

    public double length(){
        return Math.sqrt(x*x + y*y);
    }

    @Override
    public String toString(){
        return String.format("( %f, %f )", x, y);
    }
}
