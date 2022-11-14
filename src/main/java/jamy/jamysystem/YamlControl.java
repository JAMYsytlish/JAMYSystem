package jamy.jamysystem;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static jamy.jamysystem.JAMYSystem.DataFolder;

enum YamlEnum {
    Shop("ShopData/"),
    Money("MoneyData/");
    private final String path;

    YamlEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }



}



public class YamlControl {

    private final File file;
    private FileConfiguration customFile;

    private final String name;

    public YamlControl(YamlEnum type, String name) {
        this.name = name;
        this.file = new File(DataFolder, type.getPath() + name +".yaml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                Bukkit.getPlayer("JaemY_Nane").sendMessage(String.valueOf(file.createNewFile()));
            } catch (IOException e) {
                // blabla..
                Bukkit.getPlayer("JaemY_Nane").sendMessage("Failed.");
            }
        }
        Bukkit.getPlayer("JaemY_Nane").sendMessage("out");

        customFile = YamlConfiguration.loadConfiguration(file);
        this.save();


    }



    public String getName() {
        return this.name;
    }
    public FileConfiguration get(){
        return customFile;
    }
 
    public void save() {
        try{
            customFile.save(file);
        }catch (IOException e) {
            System.out.println("Couldn't save file");
        }
    }
 
    public void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }



}