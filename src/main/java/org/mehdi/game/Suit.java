package org.mehdi.game;

public enum Suit {
    SPADES("Spades", 's', Color.BLACK),
    CLUBS("Clubs", 'c', Color.BLACK),
    DIAMONDS("Diamonds", 'd', Color.RED),
    HEARTS("Hearts", 'h', Color.RED);

    private final String name;
    private final Color color;
    private final char symbol;

    private Suit(String name, char symbol, Color color) {
        this.name = name;
        this.color = color;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public char getSymbol() {
        return symbol;
    }
}