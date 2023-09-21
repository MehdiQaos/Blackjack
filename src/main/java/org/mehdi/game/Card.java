package org.mehdi.game;

import java.util.Objects;

public class Card {
    public static final int MAX_NUMBER = 13;
    public static final int MAX_COLOR = 4;
    public static final int NUM_CARDS = MAX_NUMBER * MAX_COLOR;
    private final int number;
    private final int color;

    public Card(int number, int color) {
        this.number = number;
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public int getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Card{" +
                "number=" + number +
                ", color=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return getNumber() == card.getNumber() && getColor() == card.getColor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getColor());
    }
}
