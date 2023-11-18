import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SettingsController implements ActionListener {

    private Model model;
    private Viewer viewer;
    private SettingsPanel settingsPanel;

    public SettingsController(SettingsPanel settingsPanel, Viewer viewer, Model model) {
        this.model = model;
        this.viewer = viewer;
        this.settingsPanel = settingsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        switch (command) {
            case "Default_Skin":
            case "Santa_Skin":
            case "Premium_Skin":
                String skinType = command.replace("_", " ");
                updateSkin(skinType);
                break;
            case "Buy_Premium":
                buyPremiumSkin();
                break;
            case "Default_Music":
                break;
            case "Christmas_Music":
                break;
            case "Back":
                viewer.showMenu();
                break;
        }
    }

    private void updateSkin(String skinType) {
        model.updateCurrentSkin(skinType);
        settingsPanel.updateButtonStates(skinType);
        viewer.updateSkin();
    }

    private void buyPremiumSkin() {
        if (!model.getPlayer().isPremiumAvailable()) {
            if (isCoinsEnough()) {
                model.buyPremiumSkin(settingsPanel.getPremiumSkinCost());
                viewer.updateSettings(model.getPlayer());
                settingsPanel.updatePremiumButtonText();
                updateSkin("Premium Skin");
            } else {
                settingsPanel.showNotEnoughCoinsMessage();
            }
        } else {
            updateSkin("Premium Skin");
        }
    }

    private boolean isCoinsEnough() {
        int totalCoins = model.getPlayer().getTotalCoins();
        int premiumSkinCost = settingsPanel.getPremiumSkinCost();
        return totalCoins >= premiumSkinCost;
    }

}
