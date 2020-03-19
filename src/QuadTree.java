/**
 * QuadTree Class used to efficiently locate any nodes on the graph
 * @author Jakob Coker
 */
public class QuadTree {
    private Rectangle boundary;
    private int capacity;
    private QuadTree topLeft;
    private QuadTree topRight;
    private QuadTree bottomLeft;
    private QuadTree bottomRight;

    public QuadTree(Rectangle boundary, int capacity){
        this.setBoundary(boundary);
        this.capacity = capacity;

    }

    public void subdivide(){
    }


    public Rectangle getBoundary() {
        return boundary;
    }

    public void setBoundary(Rectangle boundary) {
        this.boundary = boundary;
    }

    public int getCapacity() {
        return capacity;
    }


    public QuadTree getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(QuadTree topLeft) {
        this.topLeft = topLeft;
    }

    public QuadTree getTopRight() {
        return topRight;
    }

    public void setTopRight(QuadTree topRight) {
        this.topRight = topRight;
    }

    public QuadTree getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(QuadTree bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    public QuadTree getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(QuadTree bottomRight) {
        this.bottomRight = bottomRight;
    }
}
