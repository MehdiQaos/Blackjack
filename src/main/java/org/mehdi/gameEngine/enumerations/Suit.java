package org.mehdi.gameEngine.enumerations;

public enum Suit {
    SPADES("Spades", '♠', Color.BLACK),
    CLUBS("Clubs", '♣', Color.BLACK),
    DIAMONDS("Diamonds", '♦', Color.RED),
    HEARTS("Hearts", '♥', Color.RED);

    private final String name;
    private final Color color;
    private final char symbol;

    Suit(String name, char symbol, Color color) {
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