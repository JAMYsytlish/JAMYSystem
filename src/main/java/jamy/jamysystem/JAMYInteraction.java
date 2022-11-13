package jamy.jamysystem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

import static jamy.jamysystem.JAMYInventory.ClockMenu;
import static jamy.jamysystem.JAMYMoney.getMoney;

public class JAMYInteraction implements Listener {


    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(ClockMenu)) {
            event.setCancelled(true);

            if(event.getRawSlot() == 13) {
                Player player = (Player) event.getWhoClicked();
                player.sendMessage(String.valueOf(getMoney(player.getName())));
                return;
            }
        }
    }
    public void onRightClickonClock(PlayerInteractEvent event) {
        if(Objects.equals(event.getItem(), new ItemStack(Material.CLOCK))) {
            event.getPlayer().openInventory(ClockMenu);
        }
    }
}
