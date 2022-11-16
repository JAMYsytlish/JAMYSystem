package jamy.jamysystem.yaml;

public enum YamlEnum {
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
