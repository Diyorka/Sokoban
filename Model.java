import java.io.File;

public class Model implements GeneralModel {
    private DBService dbService;
    private Player player;
    private Viewer viewer;
    private Client client;
    private String gameType;

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

    private final int LAST_LEVEL = 9;

    private final Music boxInTargetSound;
    private final Music wonSound;
    private final Music moveSnowSound;
    private final Music backgroundSnowMusic;
    private final Music coinSound;

    private String move;
    private int playerPosX;
    private int playerPosY;

    private int[][] map;
    private Levels levelList;

    private int playerCount;
    private int boxesCount;
    private int checksCount;
    private int coinsCount;

    private int totalMoves;
    private int collectedCoins;

    private int[][] checksPos;
    private int[][] coinsPos;


    public Model(Viewer viewer) {
        this.viewer = viewer;
        dbService = new DBService();
        initPlayer("Stive");
        // levelList = new Levels(client);

        wonSound = new Music(new File("music/won.wav"));
        boxInTargetSound = new Music(new File("music/target.wav"));
        moveSnowSound = new Music(new File("music/move_snow.wav"));
        coinSound = new Music(new File("music/coin.wav"));

        backgroundSnowMusic = new Music(new File("music/backgroundSnowMusic.wav"));
        // backgroundSnowMusic.play();

        playerPosX = -1;
        playerPosY = -1;
        move = "Down";
    }

    public void setClient(Client client) {
        levelList = new Levels(client);
        this.client = client;
        gameType = client.getGameType();
    }
    public Client getClient() {
        return client;
    }

    public int[][] getDesktop(){
        return map;
    }

    public void doAction(int keyMessage) {
        System.out.println("in model do Action");
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
            System.out.println("Left");
            for(int i = 0; i < map.length; i++) {
                for(int j = 0; j < map[i].length; j++) {
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        } else if(keyMessage == RIGHT) {
            move = "Right";
            moveRight();
            System.out.println("Right");
            for(int i = 0; i < map.length; i++) {
                for(int j = 0; j < map[i].length; j++) {
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        } else if(keyMessage == UP) {
            move = "Up";
            moveTop();
            System.out.println("Up");
            for(int i = 0; i < map.length; i++) {
                for(int j = 0; j < map[i].length; j++) {
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        } else if(keyMessage == DOWN) {
            move = "Down";
            moveBot();
            System.out.println("Down");
            for(int i = 0; i < map.length; i++) {
                for(int j = 0; j < map[i].length; j++) {
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }

        returnCheck();
        viewer.update();// when play alone
        viewer.updateMyCanvas();// when play with enemy

        System.out.println("Moves: " + totalMoves); //debug

        if (isWon()) {
            moveSnowSound.stop();
            boxInTargetSound.stop();
            wonSound.play();
            int passedLevel = levelList.getCurrentLevel();
            dbService.writeCoins(player.getNickname(), passedLevel, collectedCoins);
            collectedCoins = 0;
            if (gameType.equals("alone") && levelList.getCurrentLevel() == LAST_LEVEL) {
                showEndGameDialog();
            } else if (gameType.equals("alone")) {
                showEndLevelDialog();
            } else if (gameType.equals("battle")){
                showWonDialog();
            }
        }

    }

    public void changeLevel(String command) {

        String stringLevelNumber = command.substring(command.length() - 1, command.length());
        int levelNumber = Integer.parseInt(stringLevelNumber);
        levelList.setCurrentLevel(levelNumber);
        if(gameType.equals("alone")) {
            map = levelList.getCurrentMap();// map for current our model
        }
        if(gameType.equals("battle")) {
            map = levelList.getLevelFromServer(String.valueOf(levelNumber));
        }


        if (map != null) {
            scanMap();
            System.out.println("getting our map >>>");
            for(int i = 0; i < map.length; i++) {
                for(int j = 0; j < map[i].length; j++) {
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }
        }
        System.out.println();


        viewer.showCanvas(gameType);
        totalMoves = 0;

    }


    public String getMove() {
        return move;
    }

    public int getTotalMoves() {
        return totalMoves;
    }

    public int getCollectedCoins() {
        return collectedCoins;
    }

    public Player initPlayer(String nickname) {
        player = dbService.getPlayerInfo(nickname);
        System.out.println(player.getNickname());
        System.out.println(player.getAvailableSkins());
        System.out.println(player.getTotalCoins());
        return player;
    }

    public Player getPlayer() {
        return player;
    }

    public void getNextLevel() {
        map = levelList.getNextMap();
        if (map != null) {
            scanMap();
        }
        viewer.showCanvas();
    }

    public void updateCurrentSkin(String skin) {
        dbService.updateCurrentSkin(player.getNickname(), skin);
    }

    public void showEndLevelDialog() {
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

    public void showWonDialog() {
        String[] options = {"Wait other player", "Return"};
        int result = javax.swing.JOptionPane.showOptionDialog(
                null, player.getNickname() + " won! Congratulations", "Total moves: " + totalMoves,
                javax.swing.JOptionPane.DEFAULT_OPTION, javax.swing.JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]
        );
        switch (result) {
            case 0:
                System.out.println("Wait option selected");
                break;
            case 1:
                System.out.println("Return option selected");
                break;
        }
    }
    public void showEndGameDialog() {
        String[] options = {"Exit to Menu"};
        int result = javax.swing.JOptionPane.showOptionDialog(
            null, player.getNickname() + " won! You have passed all levels ! Congratulations", "Total moves: " + totalMoves,
            javax.swing.JOptionPane.DEFAULT_OPTION, javax.swing.JOptionPane.INFORMATION_MESSAGE,
            null, options, options[0]
            );
        if (result == 0) {
            System.out.println("Exit to Menu option selected");
            client.closeClient();
            viewer.showMenu();
        } else {
            client.closeClient();
            viewer.showMenu();
            map = null;
        }
    }
    private void scanMap() {
        deleteMapValues();

        if (!(isLeftWallsCorrect() && isRightWallsCorrect())) {
            return;
        }

        setMapValues();

        if (!isMapPlayable()) {
            return;
        }

        saveChecksPos();
        saveCoinsPos();
    }

    private void deleteMapValues() {
        playerCount = 0;
        boxesCount = 0;
        checksCount = 0;
        totalMoves = 0;
        coinsCount = 0;
        collectedCoins = 0;
    }

    private void setMapValues() {
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
    }

    private boolean isLeftWallsCorrect(){
        int wallX = -1;
        int wallY = -1;
        int prevWallX = -1;
        int prevWallY = -1;
        for(int i = 0; i < map.length; i++) {
            boolean wallFound = false;
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 2) {
                    wallFound = true;
                    wallY = i;
                    wallX = j;
                    break;
                }
            }
            if (i != 0) {
                int differenceBetweenWalls = Math.abs(prevWallX - wallX);
                if (prevWallX > wallX) {
                    for (int k = prevWallX; k > prevWallX - differenceBetweenWalls; k--) {
                        if (map[wallY][k] != 2) {
                            System.out.println("isLeftWallsCorrect(): problem in a mapline" + i + "\n(prevWallX>wallX)");
                            map = null;
                            return false;
                        }
                    }
                }
                if (wallX > prevWallX) {
                    for (int k = prevWallX; k < wallX - 1; k++) {
                        if (map[prevWallY][k] != 2) {
                            System.out.println("isLeftWallsCorrect(): problem in a mapline" + i + "\n(wallX>prevWallX)");
                            map = null;
                            return false;
                        }
                    }
                }
            }
            prevWallY = wallY;
            prevWallX = wallX;
            wallFound = false;
        }
        return true;
    }

    private boolean isRightWallsCorrect() {
        for (int i = 0; i < map.length - 1; i++) {
            int currentMapLineLength = map[i].length;
            int nextMapLineLength = map[i + 1].length;
            if (nextMapLineLength <= currentMapLineLength) {
                continue;
            }

            int nextMapLineLastElementOfCurrentLine = map[i + 1][map[i].length];
            int nextMapLineLastElement = map[i + 1][map[i + 1].length - 1];
            if ((nextMapLineLastElementOfCurrentLine == 0 || nextMapLineLastElement != 2)) {
                System.out.println("isRightWallsCorrect(): problems with element in mapline " + (i + 1));
                map = null;
                return false;
            }
        }
        return true;
    }

    private boolean isMapPlayable() {
        if (playerCount != 1 || boxesCount != checksCount || boxesCount == 0 && checksCount == 0) {
            System.out.println("isMapPlayable(): map have invalid game parameters");
            System.out.println("players: " + playerCount + ("(should be equal to 1)"));
            System.out.println("boxes: " + boxesCount);
            System.out.println("checks: " + checksCount);
            map = null;
            return false;
        }
        return true;
    }

    private void saveChecksPos() {
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

    private void saveCoinsPos() {
        coinsPos = new int[coinsCount][2];
        int coinsQueue = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == COIN) {
                    coinsPos[coinsQueue][0] = i;
                    coinsPos[coinsQueue][1] = j;
                    coinsQueue++;
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

       for (int i = 0; i < coinsPos.length; i++) {
           int coinsPosY = coinsPos[i][0];
           int coinsPosX = coinsPos[i][1];
           boolean coinsValid = coinsPosY != -1 && coinsPosX != -1;

           if (coinsValid && map[coinsPosY][coinsPosX] == SPACE) {
               map[coinsPosY][coinsPosX] = COIN;
               break;
           } else if (coinsValid && map[coinsPosY][coinsPosX] == BOX) {
              coinsPos[i][0] = -1;
              coinsPos[i][1] = -1;
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
            if(map[playerPosY][playerPosX - 2] == COIN) {
                coinSound.play();
                collectedCoins++;
            }
            map[playerPosY][playerPosX - 1] = SPACE;

            if (map[playerPosY][playerPosX - 2] == CHECK) {
                boxInTargetSound.play();
            }
            map[playerPosY][playerPosX - 2] = BOX;
        }

        if(gameType.equals("battle")) {
            System.out.println("Left");
            client.sendDataToServer("Left");
        }
        moveSnowSound.play();
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
            if(map[playerPosY][playerPosX + 2] == COIN) {
                coinSound.play();
                collectedCoins++;
            }
            map[playerPosY][playerPosX + 1] = SPACE;

            if (map[playerPosY][playerPosX + 2] == CHECK) {
                boxInTargetSound.play();
            }
            map[playerPosY][playerPosX + 2] = BOX;
        }
        if(gameType.equals("battle")) {
            System.out.println("Right");
            client.sendDataToServer("Right");
        }
        moveSnowSound.play();
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
            if(map[playerPosY - 2][playerPosX] == COIN) {
                coinSound.play();
                collectedCoins++;
            }
            map[playerPosY - 1][playerPosX] = SPACE;

            if (map[playerPosY - 2][playerPosX] == CHECK) {
                boxInTargetSound.play();
            }
            map[playerPosY - 2][playerPosX] = BOX;
        }
        if(gameType.equals("battle")) {
            System.out.println("Up");
            client.sendDataToServer("Up");
        }
        moveSnowSound.play();
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
            if(map[playerPosY + 2][playerPosX] == COIN) {
                coinSound.play();
                collectedCoins++;
            }
            map[playerPosY + 1][playerPosX] = SPACE;

            if (map[playerPosY + 2][playerPosX] == CHECK) {
                boxInTargetSound.play();
            }
            map[playerPosY + 2][playerPosX] = BOX;
        }
        if(gameType.equals("battle")) {
            System.out.println("Down");
            client.sendDataToServer("Down");
        }
        moveSnowSound.play();
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
        if (((map[playerPosY][playerPosX + 2] == WALL) || (map[playerPosY][playerPosX + 2] == BOX)) && (playerPosX + 2 < map[playerPosY].length)) {
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
        if (((map[playerPosY + 2][playerPosX] == WALL) || (map[playerPosY + 2][playerPosX] == BOX)) && (playerPosY + 2 < map[playerPosX].length)) {
            System.out.println("Impossible move box to the bottom"); //debug
            return false;
        }
        return true;
    }
}
