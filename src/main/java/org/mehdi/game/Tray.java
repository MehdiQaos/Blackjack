package org.mehdi.game;

import java.util.List;

public class Tray {
    private final List<Card> cards;

    public Tray(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCards(List<Card> discardedCards) {
        cards.addAll(discardedCards);
    }
}
