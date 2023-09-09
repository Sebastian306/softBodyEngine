package Physic;

import Physic.Forces.GravityForce;
import Physic.Objects.FixedPivot;
import Physic.Objects.Pivot;
import Physic.Objects.Point;

import java.awt.*;

public class Scenes {
    public static void Scene1(SpacePanel sp){
        Pivot[] blob1 = sp.createSquareBlob(12,100,60,0.3, 0.1, 1, new Color(215, 78, 54));
        sp.createSquareBlob(120,0,50,3,0.08, 5, new Color(229, 138, 55));
        sp.createSquareBlob(30,10,60,1,0.08, 5, new Color(222, 57, 108));
        sp.createSquareBlob(133,-50,40,0.8,0.1, 3, new Color(147, 24, 24));
        sp.createSquareBlob(115,80,40,1,0.05, 4, new Color(246, 255, 152));
        sp.createSquareBlob(160,80,30,2,0.1, 1, new Color(255, 255, 0));
        sp.createSquareBlob(30,-50,50,2,0.1, 3, new Color(162, 255, 0));
        Pivot[] blob2 = sp.createSquareBlob(90,-50,30,2,0.1, 3, new Color(255, 0, 215));
        FixedPivot fp = sp.createNoCollisionFixedPivot(100, 10);
        sp.createMesh(new Point[]{blob2[2], fp}, Color.black);
        sp.createSpring(blob2[2], fp, blob2[2].getPosition().distance(fp.getPosition()), 0.1, 0.5);

        sp.setDrawMode(true);
        sp.setGravity(new GravityForce(new Vec2d(0,8)));
        sp.setCenter(-90,-60);
        sp.createQuadrangle(-10,250,-10,280,220,280,220,250, Color.black, 0.95);
        sp.createQuadrangle(-10,0,10,0,10,280,-10,280, Color.black, 0.99);
        sp.createQuadrangle(200,0,220,0,220,280,200,280, Color.black, 0.99);

        blob1[0].setVelocity(new Vec2d(0 ,-20));
        blob1[1].setVelocity(new Vec2d(20 ,0));
        blob1[3].setVelocity(new Vec2d(-20 ,0));
        blob1[2].setVelocity(new Vec2d(0 ,20));
    }

    public static void Scene2(SpacePanel sp){
        sp.createRoundBlob(100,100,60,1, 0.03, 0.1, 2, new Color(15, 2, 255));
        sp.createRoundBlob(100,0,30,1, 0.03, 0.1, 2, new Color(15, 2, 255));
        sp.setGravity(new GravityForce(new Vec2d(0,9)));
        sp.setDrawMode(false);
        sp.createQuadrangle(-100,350,-100,380,800,380,800,350, Color.black, 0.95);
    }
}
