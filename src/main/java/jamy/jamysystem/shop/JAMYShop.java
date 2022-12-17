package jamy.jamysystem.shop;

import jamy.jamysystem.JAMYMoney;
import jamy.jamysystem.item.JAMYItem;
import jamy.jamysystem.item.StainedColorPane;
import jamy.jamysystem.yaml.YamlControl;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.*;

import static jamy.jamysystem.shop.ShopE.*;
import static jamy.jamysystem.shop.ShopItemE.*;
import static jamy.jamysystem.yaml.YamlEnum.Shop;

public class JAMYShop {

    private static final HashMap<String, JAMYShop> Shop_HashMap = new HashMap<>();
    public static String shopTag = "[JAMYShop] ";
    private final String name;
    private final YamlControl yaml;

    // yaml 저장을 위한 List
    public HashSet<Player> openList = new HashSet<>();

    private JAMYShop(String name) {
        this.name = name;
        this.yaml = new YamlControl(Shop, name);
        Shop_HashMap.put(name, this);
        this.setTypeToDefault();
    }

    public static JAMYShop getShop(String name) {
        if (Shop_HashMap.containsKey(name)) {
            return Shop_HashMap.get(name);
        } else {
            return new JAMYShop(name);
        }
    }

    public static void setDefaultFrame(Inventory inventory) {
        for (int i = 36; i < 45; i++) {
            inventory.setItem(i, JAMYItem.getItem(StainedColorPane.getColorPane(2), "§f"));
        }
        List<String> lore = Arrays.asList("§f좌클릭으로 구매", "§f상점 인벤토리로 아이템을 보내 판매");
        ItemStack item = JAMYItem.getItem(Material.OAK_SIGN, "§e도움말", lore);
        inventory.setItem(45, item);
        item = JAMYItem.getItem(Material.ITEM_FRAME, "§e-1- 페이지");
        inventory.setItem(49, item);
    }

    public static void setMoneyFrame(Inventory inventory, Player toWhom) {
        inventory.setItem(53, JAMYItem.getItem(Material.PAPER, "보유 돈 : " + JAMYMoney.getMoney(toWhom)));
    }

    private void setDescriptionForItem(Inventory inventory, Player toWhom) {
        if (!this.name.equals(toWhom.getName()) && Bukkit.getPlayer(this.getName()) != null) {
            int itemAmount = this.getItemAmount();
            for (int i = 0; i < itemAmount; i++) {
                ItemStack itemStack = inventory.getItem(i);
                List<String> lore = itemStack.getItemMeta().getLore();
                lore.add(" ");
                lore.add("§3" + this.name + "§f님에게 판매 금액의 §a10§f%가 입금됩니다!");
                ItemStack jamyItem = JAMYItem.getItem(itemStack, lore);
                inventory.setItem(i, jamyItem);
            }
        }
    }

    public int exists(ItemStack itemStack) {
        for (int i = 0; i < this.getItemAmount(); i++) {
            ItemStack shopItem = (ItemStack) this.getItemInfo(i, VALUE);
            if (itemStack.isSimilar(shopItem)) {
                return i; // 같은 것 i 번째임
            }
        }
        return -1; // 같은 것 없음
    }

    public String getName() {
        return this.name;
    }

    public YamlControl getYaml() {
        return yaml;
    }

    private void setTypeToDefault() { // setType() 으로 바꿔보기
        this
                .setType(BUY, true)
                .setType(BUYSHOW, true)
                .setType(SELL, true)
                .setType(SELLSHOW, true)
                .setType(OPENED, 36)
                .setType(OWNERSHOW, false)
                .setType(SMPVSHOW, false)
                .setType(BGPVSHOW, false);
    }

    public void loadInventory(Inventory inventory, Player toWhom) {
        this.setShopContents(inventory);
        setDefaultFrame(inventory);
        setMoneyFrame(inventory, toWhom);
        this.setDescriptionForItem(inventory, toWhom);
    }

    private void setShopContents(Inventory inventory) {

        ItemStack[] items = new ItemStack[36];
        for (int i = 0; i < 36; i++) {
            items[i] = (ItemStack) this.getItemInfo(i, VALUE);
            if (items[i] == null) {
                items[i] = new ItemStack(Material.AIR);
                continue;
            }
            List<String> lore = new ArrayList<>();
            lore.add(" ");
//            lore.add(" ");
            /*  BUY true SELL true : 가격
                BUY false SELL true : 구매 가격 
                BUY true SELL false : 판매 가격
            */
            if (!this.getType(BUY).equals(false) && !this.getType(SELL).equals(false)) { // BUY true SELL true : 가격
                lore.add("§f가격 : §e" + NumberFormat.getInstance().format(this.getItemInfo(i, PRICE)));
            } else if (this.getType(BUY).equals(false) && !this.getType(SELL).equals(false)) { // BUY false SELL true : 구매 가격
                lore.add("§f구매 가격 : §e" + NumberFormat.getInstance().format(this.getItemInfo(i, PRICE)));
            } else if (!this.getType(BUY).equals(false) && this.getType(SELL).equals(false)) { // BUY true SELL false : 판매 가격
                lore.add("§f판매 가격 : §e" + NumberFormat.getInstance().format(this.getItemInfo(i, PRICE)));
            }

            if (!this.getItemInfo(i, STOCKS).equals(false)) {
                lore.add("§f재고 : §c" + NumberFormat.getInstance().format(this.getItemInfo(i, STOCKS)));
            }
            lore.add(" ");


            items[i] = JAMYItem.getItem((ItemStack) this.getItemInfo(i, VALUE), lore);


        }
        inventory.setContents(items);


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
            case STOCKS:
                obj = this.yaml.get().get("SHOP.ITEM." + index + ".STOCKS");
                break;
            case PRICE:
                obj = this.yaml.get().getInt("SHOP.ITEM." + index + ".PRICE");
                break;
            case VALUE:
                obj = this.yaml.get().getItemStack("SHOP.ITEM." + index + ".VALUE");
                break;
            case SELLAVAIL:
                obj = this.yaml.get().getBoolean("SHOP.ITEM." + index + ".SELLAVAIL");
                break;
            case BUYAVAIL:
                obj = this.yaml.get().getBoolean("SHOP.ITEM." + index + ".BUYAVAIL");
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
        LinkedList<ItemStack> Shop_Item = new LinkedList<>();
        LinkedList<Integer> Shop_Price = new LinkedList<>();
        LinkedList<Object> Shop_Stock = new LinkedList<>();


        for (String ind : this.yaml.get().getConfigurationSection("SHOP.ITEM").getKeys(false)) {
            Shop_Item.add((ItemStack) this.getItemInfo(Integer.parseInt(ind), VALUE));
            Shop_Price.add((Integer) this.getItemInfo(Integer.parseInt(ind), PRICE));
            Shop_Stock.add(this.getItemInfo(Integer.parseInt(ind), STOCKS));
        }
        this.yaml.get().set("SHOP.ITEM", null);

        Shop_Item.remove(index - 1);
        Shop_Price.remove(index - 1);
        Shop_Stock.remove(index - 1);

        listToYaml(Shop_Item, VALUE);
        listToYaml(Shop_Price, PRICE);
        listToYaml(Shop_Stock, STOCKS);

    }

    public void listToYaml(LinkedList<?> list, ShopItemE key) {
        for (int i = 0; i < list.size(); i++) {
            this.setItemInfo(i, key, list.get(i));
        }
    }

    public void deleteShop() {
        Shop_HashMap.remove(this.getName());
        this.yaml.delete();

    }


    public void openTo(Player toWhom) {
        // 돈 칸 세팅
        Inventory inventory = Bukkit.createInventory(null, 54, shopTag + this.name);
        this.loadInventory(inventory, toWhom);
        openList.add(toWhom);

        toWhom.openInventory(inventory);
    }

    //?? 이렇게 하는게 맞어?
    public int getItemAmount() {
        int size;
        try {
            size = this.yaml.get().getConfigurationSection("SHOP.ITEM").getKeys(false).size();
        } catch (NullPointerException e) {
            size = 0;
        }
        return size;
    }


    public void registerItem(ItemStack item, int price, boolean hasStocks, int stocks) {
        int index = this.getItemAmount();
        this
                .setItemInfo(index, VALUE, item)
                .setItemInfo(index, PRICE, price)
                .setItemInfo(index, STOCKS, hasStocks);
//                .setItemInfo(index, OWNER, owner);

        if (hasStocks) {
            setItemInfo(index, STOCKS, stocks); // 재고 있으면 숫자로, 없으면 false 로
        }

    }

    public void registerItem(ItemStack item, int price, boolean stocks, int howMany, boolean pricevar, int smpv, int bgpv, boolean avail) {
        int index = this.getItemAmount();
        this
                .setItemInfo(index, VALUE, item)
                .setItemInfo(index, PRICE, price)
                .setItemInfo(index, STOCKS, stocks);

        if (stocks) {
            setItemInfo(index, STOCKS, howMany); // 재고 있으면 숫자로, 없으면 false 로
        }

    }

    public void registerItem(ItemStack item, int price, boolean avail) {
        registerItem(item, price, false, 0, false, 0, 0, avail);
    }

    public void registerItem(ItemStack item, int price, boolean stocks, int howMany, boolean avail) {
        registerItem(item, price, stocks, howMany, false, 0, 0, avail);
    }

    public void registerItem(ItemStack item, int price, boolean pricevar, int smpv, int bgpv, boolean avail) {
        registerItem(item, price, false, 0, pricevar, smpv, bgpv, avail);
    }


}