package org.mehdi.game;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private int bet;
    private boolean active;
    private List<Card> cards;

    public Hand(int bet) {
        this.bet = bet;
        this.active = true;
        this.cards = new ArrayList<>();
    }

    public void add(Card card) {
        cards.add(card);
    }

    private int numberOfAces() {
        // TODO: cast
        return (int) cards.stream()
                .filter(Card::isAce)
                .count();
    }

    public int evaluate() {
        int score = cards.stream()
                .mapToInt(card -> card.getRank().getValue())
                .sum();
        int numAces = numberOfAces();
        while (score > 21 && numAces > 0) {
            score -= 10;
        }

        return score;
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public List<Card> clearHand() {
        var temp = cards;
        cards = new ArrayList<Card>();
        return temp;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}