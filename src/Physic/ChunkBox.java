package Physic;

import Physic.Objects.Point;

import java.util.*;

public class ChunkBox {
    private double endX;
    private double endY;
    private double startX;
    private double startY;
    private double height;
    private double width;
    private double chunkWidth;
    private double chunkHeight;
    private int chunkCountX;
    private int chunkCountY;
    private ArrayList<ArrayList<HashSet<Point>>>  chunks;
    private HashMap<Object, Pair<Integer, Integer>> addedPoints = new HashMap<Object, Pair<Integer, Integer>>();

    public ChunkBox(double startX, double startY, double width, double height, int chunkCountX, int chunkCountY){
        this.startX = startX;
        this.startY = startY;
        this.endX = startX + width;
        this.endY = startY + height;
        this.chunkCountX = chunkCountX;
        this.chunkCountY = chunkCountY;
        this.width = width;
        this.height = height;
        this.chunkWidth = width/chunkCountX;
        this.chunkHeight = height/chunkCountY;
        chunks = new ArrayList<ArrayList<HashSet<Point>>>();
        for(int x = 0; x < chunkCountX; x++){
            chunks.add(new ArrayList<HashSet<Point>>());
            for (int y = 0; y < chunkCountY; y++){
                chunks.get(x).add(new HashSet<Point>());
            }
        }
    }

    public void setObject(Point p){
        if(addedPoints.containsKey(p)){
            Pair<Integer, Integer> coord = addedPoints.get(p);
            chunks.get(coord.getT0()).get(coord.getT1()).remove(p);
        }

        if(p.getX() < startX || p.getX() > endX || p.getY() < startY || p.getY() > endY)
            return;

        int px = (int)((p.getX() - startX) / chunkWidth);
        int py = (int)((p.getY() - startY) / chunkHeight);

        chunks.get(px).get(py).add(p);
        addedPoints.put(p, new Pair<Integer, Integer>(px, py));

    }

    public List<Point> getCollisionCandidates(Field f){
        int sx = (int)Math.max(0, (f.x() - startX) / chunkWidth);
        int ex = (int)Math.min(chunkCountX - 1, (f.x() + f.w() - startX) / chunkWidth);
        int sy = (int)Math.max(0, (f.y() - startY) / chunkHeight);
        int ey = (int)Math.min(chunkCountY - 1, (f.y() + f.h() - startY) / chunkHeight);

        //System.out.println(String.format("sx : %d, ex : %d, sy : %d, ey : %d", sx, ex, sy, ey));

        ArrayList<Point> res = new ArrayList<Point>();

        for(int x = sx; x <= ex; x++){
            for (int y = sy; y <= ey; y++){
                res.addAll(chunks.get(x).get(y));
            }
        }

        return res;
    }
}
