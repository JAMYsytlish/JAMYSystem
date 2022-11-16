package jamy.jamysystem.shop;

import jamy.jamysystem.JAMYInventory;
import jamy.jamysystem.JAMYItem;
import jamy.jamysystem.yaml.YamlControl;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static jamy.jamysystem.yaml.YamlEnum.Shop;

public class JAMYShop {

    private final String name;
    private final Inventory inventory;
    private final YamlControl yaml;
    private static HashMap<String,JAMYShop> Shop_HashMap = new HashMap<>();
    private JAMYShop(String name) {
        this.name = name;
        this.yaml = new YamlControl(Shop,name);
        this.inventory = new JAMYInventory("[JAMYShop] " + this.name,6).getInventory();
        Shop_HashMap.put(name,this);
        this.loadInventory();
        this.setTypeToDefault();
    }
    public static JAMYShop getShop(String name) {
        if (Shop_HashMap.containsKey(name)) {
            return Shop_HashMap.get(name);
        } else {
            return new JAMYShop(name);
        }
    }
    public String getName() {
        return this.name;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    private void setTypeToDefault() {
        this.yaml.get().set("SHOP.TYPE.BUY", true);
        this.yaml.get().set("SHOP.TYPE.BUYSHOW", true);
        this.yaml.get().set("SHOP.TYPE.SELL", true);
        this.yaml.get().set("SHOP.TYPE.SELLSHOW", true);
        this.yaml.get().set("SHOP.TYPE.OPENED", 36);
        this.yaml.get().set("SHOP.TYPE.OWNERSHOW", false);
        this.yaml.get().set("SHOP.TYPE.SMPVSHOW", false);
        this.yaml.get().set("SHOP.TYPE.BGPVSHOW", false);
        this.yaml.save();
    }

    private void loadInventory() {
        ItemStack[] items = new ItemStack[36];
        for (int i = 0; i <= 35; i++) {
            items[i] = (ItemStack) yaml.get().get("SHOP.ITEM." + i + ".ITEMVALUE");
            if (items[i] == null) {
                items[i] = new ItemStack(Material.AIR);
            }
        }
        this.getInventory().setContents(items);
        this.setDefaultFrame();
    }
    /*
        0 White,      1 Orange,  2 Magenta, 3 Light Blue
        4 Yellow,     5 Lime,    6 Pink,    7 Gray
        8 Light Gray, 9 Cyan,   10 Purple, 11 Blue
        12 Brown,    13 Green,  14 Red,    15 Black
         */

    public void setDefaultFrame() {
//        for (int i = 36; i < 45; i++) {
//            this.getInventory().setItem(i, getColorPane("§f", 1, colorCode));
//        }
        List<String> lore = Arrays.asList("§f좌클릭으로 구매", "§f상점 인벤토리로 아이템을 보내 판매");
        ItemStack item = JAMYItem.getItem(Material.OAK_SIGN,"§e도움말",lore);
        this.getInventory().setItem(45, item);
        item = JAMYItem.getItem(Material.ITEM_FRAME,"§e-1- 페이지");
        this.getInventory().setItem(49, item);
    }
    /*
      @param key
          stock -> int || boolean
          price -> int
          value -> ItemStack
          avail -> boolean
       */
    public Object getItemInfo(int index, ShopItemE key) {
        Object obj = null;
        switch (key) {
            case STOCK:
                obj = this.yaml.get().get("SHOP.ITEM." + index + ".STOCK");
                break;
            case PRICE:
                obj = this.yaml.get().getInt("SHOP.ITEM." + index + ".PRICE");
                break;
            case VALUE:
                obj = this.yaml.get().get("SHOP.ITEM." + index + ".ITEMVALUE");
                break;
            case AVAIL:
                obj = this.yaml.get().get("SHOP.ITEM." + index + ".AVAIL");
        }
        return obj;
    }

    public void setItemInfo(int index, ShopItemE key, Object value) {
        this.yaml.get().set("SHOP.ITEM." + index + "." + key, value);
        this.yaml.save();
    }

    public Object getType(ShopE key) {
        return this.yaml.get().get("SHOP.TYPE." + key);
    }

    public void setType(ShopE key, Object value) {
        this.yaml.get().set("SHOP.TYPE." + key, value);
        this.yaml.save();
    }
    public void deleteItem(int index) {

        // TODO
        // delete item
        // yaml re-setting

    }

    public void deleteShop() {
        Shop_HashMap.remove(this.getName());
        this.yaml.delete();

    }

    public void openTo(Player toWhom) {
        toWhom.openInventory(this.getInventory());
    }

}