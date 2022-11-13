package jamy.jamysystem;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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
        this.file = new File(Bukkit.getServer().getPluginManager().getPlugin("JAMYSystem").getDataFolder(), type.getPath() + name +".yaml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // blabla..
            }
        } 

        customFile = YamlConfiguration.loadConfiguration(file);


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