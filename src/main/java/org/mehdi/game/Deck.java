package org.mehdi.game;

import org.mehdi.game.utils.Pair;

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
        Collections.shuffle(cards);
//        Random random = new Random();
//        int size = cards.size();
//        for (int i = 0; i < size; i++) {
//            int i1 = random.nextInt(0, size);
//            int i2 = random.nextInt(0, size);
//            Card temp = cards.get(i1);
//            cards.set(i1, cards.get(i2));
//            cards.set(i2, temp);
//        }
    }

//    public Pair<List<Card>, List<Card>> pioche() {
//        Random random = new Random();
//        int index = random.nextInt(0, cards.size());
//        List<Card> discardedCards = cards.subList(index, cards.size());
//        cards.removeAll(discardedCards);
//        return new Pair<>(cards, new ArrayList<>(discardedCards));
//    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
