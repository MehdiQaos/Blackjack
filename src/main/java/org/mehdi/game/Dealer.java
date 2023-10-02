package org.mehdi.game;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
    private List<Card> hand;

    public Dealer() {
        hand = new ArrayList<>();
    }

    public void addCard(Card newCard) {
        hand.add(newCard);
    }

    public List<Card> clearHand() {
        List<Card> discardedCards = new ArrayList<>(hand);
        hand.clear();
        return discardedCards;
    }
}
