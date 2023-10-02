package org.mehdi.game;

import java.util.Objects;

public class Card {
    public static final int MAX_NUMBER = 13;
    public static final int MAX_COLOR = 4;
    public static final int NUM_CARDS = MAX_NUMBER * MAX_COLOR;
    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean isAce() {
        return rank.equals(Rank.ACE);
    }

    @Override
    public String toString() {
        return "Card{" +
                "rank=" + rank +
                ", suit=" + suit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return getRank() == card.getRank() && getSuit() == card.getSuit();
    }
}