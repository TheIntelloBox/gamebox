package me.nikl.gamebox.games.cookieclicker.upgrades.factory;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * Created by Niklas on 09.07.2017.
 *
 * Custom upgrade replacing 'Sweatshop'
 */
public class Sweatshop extends Upgrade{

    public Sweatshop(Game game) {
        super(game, 15);
        this.cost = 65000000;
        productionsRequirements.put(Buildings.FACTORY, 25);

        icon = new MaterialData(Material.IRON_BLOCK).toItemStack();
        icon.setAmount(1);

        loadLanguage(UpgradeType.CLASSIC, Buildings.FACTORY);
    }

    @Override
    public void onActivation() {
        game.getBuilding(Buildings.FACTORY).multiply(2);
        game.getBuilding(Buildings.FACTORY).visualize(game.getInventory());
        active = true;
    }
}
