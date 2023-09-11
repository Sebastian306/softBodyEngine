package Physic;

import java.util.Objects;

public record Field(double x, double y, double w, double h){
    public Field(double x, double y, double w, double h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        Objects.requireNonNull(w);
        Objects.requireNonNull(h);
    }

    public String toString(){
        return String.format("x : %f, y : %f, w : %f, h : %f", x, y, w, h);
    }
}
