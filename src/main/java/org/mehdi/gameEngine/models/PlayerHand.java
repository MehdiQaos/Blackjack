package org.mehdi.gameEngine.models;

public class PlayerHand extends Hand{
    private int bet;
    private boolean active;

    public PlayerHand(int bet) {
        super();
        this.bet = bet;
        this.active = true;
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
}