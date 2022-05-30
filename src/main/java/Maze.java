
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze extends JFrame {

    private int[][] values;
    private boolean[][] visited;
    private int startRow;
    private int startColumn;
    private ArrayList<JButton> buttonList;
    private int rows;
    private int columns;
    private boolean backtracking;
    private int algorithm;

    public Maze(int algorithm, int size, int startRow, int startColumn) {
        this.algorithm = algorithm;
        Random random = new Random();
        this.values = new int[size][];
        for (int i = 0; i < values.length; i++) {
            int[] row = new int[size];
            for (int j = 0; j < row.length; j++) {
                if (i > 1 || j > 1) {
                    row[j] = random.nextInt(8) % 7 == 0 ? Definitions.OBSTACLE : Definitions.EMPTY;
                } else {
                    row[j] = Definitions.EMPTY;
                }
            }
            values[i] = row;
        }
        values[0][0] = Definitions.EMPTY;
        values[size - 1][size - 1] = Definitions.EMPTY;
        this.visited = new boolean[this.values.length][this.values.length];
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.buttonList = new ArrayList<>();
        this.rows = values.length;
        this.columns = values.length;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridLayout gridLayout = new GridLayout(rows, columns);
        this.setLayout(gridLayout);
        for (int i = 0; i < rows * columns; i++) {
            int value = values[i / rows][i % columns];
            JButton jButton = new JButton(String.valueOf(i));
            if (value == Definitions.OBSTACLE) {
                jButton.setBackground(Color.BLACK);
            } else {
                jButton.setBackground(Color.WHITE);
            }
            this.buttonList.add(jButton);
            this.add(jButton);
        }
        this.setVisible(true);
        this.setSize(Definitions.WINDOW_WIDTH, Definitions.WINDOW_HEIGHT);
        this.setResizable(false);
    }

    public void checkWayOut() {
        new Thread(() -> {
            boolean result =false;
            switch (this.algorithm) {
                case Definitions.ALGORITHM_DFS:
                    result=DFS();
                     break;
                case Definitions.ALGORITHM_BFS:{

                }
                    break;
            }
            JOptionPane.showMessageDialog(null,  result ? "FOUND SOLUTION" : "NO SOLUTION FOR THIS MAZE");

        }).start();
    }


    public void setSquareAsVisited(int x, int y, boolean visited) {
        try {
            if (visited) {
                if (this.backtracking) {
                    Thread.sleep(Definitions.PAUSE_BEFORE_NEXT_SQUARE * 5);
                    this.backtracking = false;
                }
                this.visited[x][y] = true;
                for (int i = 0; i < this.visited.length; i++) {
                    for (int j = 0; j < this.visited[i].length; j++) {
                        if (this.visited[i][j]) {
                            if (i == x && y == j) {
                                this.buttonList.get(i * this.rows + j).setBackground(Color.RED);
                            } else {
                                this.buttonList.get(i * this.rows + j).setBackground(Color.BLUE);
                            }
                        }
                    }
                }
            } else {
                this.visited[x][y] = false;
                this.buttonList.get(x * this.columns + y).setBackground(Color.WHITE);
                Thread.sleep(Definitions.PAUSE_BEFORE_BACKTRACK);
                this.backtracking = true;
            }
            if (!visited) {
                Thread.sleep(Definitions.PAUSE_BEFORE_NEXT_SQUARE / 4);
            } else {
                Thread.sleep(Definitions.PAUSE_BEFORE_NEXT_SQUARE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




        private boolean isGoal(Node node) {
            boolean goal = false;
            if (node.getX()==rows-1 && node.getY()==columns-1) {
                goal=true;
            }
            return goal;
        }

        public Node[][] settingNodesToMatrix() {
            Node[][] nodes = new Node[rows][columns];
            for (int i = 0; i < rows; i++) {
                nodes[i] = new Node[rows];
                for (int j = 0; j < columns; j++) {
                    nodes[i][j] = new Node(i, j);
                }
            }
            return nodes;
        }

        private void settingNeighbors(Node[][] nodes) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    Node currentNode = nodes[i][j];
                    if (checkValidIndex(i - 1) != Definitions.INVALID_INDEX) {
                        currentNode.addNeighbor(nodes[i - 1][j]);
                    }
                    if (checkValidIndex(j - 1) !=  Definitions.INVALID_INDEX) {
                        currentNode.addNeighbor(nodes[i][j - 1]);
                    }
                    if (checkValidIndex(i + 1) !=  Definitions.INVALID_INDEX) {
                        nodes[i][j].addNeighbor(nodes[i + 1][j]);
                    }
                    if (checkValidIndex(j + 1) !=  Definitions.INVALID_INDEX) {
                        currentNode.addNeighbor(nodes[i][j + 1]);
                    }

                }
            }
        }
        private int checkValidIndex(int currentIndex){
            int valid= Definitions.INVALID_INDEX;
            if (currentIndex<rows  && currentIndex>=0 ){
                valid=currentIndex;
            }
            return valid;
        }



        private boolean isEmpty (Node nodeToCheck) {
            boolean isEmpty=false;

            if (values[nodeToCheck.getX()][nodeToCheck.getY()] == Definitions.EMPTY){
                isEmpty=true;
            }
            return isEmpty ;
        }



    private boolean DFS (){
        boolean result=false;
        Stack<Node> stack = new Stack<>();
        Node[][] nodes = settingNodesToMatrix();
        settingNeighbors(nodes);
        stack.add(nodes[0][0]);
        while (!stack.empty() && !result) {
            Node currentNode = stack.pop();
            if (!visited[currentNode.getX()][currentNode.getY()]) {
                setSquareAsVisited(currentNode.getX(), currentNode.getY(),true);
                if (isGoal(currentNode)){
                    result=true;
                }else {
                    for (Node neighbor :  currentNode.getNeighbors()) {
                        if (!visited[neighbor.getX()][neighbor.getY()] && isEmpty(neighbor))
                            stack.add(neighbor);
                    }
                }
            }
        }
        return result;
    }

}


