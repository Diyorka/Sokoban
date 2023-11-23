import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Comparator;

public class PathFinder {

    private int lastIndexX;
    private int lastIndexY;
    private int moves;

    public PathFinder() {
        lastIndexX = -1;
        lastIndexY = -1;
        moves = -1;
    }

    public boolean isValid(int[][] grid, int rows, int cols, PathPair point) {
        if (rows > 0 && cols > 0) {
            return (point.getY() >= 0) && (point.getY() < rows)
                    && (point.getX() >= 0)
                    && (point.getX() < cols);
        }
        return false;
    }

    public boolean isUnBlocked(int[][] grid, int rows, int cols, PathPair point) {
        return isValid(grid, rows, cols, point)
                && (grid[point.getY()][point.getX()] != 2 && grid[point.getY()][point.getX()] != 3);
    }

    public boolean isDestination(PathPair position, PathPair dest) {
        return position == dest || position.equals(dest);
    }

    public double calculateHValue(PathPair src, PathPair dest) {
        return Math.sqrt(Math.pow((src.getY() - dest.getY()), 2.0) + Math.pow((src.getX() - dest.getX()), 2.0));
    }

    public void tracePath(PathCell[][] cellDetails, int cols, int rows, PathPair dest) {
        Stack<PathPair> path = new Stack<>();

        int row = dest.getY();
        int col = dest.getX();

        PathPair nextNode = cellDetails[row][col].getParent();
        do {
            path.push(new PathPair(row, col));
            nextNode = cellDetails[row][col].getParent();
            row = nextNode.getY();
            col = nextNode.getX();
        } while (cellDetails[row][col].getParent() != nextNode);

        moves = -1;
        while (!path.empty()) {
            PathPair p = path.peek();
            path.pop();
            lastIndexY = p.getY();
            lastIndexX = p.getX();
            moves++;
        }
    }

    public void aStarSearch(int[][] grid, int rows, int cols, PathPair src, PathPair dest) {
        if (!isValid(grid, rows, cols, src)) {
            return;
        }

        if (!isValid(grid, rows, cols, dest)) {
            return;
        }

        if (!isUnBlocked(grid, rows, cols, src)
                || !isUnBlocked(grid, rows, cols, dest)) {
            return;
        }

        int mapY = dest.getY();
        int mapX = dest.getX();
        if (grid[mapY][mapX] == 2 || grid[mapY][mapX] == 3) {
            return;
        }

        if (isDestination(src, dest)) {
            return;
        }

        boolean[][] closedList = new boolean[rows][cols];
        PathCell[][] cellDetails = new PathCell[rows][cols];

        int i, j;
        i = src.getY();
        j = src.getX();
        cellDetails[i][j] = new PathCell();
        cellDetails[i][j].setF(0.0);
        cellDetails[i][j].setG(0.0);
        cellDetails[i][j].setH(0.0);
        cellDetails[i][j].setParent(new PathPair(i, j));

        Comparator<PathDetails> pathDetailsComparator = new PathDetailsComparator();
        PriorityQueue<PathDetails> openList = new PriorityQueue<>(pathDetailsComparator);

        openList.add(new PathDetails(0.0, i, j));

        while (!openList.isEmpty()) {
            PathDetails p = openList.peek();
            i = p.getI();
            j = p.getJ();

            openList.poll();
            closedList[i][j] = true;

            for (int direction = 0; direction < 4; direction++) {
                int addX = 0, addY = 0;

                switch (direction) {
                    case 0:
                        addY = -1;
                        break;
                    case 1:
                        addY = 1;
                        break;
                    case 2:
                        addX = -1;
                        break;
                    case 3:
                        addX = 1;
                        break;
                }

                PathPair neighbour = new PathPair(i + addX, j + addY);

                if (isValid(grid, rows, cols, neighbour)) {
                    int neighbourY = neighbour.getY();
                    int neighbourX = neighbour.getX();

                    if (cellDetails[neighbourY] == null) {
                        cellDetails[neighbourY] = new PathCell[cols];
                    }

                    if (cellDetails[neighbourY][neighbourX] == null) {
                        cellDetails[neighbourY][neighbourX] = new PathCell();
                    }

                    if (isDestination(neighbour, dest)) {
                        cellDetails[neighbourY][neighbourX].setParent(new PathPair(i, j));
                        tracePath(cellDetails, rows, cols, dest);
                        return;
                    } else if (!closedList[neighbourY][neighbourX]
                            && isUnBlocked(grid, rows, cols, neighbour)) {
                        double gNew, hNew, fNew;
                        gNew = cellDetails[i][j].getG() + 1.0;
                        hNew = calculateHValue(neighbour, dest);
                        fNew = gNew + hNew;

                        if (cellDetails[neighbourY][neighbourX].getF() == -1
                                || cellDetails[neighbourY][neighbourX].getF() > fNew) {

                            openList.add(new PathDetails(fNew, neighbour.getY(), neighbour.getX()));

                            cellDetails[neighbourY][neighbourX].setG(gNew);
                            cellDetails[neighbourY][neighbourX].setF(fNew);
                            cellDetails[neighbourY][neighbourX].setParent(new PathPair(i, j));
                        }
                    }
                }
            }
        }
    }

    public int getNewX() {
        return lastIndexX;
    }

    public int getNewY() {
        return lastIndexY;
    }

    public int getMoves() {
        return moves;
    }
}
