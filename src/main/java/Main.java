import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String algorithmString = JOptionPane.showInputDialog("Choose algorithm (1 - DFS, 2 - BFS) ");
        String sizeAsString = JOptionPane.showInputDialog("Choose maze size: ");
        int size = Integer.parseInt(sizeAsString);
        int algorithm = Integer.parseInt(algorithmString);
        Maze maze = new Maze(algorithm, size, 0, 0);
        maze.checkWayOut();
    }

}
