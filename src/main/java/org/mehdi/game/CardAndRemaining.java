package org.mehdi.game;

import java.util.List;

public class CardAndRemaining {
    private final Card card;
    private final List<Card> remainingCards;

    public CardAndRemaining(Card card, List<Card> remainingCards) {
        this.card = card;
        this.remainingCards = remainingCards;
    }

    public Card getCard() {
        return card;
    }

    public List<Card> getRemainingCards() {
        return remainingCards;
    }
}
