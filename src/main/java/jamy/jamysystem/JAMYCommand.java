package jamy.jamysystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class JAMYCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlControl y1 = new YamlControl(YamlEnum.Money , "TEST");
        y1.save();
        return true;

    }
}
