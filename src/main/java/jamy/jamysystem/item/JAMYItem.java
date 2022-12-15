package jamy.jamysystem.item;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JAMYItem extends ItemStack {

    private final ItemStack itemStack;

    private JAMYItem(Material material) {
        this.itemStack = new ItemStack(material);
    }

    private JAMYItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static void playSound(Player player, Sound sound, float volume, float pitch) {
        if (!player.isOnline())
            return;
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static ItemStack getItem(Material material, String name) {
        JAMYItem jamyItem = new JAMYItem(material);
        jamyItem.setName(name);
        return jamyItem.itemStack;
    }

    public static ItemStack getItem(Material material, int amount) {
        JAMYItem jamyItem = new JAMYItem(material);
        jamyItem.itemStack.setAmount(amount);
        return jamyItem.itemStack;
    }

    public static ItemStack getItem(Material material, List<String> lore) {
        JAMYItem jamyItem = new JAMYItem(material);
        jamyItem.setLore(lore);
        return jamyItem.itemStack;
    }

    public static ItemStack getItem(Material material, String name, List<String> lore) {
        JAMYItem jamyItem = new JAMYItem(material);
        jamyItem.setName(name).setLore(lore);
        return jamyItem.itemStack;
    }

    public static ItemStack getItem(ItemStack itemStack, String name) {
        JAMYItem jamyItem = new JAMYItem(itemStack);
        jamyItem.setName(name);
        return jamyItem.itemStack;
    }

    public static ItemStack getItem(ItemStack itemStack, List<String> lore) {
        JAMYItem jamyItem = new JAMYItem(itemStack);
        jamyItem.setLore(lore);
        return jamyItem.itemStack;
    }

    public static ItemStack getItem(ItemStack itemStack, String name, List<String> lore) {
        JAMYItem jamyItem = new JAMYItem(itemStack);
        jamyItem.setName(name).setLore(lore);
        return jamyItem.itemStack;
    }

    public static ItemStack getRegister(ItemStack itemStack, int price) {
        Material type = itemStack.getType();
        int amount = itemStack.getAmount();

        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        lore.add(" ");
        lore.add("§f판매 가격: §e" + NumberFormat.getInstance().format(price));
        lore.add(" ");
        lore.add(ChatColor.DARK_GRAY + String.valueOf(UUID.randomUUID()));
        meta.setDisplayName("§f§l" + amount + "§r§f개 §6판매권");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private JAMYItem setLore(List<String> lore) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore(lore);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    private JAMYItem setName(String name) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setDisplayName(name);
        this.itemStack.setItemMeta(meta);
        return this;
    }
}

