package jamy.jamysystem;

import jamy.jamysystem.yaml.YamlControl;
import jamy.jamysystem.yaml.YamlEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

import static jamy.jamysystem.JAMYInventory.setMoneyFrame1;

public class Test implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        setMoneyFrame1();

        YamlControl yaml = new YamlControl(YamlEnum.Money, "TEST");
        yaml.get().set("TEST.KEY.1","hello");
        yaml.get().set("TEST.KEY.2","world!");
        List<Integer> list = new ArrayList<>();
        list.add(0,3);
        list.add(1,4);
        yaml.get().set("TEST.LIST",list);
        yaml.save();

        List<?> object = yaml.get().getMapList("TEST.KEY");
//        System.out.println("Class : " + object.getClass());
        System.out.println(yaml.get().getConfigurationSection("TEST.KEY").getKeys(false));

        return false;
    }
}
