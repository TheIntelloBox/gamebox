package me.nikl.gamebox.games.cookieclicker.upgrades.alchemylab;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;

/**
 * Created by Niklas on 09.07.2017.
 *
 */
public class AquaCrustulae extends Upgrade{

    public AquaCrustulae(Game game) {
        super(game, 115);
        this.cost = 375000000000000000.;
        productionsRequirements.put(Buildings.ALCHEMY_LAB, 100);

        loadLanguage(UpgradeType.CLASSIC, Buildings.ALCHEMY_LAB);
    }

    @Override
    public void onActivation() {
        game.getBuilding(Buildings.ALCHEMY_LAB).multiply(2);
        game.getBuilding(Buildings.ALCHEMY_LAB).visualize(game.getInventory());
        active = true;
    }


}
