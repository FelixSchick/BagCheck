package de.illegalaccess.bagcheck;

import de.illegalaccess.bagcheck.commands.CheckCommand;
import de.illegalaccess.bagcheck.listeners.PlayerMoveListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BagCheck extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("check").setExecutor(new CheckCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
