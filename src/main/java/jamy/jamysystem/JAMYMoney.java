package jamy.jamysystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static jamy.jamysystem.YamlEnum.Money;

public class JAMYMoney {


    public static Integer getMoney(String target) {
        YamlControl y1 = new YamlControl(Money, target);
        Integer money = (Integer) y1.get().get("Money");
        if(money == null) {
            setMoney(target, 0);
            money = 0;
        }
        return money;
    }
    
    public static void setMoney(String target, Integer tgMoney) {
        Player player = Bukkit.getPlayer(target);
        YamlControl y1 = new YamlControl(Money, target);
        y1.get().set("Money", tgMoney);
        y1.save();

    }




}