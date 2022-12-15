package jamy.jamysystem;

import jamy.jamysystem.shop.JAMYShop;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static jamy.jamysystem.item.JAMYItem.getRegister;

public class JAMYCommand implements CommandExecutor {
    public static boolean isInt(String str) {
        char tmp;
        for (int i = 0; i < str.length(); i++) {
            tmp = str.charAt(i);
            if (!Character.isDigit(tmp)) {
                return false;
            }
        }
        return true;
    }

    public static Object getBoolean(String str) {
        if (str.equals("true")) return true;
        if (str.equals("false")) return false;
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        } else {
            String name;
            Player player = ((Player) sender).getPlayer();
            JAMYShop shop;
            if (player == null) return false;
            if (args.length == 0) {
                return false;
            } else {
                name = args[0];
            }

            shop = JAMYShop.getShop(name);

            int price, stock = -1;
            switch (args[1]) {
                case "open":
                    shop.openTo(player);
                    break;
                case "delete":
                    shop.deleteShop();
                    break;
                case "getregister": //JAMYShop 0.JaemY_Nane 1.getregister 2.<price>
                    if (args.length < 3) return false;
                    if (!isInt(args[2])) {
                        return false;
                    }
                    price = Integer.parseInt(args[2]);
                    ItemStack register = (player.getInventory().getItemInMainHand());
                    if (register.getType().equals(Material.AIR)) return false;
                    register = getRegister(register, price);
                    player.getInventory().setItemInMainHand(register);
                    break;
                case "register":  //JAMYShop 0.JaemY_Nane 1.register 2.<price> 3.<stock:boolean> 4.<howMany:int>=0>
                    if (args.length < 4) return false;
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    if (itemStack.getType().equals(Material.AIR)) return false;
                    boolean stockCheck = false;
                    int howMany = 0;
                    if (!isInt(args[2])) {
                        return false;
                    }
                    if (getBoolean(args[3]) == null) {
                        return false;
                    } else if ((boolean) getBoolean(args[3])) {
                        stockCheck = (boolean) getBoolean(args[3]);
                        if (!isInt(args[4])) {
                            return false;
                        }
                        howMany = Integer.parseInt(args[4]);
                    }

                    price = Integer.parseInt(args[2]);

                    shop.registerItem(itemStack, price, stockCheck, howMany, true);
                    for (Player openPlayer : shop.openList) {
                        shop.loadInventory(openPlayer.getOpenInventory().getTopInventory(), openPlayer);
                    }
                    break;
                default:
                    return false;
            }
            return true;
        }

    }
}
