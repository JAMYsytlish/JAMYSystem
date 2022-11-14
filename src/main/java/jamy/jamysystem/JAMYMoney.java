package jamy.jamysystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static jamy.jamysystem.YamlEnum.Money;

public class JAMYMoney {


    public static Integer getMoney(Player target) {
        YamlControl y1 = new YamlControl(Money, target.getName());
        Integer money = (Integer) y1.get().get("Money");
        if(money == null) {
            setMoney(target, 0);
            money = 0;
        }
        return money;
    }
    
    public static void setMoney(Player target, Integer tgMoney) {
        YamlControl y1 = new YamlControl(Money,target.getName());
        y1.get().set("Money", tgMoney);
        y1.save();
    }

    public static void addMoney(Player target, Integer tgMoney) {
        setMoney(target, getMoney(target) + tgMoney);
    }

    public static void subMoney(Player target, Integer tgMoney) {
        setMoney(target, getMoney(target) -tgMoney);
    }



}