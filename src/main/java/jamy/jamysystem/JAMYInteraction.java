package jamy.jamysystem;

import jamy.jamysystem.item.JAMYItem;
import jamy.jamysystem.shop.JAMYShop;
import jamy.jamysystem.shop.ShopE;
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
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static jamy.jamysystem.JAMYInventory.ClockMenu;
import static jamy.jamysystem.JAMYMoney.getMoney;
import static jamy.jamysystem.shop.ShopE.SELL;

public class JAMYInteraction implements Listener {

    private static void register(JAMYShop shop, ItemStack item) {
        if (shop.getType(SELL).equals(false)) return; // 판매상점에만 등록 가능
        if (shop.exists(new ItemStack(item.getType())) != -1) return;
        int amount = Integer.valueOf(item.getItemMeta().getDisplayName().split(" ")[0].replaceAll("[§rfl개]", ""));
        ItemStack itemStack = JAMYItem.getItem(item.getType(), amount);
        List<String> lore = item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : new ArrayList<>();
        int loreSize = lore.size();
        int price = Integer.parseInt(lore.get(loreSize - 3).replaceAll("[§f판매 가격:e,]", ""));
        shop.registerItem(itemStack, price, false, 0);
        item.setAmount(item.getAmount() - 1);
        for (Player openPlayer : shop.openList) {
            shop.loadInventory(openPlayer.getOpenInventory().getTopInventory(), openPlayer);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        JAMYPlayer player = new JAMYPlayer((Player) event.getWhoClicked());
        if (event.getInventory().equals(ClockMenu.getInventory())) {
            event.setCancelled(true);
            if (event.getAction() != InventoryAction.PICKUP_ALL) return;

            final int MONEY_SLOT = 13;
            final int ADD_MONEY_SLOT = 15;

            if (event.getRawSlot() == MONEY_SLOT) {
                player.getPlayer().sendMessage("Your Money : " + NumberFormat.getInstance().format(getMoney(player.getPlayer())));
            } else if (event.getRawSlot() == ADD_MONEY_SLOT) {
                JAMYMoney.addMoney(player.getPlayer(), 10000);
            }
        } else {
            if (event.getView().getTitle().contains(JAMYShop.shopTag)) {
                String shopName = event.getView().getTitle().split(" ")[1];
                JAMYShop shop = JAMYShop.getShop(shopName);

                int slot = event.getRawSlot();
                if (slot < 54) {
                    event.setCancelled(true);
                    if (event.getAction() == InventoryAction.PICKUP_ALL) {

                        if (slot < 36) { // 상품을 클릭했을 때
                            if (!shop.getType(ShopE.BUY).equals(false)) {
                                player.buy(shop, slot);
                            }
                        }
                    } else if (event.getAction() == InventoryAction.PLACE_ALL || event.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
//                        player.getPlayer().sendMessage("Sell attempt!!");
                        event.setCancelled(true);
                        ItemStack itemOnCursor = event.getCursor();
                        sellOrRegister(event, itemOnCursor, player, shop);
                    }
                } else {
                    if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) { // shift 클릭으로 상점 인벤토리로 아이템을 옮길 때
//                        player.getPlayer().sendMessage("Sell attempt!!");
                        event.setCancelled(true);
                        ItemStack itemOnClickedSlot = event.getCurrentItem();
                        sellOrRegister(event, itemOnClickedSlot, player, shop);
                    }
                }

            }
        }
    }

    private void sellOrRegister(InventoryClickEvent event, ItemStack attemptItem, JAMYPlayer player, JAMYShop shop) {
        int sellAmount = player.sell(shop, attemptItem);
        if (sellAmount == -1) {
            if (attemptItem.getItemMeta().getDisplayName().contains("§6판매권")) {
                player.getPlayer().sendMessage("Register attempt!!");
                register(shop, attemptItem);
                return;
            } else {
                sellAmount = 0;
            }
        }
        attemptItem.setAmount(attemptItem.getAmount() - sellAmount);
        shop.loadInventory(event.getInventory(), player.getPlayer());
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getView().getTitle().contains("[JAMYShop] ")) {
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
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getType().equals(Material.CLOCK)) {
                event.getPlayer().openInventory(ClockMenu.getInventory());
            }
        }

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().contains(JAMYShop.shopTag)) {
            String shopName = event.getView().getTitle().split(" ")[1];
            JAMYShop shop = JAMYShop.getShop(shopName);
            shop.openList.remove(event.getPlayer());
            event.getPlayer().sendMessage("You have closed the shop of " + shopName);
        }
    }
}
