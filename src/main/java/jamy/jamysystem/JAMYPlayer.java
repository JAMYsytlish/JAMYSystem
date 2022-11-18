package jamy.jamysystem;

import jamy.jamysystem.shop.JAMYShop;
import jamy.jamysystem.shop.ShopItemE;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class JAMYPlayer {

    private final Player player;

    public JAMYPlayer(Player player) {
        this.player = player;
    }

    public void sendMoney(Player receiver, Integer amount) {

        Integer MoneyGiver = JAMYMoney.getMoney(this.player);
        Integer MoneyReceiver = JAMYMoney.getMoney(receiver);

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
            player.sendMessage("You don't have enough money");
        } else {
            if (!this.hasSpace((ItemStack) shop.getItemInfo(index, ShopItemE.VALUE))) {
                this.player.sendMessage("Your Inventory doesn't have space");
            } else {
                if (shop.getItemInfo(index, AVAIl) == true) { 
                   this.player.getInventory().addItem((ItemStack) shop.getItemInfo(index, ShopItemE.VALUE));
                }
            }   
        }
 
    }


    public boolean hasSpace(ItemStack itemStack) {
        Inventory tempInv = Bukkit.createInventory(null, this.player.getInventory().getSize()*9, "temp");
        tempInv.setContents(this.player.getInventory().getContents());
        final Map<Integer, ItemStack> map = tempInv.addItem(itemStack); // Attempt to add in inventory    
        return map.isEmpty();
    }

}