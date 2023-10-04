package org.mehdi.gameEngine.models;

import java.util.ArrayList;
import java.util.List;

public class Tray {
    private final List<Card> cards;

    public Tray() {
        cards = new ArrayList<>(Card.NUM_CARDS);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCards(List<Card> discardedCards) {
        cards.addAll(discardedCards);
    }

    public List<Card> clear() {
        List<Card> clearedCards = new ArrayList<Card>(cards);
        cards.clear();
        return clearedCards;
    }
}
