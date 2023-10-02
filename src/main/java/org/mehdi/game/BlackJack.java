package org.mehdi.game;

import org.mehdi.game.utils.Response;

import java.util.List;

public class BlackJack {
    private Deck deck;
    private Tray tray;
    private int round;
    private boolean isPlaying;
    private Player player;

    private Dealer dealer;

    public BlackJack(Player player) {
        this.player = player;
        this.dealer = new Dealer();
        this.round = 0;
        this.isPlaying = false;
        this.deck = new Deck();
        this.tray = new Tray();
    }

    public Response startRound(int bet) {
        if (isPlaying) {
            return new Response.Builder().valid(false).round(round).build();
        }
        player.addHand(bet);
        isPlaying = true;
        round++;
        return new Response.Builder().valid(true).round(round).build();
    }

    private void ifEmptyMakeDeckFromTray() {
        if (!deck.isEmpty()) {
            return;
        }
        deck.addMultipleCards(tray.clear());
        deck.shuffleCards();
    }

    private int dealCardToPlayer() {
        ifEmptyMakeDeckFromTray();
        Card dealedCard = deck.drawCard();
        return player.dealCard(dealedCard);
    }

    private int dealCardToDealer() {
        ifEmptyMakeDeckFromTray();
        Card dealedCard = deck.drawCard();
        return player.dealCard(dealedCard);
    }

    public Response hit() {
        if (! isPlaying) {
            return new Response.Builder().valid(false).round(round).build();
        }
        int handValue;
        try {
            handValue = dealCardToPlayer();
        } catch (IllegalStateException e) {
            return new Response.Builder().valid(false).round(round).build();
        }
        if (handValue > 21) {
            player.bustHand();

            return new Response.Builder()
                    .valid(true)
                    .round(round)
                    .state(2)
                    .build();
        }
    }

    public void push() {}
    public void doubleDown() {}
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
