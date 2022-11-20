package jamy.jamysystem.item;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JAMYItem extends ItemStack {


    public static void playSound(Player player, Sound sound, float volume, float pitch) {
        if (!player.isOnline())
            return;
        player.playSound(player.getLocation(), sound, volume, pitch);
    }



    public static ItemStack getItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getItem(ItemStack itemStack, String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);
        return itemStack;
    }


    public static ItemStack getItem(ItemStack itemStack, String name, ArrayList<String> lore) {
        ArrayList<String> array = new ArrayList<String>(lore);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(array);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getRegister(ItemStack itemStack, String owner, int price,int stock) {
        Material type = itemStack.getType();
        int amount = itemStack.getAmount();

        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        lore.add(" ");
        lore.add("§f판매권자: §3" + owner);
        lore.add("§f판매 가격: §e" + NumberFormat.getInstance().format(price));
        if(stock != -1) { lore.add("§f재고: §3" + stock); }
        lore.add(" ");
        lore.add(ChatColor.DARK_GRAY + String.valueOf(UUID.randomUUID()));
        meta.setDisplayName("§f§l" + amount + "§r§f개 §6판매권");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}

