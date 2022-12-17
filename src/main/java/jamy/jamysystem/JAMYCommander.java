package jamy.jamysystem;

import jamy.jamysystem.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class JAMYCommander implements Listener {

    private static final Inventory commander = Bukkit.createInventory(null, 3, "[JAMYShopMenu]");
    private static final ItemStack SHOP_LIST = new ItemBuilder(Material.PAPER)
            .displayName("§e상점 목록")
            .lore("§f상점 목록을 볼 수 있습니다")
            .build();
    private static final ItemStack SHOP_ACTION = new ItemBuilder(Material.APPLE)
            .displayName("§e상점 활동")
            .lore("§f상점 관련 활동을 할 수 있습니다", "§8≫판매권 발급, 판매권 등록, 물품 삭제...")
            .build();

    private static final ItemStack FRAME = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
            .displayName("§f")
            .emptyLore()
            .build();

    public static void commanderInit() {

        commander.setItem(10, SHOP_LIST);
        commander.setItem(11, SHOP_ACTION);
        for (int i = 0; i < 9; i++) {
            commander.setItem(i, FRAME);
            commander.setItem(i + 18, FRAME);
        }
        commander.setItem(9, FRAME);
        commander.setItem(17, FRAME);
    }

    @EventHandler
    public void onRightClickWithNetherStar(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        player.openInventory(commander);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("[JAMYShopMenu]")) {
            event.setCancelled(true);
            if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§e상점 목록")) {
                    // open inventory for 'shop list'
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§e상점 활동")) {
                    // open inventory for 'shop action'
                }
            }
        }
    }

}
