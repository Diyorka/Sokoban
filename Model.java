public class Model {

    private final int SPACE = 0;
    private final int PLAYER = 1;
    private final int WALL = 2;
    private final int BOX = 3;
    private final int CHECK = 4;

    private final char LEFT = 'a';
    private final char RIGHT = 'd';
    private final char UP = 'w';
    private final char DOWN = 's';
    private final char LOAD = 'r';
    private final char EXIT = 'q';

    private int playerPosX;
    private int playerPosY;

    private Viewer viewer;

    private int[][] map;
    private Levels levelList;

    private int totalMoves = 0; //debug

    private int playerCount;
    private int boxesCount;
    private int checksCount;

    private int[][] checksPos;

    public Model(Viewer viewer) {
        this.viewer = viewer;
        levelList = new Levels();
        playerPosX = -1;
        playerPosY = -1;
    }

    public int[][] getDesktop(){
        return map;
    }

    public void doAction(char message) {
        System.out.println("got -- " + message); //debug
        if(message == LOAD) {
            System.out.println("------------ Map loaded ------------\n\n"); //debug
            map = levelList.getNextLevel();
            scanMap();
        }

        if (message == LEFT) {
            moveLeft();
            totalMoves++;
        } else if(message == RIGHT) {
            moveRight();
            totalMoves++;
        } else if(message == UP) {
            moveTop();
            totalMoves++;
        } else if(message == DOWN) {
            moveBot();
            totalMoves++;
        } else if(message == EXIT) {
            System.exit(0);
        }

        returnCheck();
        viewer.update();

        System.out.println("Moves: " + totalMoves); //debug

        if (isWon()) {
            javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), "You win!");
            map = levelList.getNextLevel();
            scanMap();
            viewer.update();
            totalMoves = 0;
        }
    }

    private void scanMap() {
        for(int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == PLAYER) {
                    playerPosX = j;
                    playerPosY = i;
                    playerCount++;
                } else if (map[i][j] == BOX) {
                    boxesCount++;
                } else if (map[i][j] == CHECK) {
                    checksCount++;
                }
            }
        }

        if (boxesCount != checksCount || playerCount == 0 || playerCount > 1) {
            System.out.println("Map have invalid game parameters");
        }

        checksPos = new int[2][checksCount];
        int checksQueue = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == CHECK) {
                    checksPos[0][checksQueue] = i;
                    checksPos[1][checksQueue] = j;
                    checksQueue++;
                }
            }
        }
    }

    private boolean isWon() {
        for (int i = 0; i < checksPos.length; i++) {
            int checkPosY = checksPos[0][i];
            int checkPosX = checksPos[1][i];
            if (map[checkPosY][checkPosX] != BOX) {
                return false;
            }
        }
        return true;
    }

    private void returnCheck() {
       for (int i = 0; i < checksPos.length; i++) {
           int checkPosY = checksPos[0][i];
           int checkPosX = checksPos[1][i];
           if (map[checkPosY][checkPosX] == SPACE) {
               map[checkPosY][checkPosX] = CHECK;
               break;
           }
       }
   }

    private void moveLeft() {
        if ((map[playerPosY][playerPosX - 1] == WALL)) {
            System.out.println("Impossible move to the left"); //debug
            return;
        }

        if (map[playerPosY][playerPosX - 1] == BOX && !canMoveBoxToLeft()) {
            return;
        }

        if (map[playerPosY][playerPosX - 1] == BOX) {
            map[playerPosY][playerPosX - 1] = SPACE;
            map[playerPosY][playerPosX - 2] = BOX;
        }

        map[playerPosY][playerPosX - 1] = PLAYER;
        map[playerPosY][playerPosX] = SPACE;
        playerPosX -= 1;
    }

    private void moveRight() {
        if ((map[playerPosY][playerPosX + 1] == WALL)) {
            System.out.println("Impossible move to the right"); //debug
            return;
        }

        if(map[playerPosY][playerPosX + 1] == BOX && !canMoveBoxToRight()) {
            return;
        }

        if(map[playerPosY][playerPosX + 1] == BOX) {
            map[playerPosY][playerPosX + 1] = SPACE;
            map[playerPosY][playerPosX + 2] = BOX;
        }

        map[playerPosY][playerPosX + 1] = PLAYER;
        map[playerPosY][playerPosX] = SPACE;
        playerPosX += 1;
    }

    private void moveTop() {
        if ((map[playerPosY - 1][playerPosX] == WALL)) {
            System.out.println("Impossible move to the top"); //debug
            return;
        }

        if(map[playerPosY - 1][playerPosX] == BOX && !canMoveBoxToTop()) {
            return;
        }

        if (map[playerPosY - 1][playerPosX] == BOX) {
            map[playerPosY - 1][playerPosX] = SPACE;
            map[playerPosY - 2][playerPosX] = BOX;
        }

        map[playerPosY - 1][playerPosX] = PLAYER;
        map[playerPosY][playerPosX] = SPACE;
        playerPosY -= 1;
    }

    private void moveBot() {
        if (map[playerPosY + 1][playerPosX] == WALL) {
            System.out.println("Impossible move to the bottom"); //debug
            return;
        }

        if(map[playerPosY + 1][playerPosX] == BOX && !canMoveBoxToBot()) {
            return;
        }

        if(map[playerPosY + 1][playerPosX] == BOX) {
            map[playerPosY + 1][playerPosX] = SPACE;
            map[playerPosY + 2][playerPosX] = BOX;
        }

        map[playerPosY + 1][playerPosX] = PLAYER;
        map[playerPosY][playerPosX] = SPACE;
        playerPosY += 1;
    }

    private boolean canMoveBoxToLeft() {
        if (((map[playerPosY][playerPosX - 2] == WALL) || (map[playerPosY][playerPosX - 2] == BOX)) && (playerPosX - 2 >= 0)) {
            System.out.println("Impossible move box to the left"); //debug
            return false;
        }
        return true;
    }

    private boolean canMoveBoxToRight() {
        if (((map[playerPosY][playerPosX + 2] == WALL) || (map[playerPosY][playerPosX + 2] == BOX)) && (playerPosX + 2 < map.length)) {
            System.out.println("Impossible move box to the right"); //debug
            return false;
        }
        return true;
    }

    private boolean canMoveBoxToTop() {
        if (((map[playerPosY - 2][playerPosX] == WALL) || (map[playerPosY - 2][playerPosX] == BOX)) && (playerPosY - 2 >= 0)) {
            System.out.println("Impossible move box to the top"); //debug
            return false;
        }
        return true;
    }

    private boolean canMoveBoxToBot() {
        if (((map[playerPosY + 2][playerPosX] == WALL) || (map[playerPosY + 2][playerPosX] == BOX)) && (playerPosY + 2 < map.length)) {
            System.out.println("Impossible move box to the bottom"); //debug
            return false;
        }
        return true;
    }
}
