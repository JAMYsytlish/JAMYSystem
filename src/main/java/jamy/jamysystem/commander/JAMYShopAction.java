package jamy.jamysystem.commander;

import jamy.jamysystem.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class JAMYShopAction implements Listener {

    private static final Inventory shopAction = Bukkit.createInventory(null, 3, "[JAMYShopAction]");
    private static final ItemStack FRAME = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
            .displayName("§f")
            .emptyLore()
            .build();

    private static final ItemStack GET_REGISTER = new ItemBuilder(Material.GOLD_INGOT)
            .displayName("§e판매권 발급")
            .lore("§f판매권을 발급받을 수 있습니다")
            .build();

    private static final ItemStack REGISTER_PRODUCT = new ItemBuilder(Material.TORCH)
            .displayName("§e판매권 등록")
            .lore("§f자신의 상점에 판매권을 등록할 수 있습니다")
            .build();

    private static final ItemStack DELETE_PRODUCT = new ItemBuilder(Material.BARRIER)
            .displayName("§e물품 삭제")
            .lore("§f자신의 상점에 등록되어 있는 물품을 삭제할 수 있습니다")
            .build();

    public static void openShopActionTo(Player player) {
        player.openInventory(shopAction);
    }

    public static void init() {
        ItemStack[] items = new ItemStack[27];
        items[10] = GET_REGISTER;
        items[11] = REGISTER_PRODUCT;
        items[12] = DELETE_PRODUCT;

        for (int i = 0; i < 9; i++) {
            items[i] = FRAME;
            items[i + 18] = FRAME;
        }
        items[9] = FRAME;
        items[17] = FRAME;

        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = new ItemStack(Material.AIR);
            }
        }

        shopAction.setContents(items);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

    }

}
