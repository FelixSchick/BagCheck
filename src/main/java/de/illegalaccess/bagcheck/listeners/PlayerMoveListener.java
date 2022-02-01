package de.illegalaccess.bagcheck.listeners;

import de.illegalaccess.bagcheck.utils.BagCheckManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class PlayerMoveListener implements Listener {
    //Cancle Task if distance to checker >= 3
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        BagCheckManager bagCheckManager = new BagCheckManager();
        HashMap<Player, Player> checked = BagCheckManager.getChecked();
        if (checked.containsValue(event.getPlayer())) {
            for (Player checker : checked.keySet()){
                if (checked.get(checker) == event.getPlayer())
                    if (event.getPlayer().getLocation().distance(checker.getLocation()) >= 3)
                        bagCheckManager.cancleCheckPlayer(event.getPlayer());
            }
        }
    }
}
