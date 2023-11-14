import java.io.File;

public class Model {
    private DBService dbService;
    private Player player;
    private Viewer viewer;

    private final int SPACE = 0;
    private final int PLAYER = 1;
    private final int WALL = 2;
    private final int BOX = 3;
    private final int CHECK = 4;
    private final int COIN = 5;

    private final int LEFT =  37; //arrow left keycode
    private final int RIGHT = 39; //arrow right keycode
    private final int UP = 38; //arrow up keycode
    private final int DOWN =  40; //arrow down keycode
    private final int RESTART = 82; //'r' button keycode
    private final int EXIT = 27; //'esc' button keycode

    private final Music boxInTargetSound;
    private final Music wonSound;

    private String move;
    private int playerPosX;
    private int playerPosY;

    private int[][] map;
    private Levels levelList;

    private int playerCount;
    private int boxesCount;
    private int checksCount;
    private int coinsCount;

    private int totalMoves = 0;
    private int collectedCoins;

    private int[][] checksPos;

    public Model(Viewer viewer) {
        this.viewer = viewer;
        dbService = new DBService();
        initPlayer("Stive");
        levelList = new Levels();
        wonSound = new Music(new File("music/won.wav"));
        boxInTargetSound = new Music(new File("music/target.wav"));
        playerPosX = -1;
        playerPosY = -1;
        move = "Down";
    }

    public int[][] getDesktop(){
        return map;
    }

    public void doAction(int keyMessage) {
        if (keyMessage == RESTART) {
            System.out.println("------------ Map restarted ------------\n\n");
            collectedCoins = 0;
            map = levelList.getCurrentMap();
            if (map != null) {
                scanMap();
            }
        } else if (keyMessage == EXIT) {
            collectedCoins = 0;
            viewer.showMenu();
        }

        if (map == null) {
            System.out.println("NO MAP FOUND\n\n");
            return;
        }

        if (keyMessage == LEFT) {
            move = "Left";
            moveLeft();
        } else if(keyMessage == RIGHT) {
            move = "Right";
            moveRight();
        } else if(keyMessage == UP) {
            move = "Up";
            moveTop();
        } else if(keyMessage == DOWN) {
            move = "Down";
            moveBot();
        }

        returnCheck();
        viewer.update();

        System.out.println("Moves: " + totalMoves); //debug

        if (isWon()) {
            boxInTargetSound.stop();
            wonSound.play();
            int passedLevel = levelList.getCurrentLevel();
            dbService.writeCoins(player.getNickname(), passedLevel, collectedCoins);
            showEndLevelDialog();
            collectedCoins = 0;
        }
    }

    public void changeLevel(String command) {
        String stringLevelNumber = command.substring(command.length() - 1, command.length());
        int levelNumber = Integer.parseInt(stringLevelNumber);
        levelList.setCurrentLevel(levelNumber);
        map = levelList.getCurrentMap();

        if (map != null) {
            scanMap();
        }

        viewer.showCanvas();
        totalMoves = 0;
    }

    public String getMove() {
        return move;
    }

    public Player initPlayer(String nickname) {
        player = dbService.getPlayerInfo(nickname);
        System.out.println(player.getNickname());
        System.out.println(player.getAvailableSkins());
        System.out.println(player.getTotalCoins());
        return player;
    }

    private void showEndLevelDialog() {
        Object[] options = {"Go to levels", "Next level"};
        int userChoise = javax.swing.JOptionPane.showOptionDialog(null, "                  You completed level " + levelList.getCurrentLevel() +
                                                                  "!\n                        Total moves: " + totalMoves, "Congratulations!",
                                                                  javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE,
                                                                  null, options, options[1]);
        if (userChoise == javax.swing.JOptionPane.NO_OPTION) {
            map = levelList.getNextMap();
            if (map != null) {
                scanMap();
            }
            viewer.update();
        } else if (userChoise == javax.swing.JOptionPane.YES_OPTION) {
            viewer.showLevelChooser();
            map = null;
        } else {
            viewer.showMenu();
            map = null;
        }
    }

    private void scanMap() {
        for (int i = 0; i < map.length - 1; i++) {
            int currentMapLineLength = map[i].length;
            int nextMapLineLength = map[i + 1].length;
            if (nextMapLineLength <= currentMapLineLength) {
                continue;
            }

            int nextMapLineLastElementOfCurrentLine = map[i + 1][map[i].length];
            int nextMapLineLastElement = map[i + 1][map[i + 1].length - 1];
            if ((nextMapLineLastElementOfCurrentLine == 0 || nextMapLineLastElement != 2)) {
                System.out.println("Map have invalid structure\n" + "Problem in mapline " + (i + 1));
                map = null;
                return;
            }
        }

        playerCount = 0;
        boxesCount = 0;
        checksCount = 0;
        totalMoves = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == PLAYER) {
                    playerPosX = j;
                    playerPosY = i;
                    playerCount++;
                } else if (map[i][j] == BOX) {
                    boxesCount++;
                } else if (map[i][j] == CHECK) {
                    checksCount++;
                } else if (map[i][j] == COIN) {
                    coinsCount++;
                }
            }
        }

        if (playerCount != 1 || boxesCount != checksCount || boxesCount == 0 && checksCount == 0) {
            System.out.println("Map have invalid game parameters");
            System.out.println("players: " + playerCount);
            System.out.println("boxes: " + boxesCount);
            System.out.println("checks: " + checksCount);
            map = null;
            return;
        }

        checksPos = new int[checksCount][2];
        int checksQueue = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == CHECK) {
                    checksPos[checksQueue][0] = i;
                    checksPos[checksQueue][1] = j;
                    checksQueue++;
                }
            }
        }
    }

    private boolean isWon() {
        for (int i = 0; i < checksPos.length; i++) {
            int checkPosY = checksPos[i][0];
            int checkPosX = checksPos[i][1];
            if (map[checkPosY][checkPosX] != BOX) {
                return false;
            }
        }
        return true;
    }

    private void returnCheck() {
       for (int i = 0; i < checksPos.length; i++) {
           int checkPosY = checksPos[i][0];
           int checkPosX = checksPos[i][1];
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

            if (map[playerPosY][playerPosX - 2] == CHECK) {
                boxInTargetSound.play();
            }
            map[playerPosY][playerPosX - 2] = BOX;
        }

        if (map[playerPosY][playerPosX - 1] == COIN) {
            collectedCoins++;
        }

        map[playerPosY][playerPosX - 1] = PLAYER;
        map[playerPosY][playerPosX] = SPACE;
        playerPosX -= 1;
        totalMoves++;
    }

    private void moveRight() {
        if ((map[playerPosY][playerPosX + 1] == WALL)) {
            System.out.println("Impossible move to the right"); //debug
            return;
        }

        if (map[playerPosY][playerPosX + 1] == BOX && !canMoveBoxToRight()) {
            return;
        }

        if (map[playerPosY][playerPosX + 1] == BOX) {
            map[playerPosY][playerPosX + 1] = SPACE;

            if (map[playerPosY][playerPosX + 2] == CHECK) {
                boxInTargetSound.play();
            }
            map[playerPosY][playerPosX + 2] = BOX;
        }

        if (map[playerPosY][playerPosX + 1] == COIN) {
            collectedCoins++;
        }

        map[playerPosY][playerPosX + 1] = PLAYER;
        map[playerPosY][playerPosX] = SPACE;
        playerPosX += 1;
        totalMoves++;
    }

    private void moveTop() {
        if ((map[playerPosY - 1][playerPosX] == WALL)) {
            System.out.println("Impossible move to the top"); //debug
            return;
        }

        if (map[playerPosY - 1][playerPosX] == BOX && !canMoveBoxToTop()) {
            return;
        }

        if (map[playerPosY - 1][playerPosX] == BOX) {
            map[playerPosY - 1][playerPosX] = SPACE;

            if (map[playerPosY - 2][playerPosX] == CHECK) {
                boxInTargetSound.play();
            }
            map[playerPosY - 2][playerPosX] = BOX;
        }

        if (map[playerPosY - 1][playerPosX] == COIN) {
            collectedCoins++;
        }

        map[playerPosY - 1][playerPosX] = PLAYER;
        map[playerPosY][playerPosX] = SPACE;
        playerPosY -= 1;
        totalMoves++;
    }

    private void moveBot() {
        if (map[playerPosY + 1][playerPosX] == WALL) {
            System.out.println("Impossible move to the bottom"); //debug
            return;
        }

        if (map[playerPosY + 1][playerPosX] == BOX && !canMoveBoxToBot()) {
            return;
        }

        if (map[playerPosY + 1][playerPosX] == BOX) {
            map[playerPosY + 1][playerPosX] = SPACE;

            if (map[playerPosY + 2][playerPosX] == CHECK) {
                boxInTargetSound.play();
            }
            map[playerPosY + 2][playerPosX] = BOX;
        }

        if (map[playerPosY + 1][playerPosX] == COIN) {
            collectedCoins++;
        }

        map[playerPosY + 1][playerPosX] = PLAYER;
        map[playerPosY][playerPosX] = SPACE;
        playerPosY += 1;
        totalMoves++;
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
