package Physic;

import Physic.Objects.Point;

import java.util.*;

/**
 * Represents a spatial partitioning structure for efficiently handling collision detection.
 */
public class ChunkBox {

    private final double endX;
    private final double endY;
    private final double startX;
    private final double startY;
    private double height;
    private double width;
    private final double chunkWidth;
    private final double chunkHeight;
    private final int chunkCountX;
    private final int chunkCountY;
    private final ArrayList<ArrayList<HashSet<Point>>> chunks;
    private final HashMap<Object, Pair<Integer, Integer>> addedPoints = new HashMap<Object, Pair<Integer, Integer>>();

    /**
     * Initializes a new ChunkBox with specified dimensions and partition counts.
     *
     * @param startX      The starting X-coordinate of the ChunkBox.
     * @param startY      The starting Y-coordinate of the ChunkBox.
     * @param width       The width of the ChunkBox.
     * @param height      The height of the ChunkBox.
     * @param chunkCountX The number of partitions in the X-axis.
     * @param chunkCountY The number of partitions in the Y-axis.
     */
    public ChunkBox(double startX, double startY, double width, double height, int chunkCountX, int chunkCountY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = startX + width;
        this.endY = startY + height;
        this.chunkCountX = chunkCountX;
        this.chunkCountY = chunkCountY;
        this.width = width;
        this.height = height;
        this.chunkWidth = width / chunkCountX;
        this.chunkHeight = height / chunkCountY;
        chunks = new ArrayList<ArrayList<HashSet<Point>>>();
        for (int x = 0; x < chunkCountX; x++) {
            chunks.add(new ArrayList<HashSet<Point>>());
            for (int y = 0; y < chunkCountY; y++) {
                chunks.get(x).add(new HashSet<Point>());
            }
        }
    }

    /**
     * Sets the object at the specified point in the ChunkBox.
     *
     * @param p The Point object to be set in the ChunkBox.
     */
    public void setObject(Point p) {
        if (addedPoints.containsKey(p)) {
            Pair<Integer, Integer> coord = addedPoints.get(p);
            chunks.get(coord.getT0()).get(coord.getT1()).remove(p);
        }

        if (p.getX() < startX || p.getX() > endX || p.getY() < startY || p.getY() > endY)
            return;

        int px = (int) ((p.getX() - startX) / chunkWidth);
        int py = (int) ((p.getY() - startY) / chunkHeight);

        chunks.get(px).get(py).add(p);
        addedPoints.put(p, new Pair<Integer, Integer>(px, py));
    }

    /**
     * Retrieves a list of collision candidates for the specified field.
     *
     * @param f The Field object for which collision candidates are to be retrieved.
     * @return A list of collision candidates.
     */
    public List<Point> getCollisionCandidates(Field f) {
        int sx = (int) Math.max(0, (f.x() - startX) / chunkWidth);
        int ex = (int) Math.min(chunkCountX - 1, (f.x() + f.w() - startX) / chunkWidth);
        int sy = (int) Math.max(0, (f.y() - startY) / chunkHeight);
        int ey = (int) Math.min(chunkCountY - 1, (f.y() + f.h() - startY) / chunkHeight);

        ArrayList<Point> res = new ArrayList<Point>();

        for (int x = sx; x <= ex; x++) {
            for (int y = sy; y <= ey; y++) {
                res.addAll(chunks.get(x).get(y));
            }
        }

        return res;
    }
}
