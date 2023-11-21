import java.util.HashMap;
import java.util.ArrayList;

public class Player {
    private String nickname;
    private HashMap<Integer, Integer> coinsOnLevels;
    private ArrayList<String> availableSkins;
    private PlayerSkin currentSkin;
    private int totalCoins;

    public Player(String nickname, HashMap<Integer, Integer> coinsOnLevels, ArrayList<String> availableSkins, PlayerSkin currentSkin, int totalCoins) {
        this.nickname = nickname;
        this.coinsOnLevels = coinsOnLevels;
        this.availableSkins = availableSkins;
        this.currentSkin = currentSkin;
        this.totalCoins = totalCoins;
    }

    public boolean isPremiumAvailable() {
        ArrayList<String> availableSkins = getAvailableSkins();

        for (String skin : availableSkins) {
            if ("Premium Skin".equals(skin)) {
                return true;
            }
        }

        return false;
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

    public PlayerSkin getCurrentSkin() {
        return currentSkin;
    }

    public void setCurrentSkin(PlayerSkin currentSkin) {
        this.currentSkin = currentSkin;
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
