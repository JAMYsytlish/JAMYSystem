package jamy.jamysystem;

import jamy.jamysystem.shop.JAMYShop;
import jamy.jamysystem.shop.ShopItemE;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static jamy.jamysystem.shop.ShopE.BUY;
import static jamy.jamysystem.shop.ShopItemE.PRICE;

public class JAMYPlayer {

    private final Player player;

    public JAMYPlayer(Player player) {
        this.player = player;
    }

    public void sendMoney(Player receiver, Integer amount) {

        int MoneyGiver = JAMYMoney.getMoney(this.player);

        if (MoneyGiver < amount) {
            this.player.sendMessage("Not enough money.");
        } else {
            JAMYMoney.subMoney(this.player, amount);
            JAMYMoney.addMoney(receiver, amount);
        }

    }

    public Player getPlayer() {
        return this.player;
    }

    // (JAMYPlayer).buy(shop, 3) : JAMYPlayer buys 3 index of products in 'shop'
    public void buy(JAMYShop shop, int index) {
        if (JAMYMoney.getMoney(this.player) < (int) shop.getItemInfo(index,PRICE)) {
            this.player.sendMessage("You don't have enough money");
        } else {
            if (!this.hasSpace((ItemStack) shop.getItemInfo(index, ShopItemE.VALUE))) {
                this.player.sendMessage("Your Inventory doesn't have space");
            } else {
                if ((boolean) shop.getType(BUY)) {
                    this.player.getInventory().addItem((ItemStack) shop.getItemInfo(index, ShopItemE.VALUE));
                    JAMYMoney.subMoney(this.player,(int) shop.getItemInfo(index,PRICE));
                    shop.loadInventory(this.player.getOpenInventory().getTopInventory(),this.player);

                }
            }   
        }
 
    }


    public boolean hasSpace(ItemStack item) {
        final ItemStack[] contents = this.player.getInventory().getContents();
        int amount = item.getAmount();
        for (final ItemStack stack : contents) {
            if (stack == null || stack.getType() == Material.AIR) {
                if (amount <= 64) {
                    amount = 0;
                    break;
                } else amount -= 64;
            } else {
                if (!stack.isSimilar(item)) continue;
                final int diff = 64 - stack.getAmount();
                if (diff >= amount) {
                    amount = 0;
                    break;
                } else amount -= diff;
            }
        }
        return amount <= 0;
    }
}