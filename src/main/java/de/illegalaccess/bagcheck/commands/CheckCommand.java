package de.illegalaccess.bagcheck.commands;

import de.illegalaccess.bagcheck.utils.BagCheckManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class CheckCommand implements CommandExecutor {
    BagCheckManager bagCheckManager;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        bagCheckManager = new BagCheckManager();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("checkBag.check")) {
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (target != player)
                        if (player.getLocation().distance(target.getLocation()) <= 3) {
                            bagCheckManager.checkPlayer(player, target);
                        }
                }
            } else
                player.sendMessage("§cDazu hast du keine Permissions.");
        }
        return false;
    }
}