import java.util.Map;
import java.util.HashMap;

public class SkinFactory {

    private Map<String, PlayerSkin> skins;

    public SkinFactory() {
        skins = new HashMap<String, PlayerSkin>();
        initSkins();
    }

    public PlayerSkin getPlayerSkin(String skinType) {
        return skins.get(skinType);
    }

    private void initSkins() {
        skins.put("Default Skin", new DefaultSkin());
        skins.put("Santa Skin", new SantaSkin());
        skins.put("Premium Skin", new PremiumSkin());
    }
}
