package jamy.jamysystem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

import static jamy.jamysystem.JAMYMoney.getMoney;
import static jamy.jamysystem.JAMYSystem.ClockMenu;

public class JAMYInteraction implements Listener {


    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(ClockMenu.getInventory())) {
            event.setCancelled(true);

            if(event.getRawSlot() == 13) {
                Player player = (Player) event.getWhoClicked();
                player.sendMessage(String.valueOf(getMoney(player.getName())));
                return;
            }
        }
    }
    public void onRightClickonClock(PlayerInteractEvent event) {
        event.getPlayer().sendMessage("1");


        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            event.getPlayer().sendMessage("1");
            if(Objects.equals(event.getItem(), new ItemStack(Material.CLOCK))) {
                event.getPlayer().sendMessage("1");
                event.getPlayer().openInventory(ClockMenu.getInventory());
            }
        }

    }
}
