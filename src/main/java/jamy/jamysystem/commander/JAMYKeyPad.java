package jamy.jamysystem.commander;

import jamy.jamysystem.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class JAMYKeyPad {


    private static final Inventory keyPad = Bukkit.createInventory(null, 6, "[JAMYKeyPad]");
    private static final ItemStack FRAME = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
            .displayName("§f")
            .emptyLore()
            .build();

    public static void init() {
        for (int i = 0; i < 54; i++) {
            keyPad.setItem(i, FRAME);
        }
        keyPad.setItem(Number.ONE.getSlot(), Number.ONE.getItem());
        keyPad.setItem(Number.TWO.getSlot(), Number.TWO.getItem());
        keyPad.setItem(Number.THREE.getSlot(), Number.THREE.getItem());
        keyPad.setItem(Number.FOUR.getSlot(), Number.FOUR.getItem());
        keyPad.setItem(Number.FIVE.getSlot(), Number.FIVE.getItem());
        keyPad.setItem(Number.SIX.getSlot(), Number.SIX.getItem());
        keyPad.setItem(Number.SEVEN.getSlot(), Number.SEVEN.getItem());
        keyPad.setItem(Number.EIGHT.getSlot(), Number.EIGHT.getItem());
        keyPad.setItem(Number.NINE.getSlot(), Number.NINE.getItem());
        keyPad.setItem(Number.ZERO.getSlot(), Number.ZERO.getItem());
        keyPad.setItem(Number.BACKSPACE.getSlot(), Number.BACKSPACE.getItem());

    }

    enum Number {
        ONE(1, 37),
        TWO(2, 38),
        THREE(3, 39),
        FOUR(4, 28),
        FIVE(5, 29),
        SIX(6, 30),
        SEVEN(7, 19),
        EIGHT(8, 20),
        NINE(9, 21),
        ZERO(10, "0", 47),
        BACKSPACE(88, "§f§l입력 지우기", 48);
        private final ItemStack itemStack;
        private final int slot;

        Number(int customModelData, String name, int slot) {
            final ItemStack itemStack = new ItemBuilder(Material.GUNPOWDER)
                    .displayName("§f§l" + name)
                    .build();
            final ItemMeta meta = itemStack.getItemMeta();
            if (meta == null) {
                System.out.println("Number -" + ChatColor.RED + " ItemMeta cannot be null!");
            }
            meta.setCustomModelData(customModelData);
            itemStack.setItemMeta(meta);
            this.itemStack = itemStack;
            this.slot = slot;
        }

        Number(int customModelData, int slot) {
            this(customModelData, String.valueOf(customModelData), slot);
        }

        public ItemStack getItem() {
            return itemStack;
        }

        public int getSlot() {
            return slot;
        }


    }


}
