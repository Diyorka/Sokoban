import java.io.File;

public class EnemyModel implements GeneralModel{

    private DBService dbService;
    private Player player;
    private Viewer viewer;
    private String nickName;

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
    private Client client;
    private boolean isEnemyCompletedGame;

    public EnemyModel(Viewer viewer) {
        this.viewer = viewer;
        dbService = new DBService();
        player = dbService.getPlayerInfo("Stive");
        levelList = new Levels(client);
        playerPosX = -1;
        playerPosY = -1;
        move = "Down";
        
    }

    public boolean getIsEnemyCompletedGame() {
        return isEnemyCompletedGame;
    }
    public void setClient(Client client) {
        levelList = new Levels(client);
        this.client = client;
    }

    public int[][] getDesktop(){
        return map;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    public void doAction(String action) {

        if (map == null) {
            System.out.println("NO MAP FOUND\n\n");
            return;
        }

        if (action.equals("Left")) {
            move = "Left";
            moveLeft();
        } else if(action.equals("Right")) {
            move = "Right";
            moveRight();
        } else if(action.equals("Up")) {
            move = "Up";
            moveTop();
        } else if(action.equals("Down")) {
            move = "Down";
            moveBot();
        } else if (action.equals("Given up")) {
            viewer.showEnemyGiveUpDialog();
        } else if (action.equals("You have 30 seconds")) {
            viewer.getMyCanvas().setTimer(client, viewer);
            viewer.updateMyCanvas();
            viewer.getModel().setIsPlayerFirstCompletedGame(false);
        } else if (action.equals("complete")) {
            System.out.println("MAKE isEnemyCompletedGame TRUE");
            isEnemyCompletedGame = true;
        }

        returnCheck();
        viewer.updateEnemyCanvas();

        System.out.println("Moves: " + totalMoves); //debug

    }

    public void changeLevel() {
        // initialize enemyMap
        System.out.println("initialize enemy Map [~]");
        map = levelList.getEnemyLevelFromServer();
        String nickNameAndSkin = client.getDataFromServer();
        String[] arrayNameSkin = nickNameAndSkin.split(";");
        nickName = arrayNameSkin[0];
        String skin = arrayNameSkin[1];
        setPlayer(nickName);
        updateCurrentSkin(skin);

        if (map != null) {
            scanMap();
            System.out.println("getting enemy map >>> ");
            for(int i = 0; i < map.length; i++) {
                for(int j = 0; j < map[i].length; j++) {
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
            viewer.showCanvas("battle");
        }

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

    public Player setPlayer(String nickname) {
        player = dbService.getPlayerInfo(nickname);
        viewer.updateEnemySkin();
        return player;
    }

    public Player getPlayer() {
        return player;
    }

    public void updateCurrentSkin(String skinType) {
        dbService.updateCurrentSkin(nickName, skinType);
        PlayerSkin skin = null;
        switch (skinType) {
            case "Default Skin":
                skin = new DefaultSkin();
                break;
            case "Santa Skin":
                skin = new SantaSkin();
                break;
            case "Premium Skin":
                skin = new PremiumSkin();
                break;
        }
        player.setCurrentSkin(skin);
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
        coinsCount = 0;
        collectedCoins = 0;
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
                collectedCoins++;
            }
            map[playerPosY][playerPosX - 1] = SPACE;

            if (map[playerPosY][playerPosX - 2] == CHECK) {

            }
            map[playerPosY][playerPosX - 2] = BOX;
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
            if(map[playerPosY][playerPosX + 2] == COIN) {
                collectedCoins++;
            }
            map[playerPosY][playerPosX + 1] = SPACE;

            if (map[playerPosY][playerPosX + 2] == CHECK) {

            }
            map[playerPosY][playerPosX + 2] = BOX;
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
            if(map[playerPosY - 2][playerPosX] == COIN) {
                collectedCoins++;
            }
            map[playerPosY - 1][playerPosX] = SPACE;

            if (map[playerPosY - 2][playerPosX] == CHECK) {

            }
            map[playerPosY - 2][playerPosX] = BOX;
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
            if(map[playerPosY + 2][playerPosX] == COIN) {
                collectedCoins++;
            }
            map[playerPosY + 1][playerPosX] = SPACE;

            if (map[playerPosY + 2][playerPosX] == CHECK) {

            }
            map[playerPosY + 2][playerPosX] = BOX;
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
