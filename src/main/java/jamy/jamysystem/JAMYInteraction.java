package jamy.jamysystem;

import jamy.jamysystem.shop.JAMYShop;
import jamy.jamysystem.shop.ShopE;
import jamy.jamysystem.shop.ShopItemE;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

import static jamy.jamysystem.JAMYInventory.ClockMenu;
import static jamy.jamysystem.JAMYMoney.getMoney;

public class JAMYInteraction implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        JAMYPlayer player = new JAMYPlayer((Player) event.getWhoClicked());
        if (event.getInventory().equals(ClockMenu.getInventory())) {
            event.setCancelled(true);
            if(event.getAction() != InventoryAction.PICKUP_ALL) return;


            if(event.getRawSlot() == 13) {
                player.getPlayer().sendMessage("Your Money : " + String.valueOf(getMoney(player.getPlayer())));
            } else if(event.getRawSlot() == 15) {
                JAMYMoney.addMoney(player.getPlayer(), 10000 );
            }
        } else {
            if (event.getView().getTitle().contains(JAMYShop.shopTag)) {
                String shopName = event.getView().getTitle().split(" ")[1];
                JAMYShop shop = JAMYShop.getShop(shopName);

                int slot = event.getRawSlot();
                if (slot < 54) {
                    event.setCancelled(true);
                    if(event.getAction() == InventoryAction.PICKUP_ALL) {

                        if (slot < 36) { // 상품을 클릭했을 때
                            if (!shop.getType(ShopE.BUY).equals(false)) {
                                player.buy(shop, slot + 1);
                            }
                        }
                    } else if (event.getAction() == InventoryAction.PLACE_ALL || event.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
                        player.getPlayer().sendMessage("Sell attempt!!");
                    }
                } else {
                    if(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) { // shift 클릭으로 상점 인벤토리로 아이템을 옮길 때
                        player.getPlayer().sendMessage("Sell attempt!!");
                        event.setCancelled(true);
                        return;
                    }
                }

            }
        }
    }
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if(event.getView().getTitle().contains("[JAMYShop] ")) {
            for (Integer slot : event.getRawSlots()) {
                if (slot < 54) {
                    event.setCancelled(true);
                    return;
                }
            }

        }
    }
    @EventHandler
    public void onRightClickOnClock(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(Objects.equals(Objects.requireNonNull(event.getItem()).getType(), Material.CLOCK)) {
                event.getPlayer().openInventory(ClockMenu.getInventory());
            }
        }

    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(event.getView().getTitle().contains(JAMYShop.shopTag)){
            String shopName = event.getView().getTitle().split(" ")[1];
            JAMYShop shop = JAMYShop.getShop(shopName);
            shop.openList.remove(event.getPlayer());
            event.getPlayer().sendMessage("You have closed the shop of " + shopName);
        }
    }
}
