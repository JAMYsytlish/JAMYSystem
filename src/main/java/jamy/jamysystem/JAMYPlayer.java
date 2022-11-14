package jamy.jamysystem;

import org.bukkit.entity.Player;

public class JAMYPlayer {

    private final Player player;

    public JAMYPlayer(Player player) {
        this.player = player;
    }

    public void sendMoney(Player receiver, Integer amount) {

        Integer MoneyGiver = JAMYMoney.getMoney(this.player);
        Integer MoneyReceiver = JAMYMoney.getMoney(receiver);

        if (MoneyGiver < amount) {
            this.player.sendMessage("Not enough money.");
            return;
        } else {
            JAMYMoney.subMoney(this.player, amount);
            JAMYMoney.addMoney(receiver, amount);
        }

    }

}