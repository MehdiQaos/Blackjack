package org.mehdi.game;

import java.util.ArrayList;
import java.util.List;

public abstract class Hand {
    List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Card> clearHand() {
        var temp = cards;
        cards = new ArrayList<>();
        return temp;
    }

    public void add(Card card) {
        cards.add(card);
    }

    int numberOfAces() {
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
            numAces--;
        }

        return score;
    }
}
