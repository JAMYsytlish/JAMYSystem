package jamy.jamysystem.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class StainedColorPane {


    /*
        0 White,      1 Orange,  2 Magenta, 3 Light Blue
        4 Yellow,     5 Lime,    6 Pink,    7 Gray
        8 Light Gray, 9 Cyan,   10 Purple, 11 Blue
        12 Brown,    13 Green,  14 Red,    15 Black
         */

    public static Material[] ColorPane = {
            Material.WHITE_STAINED_GLASS_PANE,
            Material.ORANGE_STAINED_GLASS_PANE,
            Material.MAGENTA_STAINED_GLASS_PANE
    };


    public static Material getColorPane(int index) {
        return ColorPane[index];
    }
}
