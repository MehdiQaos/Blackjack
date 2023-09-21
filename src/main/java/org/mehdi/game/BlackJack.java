package org.mehdi.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlackJack {
    private Deck deck;
    private int round;
    private boolean isPlaying;
    private Player player;

    public BlackJack(Player player) {
        this.player = player;
        round = 0;
        isPlaying = false;
        playedCards = new ArrayList<Card>();
        cards = new ArrayList<Card>();
        for (int i = 1; i <= Card.MAX_COLOR; ++i) {
            for (int j = 1; j <= Card.MAX_NUMBER; ++j) {
                cards.add(new Card(j, i));
            }
        }
    }

    public void hit() {}
    public void stand() {}
    public void surrender() {}
    public void split() {}

    public static boolean insertCard(List<Card> cards, Card cardToInsert, int index) {
        if (cards.size() == 52 ||
                cards.stream().anyMatch(card -> card == cardToInsert))
            return false;

        cards.add(index, cardToInsert);
        return true;
    }
}
