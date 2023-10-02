package org.mehdi.game;

import java.util.*;
import java.util.stream.Collectors;

public class Deck {
    public List<Card> cards;

    public Deck() {
        cards = Arrays.stream(Suit.values())
              .flatMap(suit -> Arrays.stream(Rank.values()).map(rank -> new Card(rank, suit)))
              .collect(Collectors.toList());
        shuffleCards();
    }

    public void addMultipleCards(List<Card> cardsToAdd) {
        cards.addAll(cardsToAdd);
    }

    public List<Card> drawMultipleCards(int n) {
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

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
