package jamy.jamysystem.item;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder implements Builder {

    private final Material type;
    private Boolean unbreakable = null;
    private int amount = 1;
    private String displayName;
    private List<String> lore;

    public ItemBuilder(final Material type) {
        this.type = Preconditions.checkNotNull(type, "Type cannot be null");
    }

    public ItemBuilder unbreakable(final boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    @Override
    public ItemBuilder amount(final int amount) {
        Preconditions.checkArgument(amount > 0, "amount must be greater than 0");
        this.amount = amount;
        return this;
    }

    @Override
    public ItemBuilder displayName(final String displayName) {
        this.displayName = displayName;
        return this;
    }

    @Override
    public ItemBuilder lore(final String... lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    @Override
    public ItemBuilder lore(final List<String> lore) {
        this.lore = lore;
        return this;
    }

    @Override
    public ItemBuilder emptyLore() {
        this.lore = null;
        return this;
    }

    @Override
    public ItemStack build() {
        final ItemStack stack = new ItemStack(type, amount);
        final ItemMeta meta = stack.getItemMeta();
        if (displayName != null) meta.setDisplayName(displayName);
        if (lore != null) meta.setLore(lore);
        if (unbreakable != null) meta.setUnbreakable(unbreakable);
        stack.setItemMeta(meta);
        return stack;
    }

}