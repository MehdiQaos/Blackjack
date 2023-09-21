package org.mehdi.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    public List<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
        for (int i = 1; i <= Card.MAX_COLOR; ++i) {
            for (int j = 1; j <= Card.MAX_NUMBER; ++j) {
                cards.add(new Card(j, i));
            }
        }
        shuffleCards();
    }

    public List<Card> drawNcards(int n) {
        if (n > cards.size() || n <= 0)
            return Collections.emptyList();

        List<Card> drawedCards = new ArrayList<>(cards.subList(0, n));
        cards.subList(0, n).clear();
        return drawedCards;
    }

    public Card drawCard() {
        if (cards.isEmpty())
            return null;
        return cards.remove(0);
    }

    public void shuffleCards() {
        Random random = new Random();
        for (int i = 0; i < 52; i++) {
            int index = random.nextInt(0, cards.size());
            Card card = cards.remove(i);
            cards.add(index, card);
        }
    }
}
