package Physic.Connections;

import Physic.Objects.PhysicalObjects;
import Physic.Canvas.SpacePanel;
import Physic.Vec2d;

import java.awt.*;

public abstract class Connection {
    protected PhysicalObjects objects[] = new PhysicalObjects[2];
    protected SpacePanel space;

    public SpacePanel getSpace() {
        return space;
    }

    public void setSpace(SpacePanel space) {
        this.space = space;
    }

    public PhysicalObjects[] getObjects(){
        return objects;
    }
    public abstract void Draw(Graphics g, double scale, Vec2d center);
}
