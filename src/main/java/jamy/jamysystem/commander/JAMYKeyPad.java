package jamy.jamysystem.commander;

import jamy.jamysystem.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class JAMYKeyPad {


    private static final Inventory keyPad = Bukkit.createInventory(null, 6, "[JAMYKeyPad]");

    enum Number {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        ZERO(0);
        private final ItemStack itemStack;

        Number(int customModelData) {
            final String name = String.valueOf(customModelData);
            final ItemStack itemStack = new ItemBuilder(Material.GUNPOWDER).displayName(String.valueOf(name.charAt(name.length() - 1))).build();
            final ItemMeta meta = itemStack.getItemMeta();
            meta.setCustomModelData(customModelData);
            itemStack.setItemMeta(meta);
            this.itemStack = itemStack;
        }

    }


}
