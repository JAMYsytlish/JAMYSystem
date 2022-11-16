package jamy.jamysystem.yaml;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static jamy.jamysystem.JAMYSystem.DataFolder;


public class YamlControl {

    private final File file;
    private final FileConfiguration customFile;
    private final String name;

    public YamlControl(YamlEnum type, String name) {
        this.name = name;
        this.file = new File(DataFolder, type.getPath() + name +".yaml");
//        System.out.println("exists? : " + file.exists());
//        System.out.println(file.getPath());

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
//                e.printStackTrace();
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
        try {
            customFile.save(file);
        } catch (IOException e) {
            System.out.println("Couldn't save file");
        }
    }
    public void delete() {
        file.delete();
    }
//    public void reload(){
//        customFile = YamlConfiguration.loadConfiguration(file);
//    }

}