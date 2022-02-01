package de.illegalaccess.bagcheck.utils;

import de.illegalaccess.bagcheck.BagCheck;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;

public class BagCheckManager {
                    //P1: checker   P2: target
    private static HashMap<Player, Player> checked = new HashMap<>();
                    //P1: checker //int: rest time
    private static HashMap<Player, Integer> timer = new HashMap<>();

    public void checkPlayer(Player checker, Player target) {
        if (!(checked.containsValue(target) && checked.containsValue(checker))) {
            checked.put(checker, target);
            timer.put(checker, 4);

            final int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(BagCheck.getPlugin(BagCheck.class), new Runnable() {
                @Override
                public void run() {
                    if (checked.containsValue(target) && checked.containsKey(checker)) {
                        if (timer.containsKey(checker)) {
                            if (timer.get(checker) != 0){
                                timer.replace(checker, timer.get(checker), timer.get(checker) - 1);
                                checker.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§eDurchsuchung dauert noch §f§l" + timer.get(checker) + " §eSekunden"));
                                target.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§eDurchsuchung dauert noch §f§l" + timer.get(checker) + " §eSekunden"));
                            } else {
                                cancleCheckPlayer(target);
                                checker.openInventory(target.getInventory());
                            }
                        }
                    }
                }
            }, 0 , 20);
            Bukkit.getScheduler().scheduleSyncDelayedTask(BagCheck.getPlugin(BagCheck.class), new Runnable() {
                public void run() {
                    Bukkit.getScheduler().cancelTask(task);
                }
            }, 21*4);
        } else
            checker.sendMessage("§cDu durchsuchtst bereits jemanden.");
    }

    public void cancleCheckPlayer(Player target) {
        if (checked.containsValue(target)) {
            for (Player checker : checked.keySet()){
                if (checked.get(checker) == target) {
                    checked.remove(checker);
                    timer.remove(checker);

                    checker.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cDurchsuchung abgebrochen!"));
                    target.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cDurchsuchung abgebrochen!"));
                }
            }
        }
    }

    public static HashMap<Player, Player> getChecked() {
        return checked;
    }
}
