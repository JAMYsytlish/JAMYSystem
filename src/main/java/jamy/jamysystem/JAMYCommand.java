package jamy.jamysystem;

import jamy.jamysystem.shop.JAMYShop;
import jamy.jamysystem.yaml.YamlControl;
import jamy.jamysystem.yaml.YamlEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JAMYCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return false;
        } else {
            String name;
            Player player = ((Player) sender).getPlayer();
            JAMYShop shop;
            if(player == null) return false;
            switch (args.length) {
                case 1:
                    name = sender.getName();
                    break;
                case 2:
                    name = args[1];
                    break;
                default:
                    return false;
            }
            switch(args[0]) {
                case "open":
                    shop = JAMYShop.getShop(name);
                    shop.openTo(player);
                    break;
                case "delete":
                    shop = JAMYShop.getShop(name);
                    shop.deleteShop();
                    break;
                default:
                    return false;
            }
            return true;
        }

    }
}
