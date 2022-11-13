package jamy.jamysystem;

import org.bukkit.entity.Player;

public class JAMYPlayer {

    private final Player player;

    public JAMYPlayer(Player player) {
        this.player = player;
    }

    public void sendMoney(String receiver, Integer amount) {

        Integer MoneyGiver = JAMYMoney.getMoney(this.player.getName());
        Integer MoneyReceiver = JAMYMoney.getMoney(receiver);

        if (MoneyGiver < amount) {
            this.player.sendMessage("Not enough money.");
            return;
        } else {
            MoneyGiver -= amount;
            JAMYMoney.setMoney(this.player.getName(), MoneyGiver);
            MoneyReceiver += amount;
            JAMYMoney.setMoney(receiver, MoneyReceiver);
        }

    }

}