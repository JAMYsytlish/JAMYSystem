package jamy.jamysystem.shop;

import jamy.jamysystem.JAMYInventory;
import jamy.jamysystem.JAMYMoney;
import jamy.jamysystem.item.JAMYItem;
import jamy.jamysystem.item.StainedColorPane;
import jamy.jamysystem.yaml.YamlControl;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static jamy.jamysystem.shop.ShopE.*;
import static jamy.jamysystem.shop.ShopItemE.*;
import static jamy.jamysystem.yaml.YamlEnum.Shop;

public class JAMYShop {

    private final String name;
    private final YamlControl yaml;
    private static HashMap<String,JAMYShop> Shop_HashMap = new HashMap<>();
    public static String shopTag = "[JAMYShop] ";

    public ArrayList<Player> openList = new ArrayList<>();
    private JAMYShop(String name) {
        this.name = name;
        this.yaml = new YamlControl(Shop,name);
        Shop_HashMap.put(name,this);
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

    private void setTypeToDefault() { // setType() 으로 바꿔보기
        this
        .setType(BUY,true)
        .setType(BUYSHOW,true)
        .setType(SELL,true)
        .setType(SELLSHOW,true)
        .setType(OPENED,36)
        .setType(OWNERSHOW,false)
        .setType(SMPVSHOW,false)
        .setType(BGPVSHOW,false);
    }



    public void loadInventory(Inventory inventory, Player toWhom) {
        this.setShopContents(inventory);
        setDefaultFrame(inventory);
        setMoneyFrame(inventory, toWhom);
    }
    
    private void setShopContents(Inventory inventory) {

        ItemStack[] items = new ItemStack[36];
        for (int i = 0; i <= 35; i++) {
            items[i] = (ItemStack) this.getItemInfo(i+1,VALUE);
            if (items[i] == null) {
                items[i] = new ItemStack(Material.AIR);
            }
        }
        inventory.setContents(items);
        

    }
    /*
        0 White,      1 Orange,  2 Magenta, 3 Light Blue
        4 Yellow,     5 Lime,    6 Pink,    7 Gray
        8 Light Gray, 9 Cyan,   10 Purple, 11 Blue
        12 Brown,    13 Green,  14 Red,    15 Black
         */

    public static void setDefaultFrame(Inventory inventory) {
        for (int i = 36; i < 45; i++) {
            inventory.setItem(i, JAMYItem.getItem(StainedColorPane.getColorPane(2),"§f"));
        }
        List<String> lore = Arrays.asList("§f좌클릭으로 구매", "§f상점 인벤토리로 아이템을 보내 판매");
        ItemStack item = JAMYItem.getItem(Material.OAK_SIGN,"§e도움말",lore);
        inventory.setItem(45, item);
        item = JAMYItem.getItem(Material.ITEM_FRAME,"§e-1- 페이지");
        inventory.setItem(49, item);
    }

    public static void setMoneyFrame(Inventory inventory, Player toWhom) {
        inventory.setItem(53, JAMYItem.getItem(Material.PAPER,"보유 돈 : " + JAMYMoney.getMoney(toWhom)));
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
                obj = this.yaml.get().getItemStack("SHOP.ITEM." + index + ".VALUE");
                break;
            case AVAIL:
                obj = this.yaml.get().getBoolean("SHOP.ITEM." + index + ".AVAIL");
        }
        return obj;
    }

    public JAMYShop setItemInfo(int index, ShopItemE key, Object value) {
        this.yaml.get().set("SHOP.ITEM." + index + "." + key, value);
        this.yaml.save();
        return this;
    }

    public Object getType(ShopE key) {
        return this.yaml.get().get("SHOP.TYPE." + key);
    }

    public JAMYShop setType(ShopE key, Object value) {
        this.yaml.get().set("SHOP.TYPE." + key, value);
        this.yaml.save();
        return this;
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
        // 돈 칸 세팅
        Inventory inventory = Bukkit.createInventory(null, 54,shopTag + this.name);
        this.loadInventory(inventory, toWhom);
        openList.add(toWhom);

        toWhom.openInventory(inventory);
    }
    //?? 이렇게 하는게 맞어?
    public int getItemAmount() {
        int size;
        try {
            size = this.yaml.get().getConfigurationSection("SHOP.ITEM").getKeys(false).size();
//            System.out.println(this.yaml.get().getList("SHOP.ITEM").toString());
//            System.out.println("1");
        } catch (NullPointerException e) {
            size = 0;
        }
        return size;
    }
    public void registerItem(ItemStack item, int price, boolean stock, int howMany, boolean pricevar, int smpv, int bgpv, boolean avail) {
        int index = this.getItemAmount() + 1; 
        this
        .setItemInfo(index, VALUE, item)
        .setItemInfo(index, PRICE, price)
        .setItemInfo(index, STOCK, stock);
        if (stock) {
            setItemInfo(index,STOCK,howMany); // 재고 있으면 숫자로, 없으면 false 로
        }




    }

    public void registerItem(ItemStack item, int price, boolean avail) {
        registerItem(item, price, false, 0, false, 0,0, avail);
    }

    public void registerItem(ItemStack item, int price, boolean stock, int howMany, boolean avail) {
        registerItem(item, price, stock, howMany, false, 0, 0, avail);
    }
    
    public void registerItem(ItemStack item, int price, boolean pricevar, int smpv, int bgpv, boolean avail) {
        registerItem(item, price, false, 0, pricevar, smpv, bgpv,avail );
    }
}