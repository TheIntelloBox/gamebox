package me.nikl.gamebox.inventory.shop;

import me.nikl.gamebox.GameBox;
import me.nikl.gamebox.GameBoxSettings;
import me.nikl.gamebox.PluginManager;
import me.nikl.gamebox.data.GBPlayer;
import me.nikl.gamebox.inventory.GUIManager;
import me.nikl.gamebox.inventory.button.AButton;
import me.nikl.gamebox.inventory.gui.AGui;
import me.nikl.gamebox.inventory.ClickAction;
import me.nikl.gamebox.utility.InventoryUtility;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author Niklas Eicker
 *
 * class to extend upon for shop GUIs
 */
public class Shop extends AGui{
    FileConfiguration shop;

    ShopManager shopManager;

    protected Map<UUID, AButton> tokenButtons = new HashMap<>();

    protected int tokenButtonSlot;

    public Shop(GameBox plugin, GUIManager guiManager, int slots, ShopManager shopManager, String[] args, String title) {
        super(plugin, guiManager, slots, args, title);
        this.shopManager = shopManager;
        this.shop = shopManager.getShop();

        Map<Integer, ItemStack> hotBarButtons = plugin.getPluginManager().getHotBarButtons();


        // set lower grid
        if (hotBarButtons.get(PluginManager.exitButtonSlot) != null) {
            AButton exit = new AButton(hotBarButtons.get(PluginManager.exitButtonSlot));
            ItemMeta meta = hotBarButtons.get(PluginManager.exitButtonSlot).getItemMeta();
            exit.setItemMeta(meta);
            exit.setAction(ClickAction.CLOSE);
            setLowerButton(exit, PluginManager.exitButtonSlot);
        }


        if (hotBarButtons.get(PluginManager.toMainButtonSlot) != null) {
            AButton main = new AButton(hotBarButtons.get(PluginManager.toMainButtonSlot));
            ItemMeta meta = hotBarButtons.get(PluginManager.toMainButtonSlot).getItemMeta();
            main.setItemMeta(meta);
            main.setAction(ClickAction.OPEN_MAIN_GUI);
            setLowerButton(main, PluginManager.toMainButtonSlot);
        }


        tokenButtonSlot = slots - 9;

        if(GameBoxSettings.tokensEnabled) {
            // set a placeholder in the general main gui
            AButton tokens = guiManager.getTokenButton();
            ItemMeta meta = tokens.getItemMeta();
            meta.setDisplayName("Placeholder");
            tokens.setItemMeta(meta);
            setButton(tokens, tokenButtonSlot);
        }
    }



    @Override
    public boolean open(Player player){
        if(!openInventories.containsKey(player.getUniqueId())){
            loadPlayerShop(pluginManager.getPlayer(player.getUniqueId()));
        }
        return super.open(player);
    }

    void loadPlayerShop(GBPlayer player){

        if(GameBoxSettings.tokensEnabled) {
            AButton tokens = guiManager.getTokenButton();
            tokenButtons.put(player.getUuid(), tokens);
        }

        Inventory inventory = InventoryUtility.createInventory(null, this.inventory.getSize(), "GameBox gui");
        inventory.setContents(this.inventory.getContents().clone());

        openInventories.putIfAbsent(player.getUuid(),inventory);

        updateTokens(player);
    }

    void updateTokens(GBPlayer player) {
        if(!GameBoxSettings.tokensEnabled) return;
        if(!tokenButtons.keySet().contains(player.getUuid())) return;
        if(!openInventories.keySet().contains(player.getUuid())) return;

        ItemMeta meta = tokenButtons.get(player.getUuid()).getItemMeta();
        meta.setDisplayName(plugin.lang.BUTTON_TOKENS.replace("%tokens%", String.valueOf(player.getTokens())));
        tokenButtons.get(player.getUuid()).setItemMeta(meta);

        openInventories.get(player.getUuid()).setItem(tokenButtonSlot, tokenButtons.get(player.getUuid()));
    }

    @Override
    public void removePlayer(UUID uuid) {
        tokenButtons.remove(uuid);
        super.removePlayer(uuid);
    }
}