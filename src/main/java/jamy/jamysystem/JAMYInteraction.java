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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
            if (event.getView().getTitle().contains("[JAMYShop] ")) {
                String shopName = event.getView().getTitle().split(" ")[1];
                JAMYShop shop = JAMYShop.getShop(shopName);

                int slot = event.getRawSlot();
                if (slot < 54) {
                    event.setCancelled(true);
                    if(event.getAction() != InventoryAction.PICKUP_ALL) return;
                    if (slot < 36) { // 상품을 클릭했을 때
                        if(!shop.getType(ShopE.BUY).equals(false)) {
                            int price = (int) shop.getItemInfo(slot+1, ShopItemE.PRICE);
                            if(getMoney(player.getPlayer()) >= price) {
                                player.buy(shop,slot+1);
                            }

                        }
                    }
                }

            }
        }
    }
    @EventHandler
    public void onRightClickcOnClock(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(Objects.equals(Objects.requireNonNull(event.getItem()).getType(), Material.CLOCK)) {
                event.getPlayer().openInventory(ClockMenu.getInventory());
            }
        }

    }
}
