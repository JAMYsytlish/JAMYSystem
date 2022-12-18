package jamy.jamysystem.commander;

import jamy.jamysystem.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class JAMYCommander implements Listener {

    private static final Inventory commander = Bukkit.createInventory(null, 3, "[JAMYCommander]");
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
        ItemStack[] items = new ItemStack[27];
        items[10] = SHOP_LIST;
        items[11] = SHOP_ACTION;
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
        commander.setContents(items);
    }


    @EventHandler
    public void onRightClickWithNetherStar(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getType().equals(Material.NETHER_STAR)) {
                final Player player = event.getPlayer();
                player.sendMessage("HUH??");
                player.openInventory(commander);

            }
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("[JAMYShopMenu]")) {
            event.setCancelled(true);
            if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§e상점 목록")) {
                    // open inventory for 'shop list'
                    JAMYShopAction.openShopActionTo((Player) event.getWhoClicked());
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§e상점 활동")) {
                    // open inventory for 'shop action'
                }
            }
        }
    }

}
