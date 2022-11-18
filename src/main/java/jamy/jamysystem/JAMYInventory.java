package jamy.jamysystem;

import jamy.jamysystem.item.JAMYItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;


public class JAMYInventory implements Cloneable {

    private final Inventory inventory;

    public static JAMYInventory ClockMenu = new JAMYInventory("ClockMenu", 3);


    public void setupClockMenu() {
        this.getInventory().setItem(13, JAMYItem.getItem(Material.PAPER, ChatColor.WHITE + "Check Money"));
        this.getInventory().setItem(15, JAMYItem.getItem(Material.GOLD_NUGGET, ChatColor.WHITE + "Setting money"));

    }

    // 샘플 인벤토리를 하나 만들어두고 만약 모든 플레이어에게 다 다르게 표시되게 해야한다면 그때마다 새로 복제해서 만들어서 다른 부분만 채워 보여주는 것으로.
    public JAMYInventory(String title, int raw) {
        this.inventory = Bukkit.createInventory(null, raw*9, title);
    }


//    public void setMoneyFrame(JAMYInventory inv, Player toWhom) {
//        Inventory gui = Bukkit.createInventory(null, 36, "§c테스트");
//        IInventory inventory = ((CraftInventoryCustom) gui).getInventory();
//        try {
//            Field field = inventory.getClass().getDeclaredField("title");
//            field.setAccessible(true);
//            String title = (String) field.get(inventory);
//            Bukkit.broadcastMessage("gui 타이틀: " + title);
//        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
//    }

    public Inventory getInventory() {
        return this.inventory;
    }

    

}
