package me.nikl.gamebox.commands.admin;

import co.aikar.commands.annotation.PreCommand;
import me.nikl.gamebox.GameBox;
import me.nikl.gamebox.Module;
import me.nikl.gamebox.commands.GameBoxBaseCommand;
import me.nikl.gamebox.utility.Permission;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Niklas Eicker
 */
public abstract class GameAdminCommand extends GameBoxBaseCommand {
    private Module module;

    public GameAdminCommand(GameBox gameBox, Module module) {
        super(gameBox);
        Validate.notNull(module, "The GameAdminCommand needs a valid module!");
        this.module = module;
        gameBox.getCommands().registerCommand(this);
    }

    @Override
    @PreCommand
    public boolean preCommand(CommandSender sender) {
        GameBox.debug("in GameAdminCommand pre command");
        if (!Permission.ADMIN_GAME.hasPermission(sender, module)) {
            sender.sendMessage(gameBox.lang.PREFIX + gameBox.lang.CMD_NO_PERM);
            return true;
        }
        if (sender instanceof Player) {
            sender.sendMessage(gameBox.lang.PREFIX + " Only from the console!");
            return true;
        }
        return false;
    }
}
