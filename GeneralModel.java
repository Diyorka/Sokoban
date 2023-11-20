public interface GeneralModel {
    String getMove();
    Player getPlayer();
    int getTotalMoves();
    int getCollectedCoins();
    int[][] getDesktop();

    String getNickName();
    void updateCurrentSkin(String skinType);
}
