package Physic;

public class Pair<T, T1> {
    T t0;
    T1 t1;

    public Pair(T t0, T1 t1){
        this.t0 = t0;
        this.t1 = t1;
    }

    public void setT0(T t0) {
        this.t0 = t0;
    }

    public void setT1(T1 t1) {
        this.t1 = t1;
    }

    public T getT0() {
        return t0;
    }

    public T1 getT1() {
        return t1;
    }
}
