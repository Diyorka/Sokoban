public interface GeneralModel {
    public String getMove();
    public int getTotalMoves();
    public int getCollectedCoins();
    public int[][] getDesktop();
    public void doAction(int keyMessage);

}
