import java.util.ArrayList;
import java.util.List;

public class Node {
    private int x;
    private int y;
    private List<Node> neighbors;

    public Node(int x,int y){
        this.x=x;
        this.y=y;
        neighbors=new ArrayList<>();

    }

    public void addNeighbor(Node neighbor){
        this.neighbors.add(neighbor);

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Node> neighbors) {
        this.neighbors = neighbors;
    }
}

