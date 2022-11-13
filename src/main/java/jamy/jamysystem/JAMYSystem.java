package jamy.jamysystem;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import static jamy.jamysystem.JAMYInventory.setupClockMenu;

public final class JAMYSystem extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup
        getServer().getPluginManager().registerEvents(new JAMYInteraction(), this);
        setupClockMenu();
        System.out.println(ChatColor.BLUE+"Welcome To JAMYSystem!!");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
