package jamy.jamysystem;

import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static jamy.jamysystem.JAMYInventory.ClockMenu;


public final class JAMYSystem extends JavaPlugin {

    public static File DataFolder;
    @Override
    public void onEnable() {
        // Plugin startup
        DataFolder = getDataFolder();
        getServer().getPluginManager().registerEvents(new JAMYInteraction(), this);
        getCommand("yamltest").setExecutor(new JAMYCommand());
        getCommand("JAMYShop").setExecutor(new JAMYCommand());
        ClockMenu.setupClockMenu();
        System.out.println(ChatColor.BLUE+"Welcome To JAMYSystem!!");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
