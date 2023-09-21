package org.mehdi.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int money;
    private String name;
    private List<List<Card>> hands;

    public Player(int money, String name) {
        this.money = money;
        this.name = name;
        hands = new ArrayList<>();
        hands.add(new ArrayList<Card>());
    }

    public int numberOfHands() {
        return hands.size();
    }

    public int getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public void addCard(int handIndex, Card card) {
        hands.get(handIndex).add(card);
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<List<Card>> getHands() {
        return hands;
    }
}