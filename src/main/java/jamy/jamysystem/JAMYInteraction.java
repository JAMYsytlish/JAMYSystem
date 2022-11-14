package jamy.jamysystem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

import static jamy.jamysystem.JAMYInventory.ClockMenu;
import static jamy.jamysystem.JAMYMoney.getMoney;

public class JAMYInteraction implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(ClockMenu.getInventory())) {
            event.setCancelled(true);
            if(event.getAction() != InventoryAction.PICKUP_ALL) {
               return;
            }
            Player player = (Player) event.getWhoClicked();
            if(event.getRawSlot() == 13) {
                player.sendMessage("Your Money : " + String.valueOf(getMoney(player)));
            } else if(event.getRawSlot() == 15) {
                JAMYMoney.addMoney(player, 10000 );
            }
        }
    }
    @EventHandler
    public void onRightClickonClock(PlayerInteractEvent event) {


        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(Objects.equals(Objects.requireNonNull(event.getItem()).getType(), Material.CLOCK)) {
                event.getPlayer().openInventory(ClockMenu.getInventory());
            }
        }

    }
}
