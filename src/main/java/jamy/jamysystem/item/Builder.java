package jamy.jamysystem.item;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Builder {

    Builder amount(final int amount);

    Builder displayName(final String displayName);

    Builder lore(final String... lore);

    Builder lore(final List<String> lore);

    Builder emptyLore();

    ItemStack build();

}