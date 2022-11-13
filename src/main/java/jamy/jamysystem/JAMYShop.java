package jamy.jamysystem;

import static jamy.jamysystem.YamlEnum.Shop;

public class JAMYShop {

    private final String name;

    public JAMYShop(String name) {
        this.name = name;
        YamlControl y1 = new YamlControl(Shop,name);

    }

    public String getName() {
        return this.name;
    }


    public void setYamlDefault(YamlControl y1) {
        y1.get().set("Name.",y1.getName());
    }
}