package jamy.jamysystem;

import jamy.jamysystem.yaml.YamlControl;
import org.bukkit.entity.Player;

import static jamy.jamysystem.yaml.YamlEnum.Money;

public class JAMYMoney {


    public static int getMoney(Player target) {
        YamlControl y1 = new YamlControl(Money, target.getName());
        Integer money = (Integer) y1.get().get("MONEY");
        if (money == null) {
            setMoney(target, 0);
            money = 0;
        }
        return money;
    }

    public static void setMoney(Player target, Integer tgMoney) {
        YamlControl y1 = new YamlControl(Money, target.getName());
        y1.get().set("MONEY", tgMoney);
        y1.save();
    }

    public static void addMoney(Player target, Integer tgMoney) {
        setMoney(target, getMoney(target) + tgMoney);
    }

    public static void subMoney(Player target, Integer tgMoney) {
        setMoney(target, getMoney(target) - tgMoney);
    }


}