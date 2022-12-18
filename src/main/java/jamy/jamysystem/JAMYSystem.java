package jamy.jamysystem;

import jamy.jamysystem.commander.JAMYCommander;
import jamy.jamysystem.commander.JAMYShopAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static jamy.jamysystem.JAMYInventory.ClockMenu;


public final class JAMYSystem extends JavaPlugin {
    // font : Office Code Pro  , Theme : Catppuccin frappe
    public static File DataFolder;

    @Override
    public void onEnable() {
        // Plugin startup
        DataFolder = getDataFolder();
        Bukkit.getPluginManager().registerEvents(new JAMYInteraction(), this);
        Bukkit.getPluginManager().registerEvents(new JAMYCommander(), this);
        Bukkit.getPluginManager().registerEvents(new JAMYShopAction(), this);
        getCommand("test").setExecutor(new Test());
        getCommand("JAMYShop").setExecutor(new JAMYCommand());
        ClockMenu.setupClockMenu();
        JAMYCommander.commanderInit();
        JAMYShopAction.shopActionInventoryInit();
        System.out.println(ChatColor.BLUE + "Welcome To JAMYSystem!!");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
