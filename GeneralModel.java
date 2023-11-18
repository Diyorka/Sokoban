public interface GeneralModel {
    String getMove();
    Player getPlayer();
    int getTotalMoves();
    int getCollectedCoins();
    int[][] getDesktop();
    void updateCurrentSkin(String skinType);
    // public void doAction(int keyMessage);

}
