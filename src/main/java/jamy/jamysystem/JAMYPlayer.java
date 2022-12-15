package jamy.jamysystem;

import jamy.jamysystem.shop.JAMYShop;
import jamy.jamysystem.shop.ShopItemE;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static jamy.jamysystem.shop.ShopE.BUY;
import static jamy.jamysystem.shop.ShopE.SELL;
import static jamy.jamysystem.shop.ShopItemE.*;

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
        if (JAMYMoney.getMoney(this.player) < (int) shop.getItemInfo(index, PRICE)) {
            this.player.sendMessage("You don't have enough money");
        } else if (!this.hasSpace((ItemStack) shop.getItemInfo(index, ShopItemE.VALUE))) {
            this.player.sendMessage("Your Inventory doesn't have space");
        } else {
            if ((boolean) shop.getType(BUY)) {
                Object stocks = shop.getItemInfo(index, STOCKS);
                if (!stocks.equals(false)) {
                    if ((int) stocks <= 0) {
                        this.player.sendMessage("Stocks shortage");
                        return;
                    } else {
                        shop.setItemInfo(index, STOCKS, (int) stocks - 1);
                    }
                }
                this.player.getInventory().addItem((ItemStack) shop.getItemInfo(index, ShopItemE.VALUE));
                JAMYMoney.subMoney(this.player, (int) shop.getItemInfo(index, PRICE));
                shop.loadInventory(this.player.getOpenInventory().getTopInventory(), this.player);

            }
        }
    }


    public int sell(JAMYShop shop, ItemStack itemStack) { // itemStack으로 판매 시도 // itemStack <- cursor // return = 판 아이템 개수
        // shop에 itemStack 과 같은 물품이 있는지 확인
        int index = shop.exists(itemStack);
        if (index == -1) return -1;

        ItemStack shopItem = (ItemStack) shop.getItemInfo(index, VALUE);
        int price = (int) shop.getItemInfo(index, PRICE);
        if (shop.getType(SELL).equals(false)) {
            return 0;
        }
        if (shop.getType(SELL).equals("on/off") && shop.getItemInfo(index, SELLAVAIL).equals(false)) {
            return 0;
        }

        // 판매 작업 시작
        int sellAmount = Math.floorDiv(itemStack.getAmount(), shopItem.getAmount()); // 몫

        int moneyToAdd = (int) Math.floor(price * sellAmount * 0.9);
        int moneyToSend = price * sellAmount - moneyToAdd;

        JAMYMoney.addMoney(this.player, moneyToAdd);
        if (Bukkit.getPlayer(shop.getName()) != null) {
            JAMYMoney.addMoney(Bukkit.getPlayer(shop.getName()), moneyToSend);
        } else {
            JAMYMoney.addMoney(this.player, moneyToSend);
        }
        if (!shop.getItemInfo(index, STOCKS).equals(false)) {
            shop.setItemInfo(index, STOCKS, (int) shop.getItemInfo(index, STOCKS) + sellAmount);
        }
        return sellAmount * shopItem.getAmount();

        // shop에 있는 개수가 1개 보다 큰 경우
        // itemStack 개수가 그보다 큰지 확인, mod로 남은 개수 확인 floor로 판매될 묶음수 확인

        // sell 이 true || on/off
        // sell on/off -> sell.avail true 인지 확인
        // 판매 ( price 가져와서 player에게 추가 )
        // stock != false 라면 stock에 판매된 개수 추가 하도록

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