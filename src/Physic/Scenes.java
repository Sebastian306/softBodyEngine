package Physic;

import Physic.Canvas.SpacePanel;
import Physic.Forces.GravityForce;
import Physic.Objects.FixedPivot;
import Physic.Objects.Pivot;
import Physic.Objects.Point;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class Scenes {

    private static BiConsumer<SpacePanel, GameEngine> scene1 = Scenes::Scene1;
    private static BiConsumer<SpacePanel, GameEngine> scene2 = Scenes::Scene2;
    private static BiConsumer<SpacePanel, GameEngine> scene3 = Scenes::Scene3;
    private static BiConsumer<SpacePanel, GameEngine>[] scenes = new BiConsumer[]{scene1, scene2, scene3};

    public static BiConsumer<SpacePanel, GameEngine>[] getScenes() {
        return scenes.clone();
    }

    public static void Scene1(SpacePanel sp, GameEngine ge){
        Pivot[] blob1 = ge.createSquareBlob(12,100,60,0.5, 0.1, 1, new Color(215, 78, 54));
        ge.createSquareBlob(120,0,50,3,0.08, 5, new Color(229, 138, 55));
        ge.createSquareBlob(30,10,60,1,0.08, 5, new Color(222, 57, 108));
        ge.createSquareBlob(133,-50,40,0.8,0.1, 3, new Color(147, 24, 24));
        ge.createSquareBlob(115,80,40,0.8,0.05, 4, new Color(246, 255, 152));
        ge.createSquareBlob(160,80,30,2,0.1, 1, new Color(255, 255, 0));
        ge.createSquareBlob(30,-50,50,2,0.1, 3, new Color(162, 255, 0));
        Pivot[] blob2 = ge.createSquareBlob(90,-50,30,2,0.1, 3, new Color(255, 0, 215));
        FixedPivot fp = ge.createNoCollisionFixedPivot(100, 10);
        ge.createMesh(new Point[]{blob2[2], fp}, Color.black);
        ge.createSpring(blob2[2], fp, blob2[2].getPosition().distance(fp.getPosition()), 0.1, 0.5);

        ge.setGravity(new GravityForce(new Vec2d(0,8)));
        ge.setCenter(-90,-60);
        ge.createQuadrangle(-10,250,-10,280,220,280,220,250, Color.black, 0.95);
        ge.createQuadrangle(-10,0,10,0,10,280,-10,280, Color.black, 0.99);
        ge.createQuadrangle(200,0,220,0,220,280,200,280, Color.black, 0.99);

        blob1[0].setVelocity(new Vec2d(0 ,-20));
        blob1[1].setVelocity(new Vec2d(20 ,0));
        blob1[3].setVelocity(new Vec2d(-20 ,0));
        blob1[2].setVelocity(new Vec2d(0 ,20));
    }

    public static void Scene2(SpacePanel sp, GameEngine ge){
        ge.createRoundBlob(100,100,60,20, 0.16, 0.1, 1, new Color(255, 20, 20));
        ge.createRoundBlob(230,100,60,20, 0.16, 0.1, 1, new Color(255, 145, 20));
        ge.createRoundBlob(110,0,30,20, 0.07, 0.1, 2, new Color(15, 2, 255));
        ge.createRoundBlob(70,-150,30,20, 0.1, 0.1, 2, new Color(121, 114, 255));
        ge.createRoundBlob(200,0,30,20, 0.1, 0.1, 2, new Color(129, 25, 211));
        ge.setGravity(new GravityForce(new Vec2d(0,9)));
        ge.setScale(1.3);
        ge.setCenter(-100, -50);
        sp.setDrawMode(true);
        ge.createQuadrangle(-100,350,-100,380,800,380,800,350, Color.black, 0.95);
        ge.createQuadrangle(0,350,0,0,-20,0,-20,350, Color.black, 0.95);
        ge.createQuadrangle(300,350,300,0,320,0,320,350, Color.black, 0.95);
    }

    public static void Scene3(SpacePanel sp, GameEngine ge){

        ge.setCenter(-50, -50);
        ge.setScale(1.15);
        sp.setDrawMode(true);
        ge.setGravity(new GravityForce(new Vec2d(0,10)));
        sp.setBackground(new Color(0, 113, 128));

        ge.createRoundBlob(120,-30,35,20, 0.1, 0.1, 2, new Color(0, 178, 5));
        ge.createRoundBlob(60,10,35,20, 0.12, 0.1, 2, new Color(159, 255, 0));

        ge.createQuadrangle(170,130,170,155,-25,81,-25,55, new Color(120, 232, 223), 1);
        ge.createQuadrangle(190,250,190,225,425,170,425,195, new Color(120, 232, 223), 1);
        ge.createQuadrangle(0,0,-25,0,-25,425,0,425, Color.black, 0.98);
        ge.createQuadrangle(425,0,400,0,400,425,425,425, Color.black, 0.98);
        ge.createQuadrangle(145,285,145,310,0,255,0,230, Color.black, 0.98);
        ge.createQuadrangle(-25,400,200,400,200,425,-25,425, Color.black, 0.95);
        ge.createQuadrangle(150,400,425,300,425,425,150,425, Color.black, 0.98);
    }
}
