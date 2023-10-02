package org.mehdi.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int bank;
    private String name;
    private List<Hand> hands;

    public Player(int bank, String name) {
        this.bank = bank;
        this.name = name;
        hands = new ArrayList<>();
    }

    public void addHand(int bet) {
        if (bet > bank) {
            return;
        }
        bank -= bet;
        Hand newHand = new Hand(bet);
        hands.add(newHand);
    }

    public int dealCard(Card newCard) {
        Hand currentHand = nextActiveHand();
        if (currentHand == null) {
            throw new IllegalStateException("No active hand available to deal a card.");
        }
        currentHand.add(newCard);
        return currentHand.evaluate();
    }

    public void bustHand() {
        Hand currentHand = nextActiveHand();
        if (currentHand == null) {
            throw new IllegalStateException("No active hand available to bust.");
        }
        currentHand.deactivate();
    }

    public boolean checkCurrentHand() {
        Hand currentHand = nextActiveHand();
        return currentHand != null;
    }

    public void dealCardToHand(Card card, int handNumber) {
        int handIndex = handNumber - 1;
        Hand hand = hands.get(handIndex);
        hand.add(card);
    }

    public Hand nextActiveHand() {
        // TODO: use streams api
        for (Hand hand: hands) {
            if (hand.isActive()) {
                return hand;
            }
        }
        return null;
    }

    public List<Card> clearHands() {
        List<Card> discardedCards = new ArrayList<>();
        for (var hand: hands) {
            discardedCards.addAll(hand.clearHand());
        }
        hands.clear();
        return discardedCards;
    }

    public int numberOfHands() {
        return hands.size();
    }

    public int getBank() {
        return bank;
    }

    public String getName() {
        return name;
    }

    public void addCard(int handIndex, Card card) {
        hands.get(handIndex).add(card);
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Hand> getHands() {
        return hands;
    }
}