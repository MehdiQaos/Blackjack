package org.mehdi.game;

import org.mehdi.game.utils.Response;
import org.mehdi.game.enumerations.State;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int bank;
    private String name;
    private List<PlayerHand> hands;

    public Player(int bank, String name) {
        this.bank = bank;
        this.name = name;
        hands = new ArrayList<>();
    }

    public PlayerHand addHand(int bet) {
        if (bet > bank) {
            return null;
        }
        bank -= bet;
        PlayerHand newHand = new PlayerHand(bet);
        hands.add(newHand);
        return newHand;
    }

    public Response hit(Card newCard, int round) {
        PlayerHand currentHand = dealCard(newCard);
        int handValue = currentHand.evaluate();
        Response.Builder builder = new Response.Builder()
                .valid(true)
                .round(round);
        if (handValue > 21) {
            currentHand.deactivate();
            return builder.state(State.BUST)
                .playerCards(currentHand.getCards())
                .build();
        } else if (handValue == 21) {
            currentHand.deactivate();
            builder.state(State.BLACKJACK);
        }
        return builder.state(State.PLAYING)
                .playerCards(currentHand.getCards())
                .build();
    }

    public PlayerHand dealCard(Card newCard) {
        PlayerHand currentHand = currentHand();
        if (currentHand == null) {
            throw new IllegalStateException("No active hand available to deal a card.");
        }
        currentHand.add(newCard);
        return currentHand;
    }

    public void bustHand() {
        PlayerHand currentHand = currentHand();
        if (currentHand == null) {
            throw new IllegalStateException("No active hand available to bust.");
        }
        currentHand.deactivate();
    }

    public boolean checkCurrentHand() {
        PlayerHand currentHand = currentHand();
        return currentHand != null;
    }

    public void dealCardToHand(Card card, int handNumber) {
        int handIndex = handNumber - 1;
        PlayerHand hand = hands.get(handIndex);
        hand.add(card);
    }

    public PlayerHand currentHand() {
        // TODO: use streams api
        for (PlayerHand hand: hands) {
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

    public void adjustBankBy(int money) {
        if (-money > bank) {
            throw new IllegalArgumentException("money not enough in the bank");
        }
        bank += money;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayerHand> getHands() {
        return hands;
    }
}