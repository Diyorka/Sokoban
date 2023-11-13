import java.util.HashMap;
import java.util.ArrayList;

public class Player {
    private String nickname;
    private HashMap<Integer, Integer> coinsOnLevels;
    private ArrayList<String> availableSkins;
    private int totalCoins;

    public Player(String nickname, HashMap<Integer, Integer> coinsOnLevels, ArrayList<String> availableSkins, int totalCoins) {
        this.nickname = nickname;
        this.coinsOnLevels = coinsOnLevels;
        this.availableSkins = availableSkins;
        this.totalCoins = totalCoins;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public HashMap<Integer, Integer> getCoinsOnLevels() {
        return coinsOnLevels;
    }

    public void setCoinsOnLevels(HashMap<Integer, Integer> coinsOnLevels) {
        this.coinsOnLevels = coinsOnLevels;
    }

    public ArrayList<String> getAvailableSkins() {
        return availableSkins;
    }

    public void setAvailableSkins(ArrayList<String> availableSkins) {
        this.availableSkins = availableSkins;
    }

    public int getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(int totalCoins) {
        this.totalCoins = totalCoins;
    }
}
