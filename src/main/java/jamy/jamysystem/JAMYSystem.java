package jamy.jamysystem;

import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;


public final class JAMYSystem extends JavaPlugin {
    public static JAMYInventory ClockMenu = new JAMYInventory("ClockMenu", 5);
    @Override
    public void onEnable() {
        // Plugin startup
        getServer().getPluginManager().registerEvents(new JAMYInteraction(), this);
        ClockMenu.setupClockMenu();
        System.out.println(ChatColor.BLUE+"Welcome To JAMYSystem!!");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
