package org.mehdi.gameEngine;

import org.mehdi.gameEngine.enumerations.Result;
import org.mehdi.gameEngine.utils.Pair;
import org.mehdi.gameEngine.utils.Response;
import org.mehdi.gameEngine.enumerations.State;
import org.mehdi.gameEngine.models.*;

import java.util.List;

public class BlackJack implements IBlackJack {
    private final Deck deck;
    private final Tray tray;
    private int round;
    private final Player player;

    private final DealerHand dealerHand;

    public BlackJack(Player player) {
        this.player = player;
        this.dealerHand = new DealerHand();
        this.round = 0;
        this.deck = new Deck();
        this.tray = new Tray();
    }

    public boolean isNotPlaying() {
        return player.currentHand() == null;
    }

    public Response startRound(int bet) {
        if (!isNotPlaying()) {
            return invalidResponse().build();
        }
        if (bet <= 0) {
            return invalidResponse().state(State.BET).build();
        }
        if (bet > player.getBank()) {
            return invalidResponse().state(State.BANK).build();
        }
        clearPlayedCards();
        round++;
        PlayerHand playerHand = initialDeal(bet);
        if (playerHand.evaluate() == 21 || dealerHand.evaluate() == 21) {
            return handleInitialDealBlackJack(playerHand);
        }
        return validResponseBuilder(playerHand).state(State.PLAYING).build();
    }

    private Response handleInitialDealBlackJack(PlayerHand playerHand) {
        int dealerHandValue = dealerHand.evaluate();
        int playerHandValue = playerHand.evaluate();
        var responseBuilder = validResponseBuilder(playerHand).state(State.BLACKJACK);
        if (dealerHandValue == 21 && playerHandValue == 21) {
            responseBuilder.result(Result.DRAW);
        } else if (dealerHandValue == 21) {
            responseBuilder.result(Result.LOSE);
        } else {
            responseBuilder.result(Result.WIN);
        }
        return responseBuilder.build();
    }

    private PlayerHand initialDeal(int bet) {
        PlayerHand newHand = player.addHand(bet);
        dealCardToPlayer();
        dealCardToDealer();
        dealCardToPlayer();
        dealCardToDealer();
        return newHand;
    }

    private void ifEmptyMakeDeckFromTray() {
        if (!deck.isEmpty()) {
            return;
        }
        deck.addMultipleCards(tray.clear());
        deck.shuffleCards();
    }

    private Card dealCard() {
        ifEmptyMakeDeckFromTray();
        return deck.drawCard();
    }

    private Pair<PlayerHand, Card> dealCardToPlayer() {
        Card card = dealCard();
        PlayerHand hand = player.dealCard(card);
        return new Pair<>(hand, card);
    }

    private Card dealCardToDealer() {
        Card card = dealCard();
        dealerHand.add(card);
        return card;
    }

    public Response hit() {
        if (isNotPlaying()) {
            return invalidResponse().build();
        }
        Card dealedCard;
        PlayerHand currentHand;
        try {
            Pair<PlayerHand, Card> handAndCard = dealCardToPlayer();
            currentHand = handAndCard.first;
            dealedCard = handAndCard.second;
        } catch (IllegalStateException e) {
            return invalidResponse().build();
        }

        int handValue = currentHand.evaluate();
        Response.Builder responseBuilder = validResponseBuilder(currentHand);

        if (handValue > 21) {
            return handleTheHand();
        } else if (handValue == 21) {
            playDealer();
            return handleTheHand();
        } else {
            responseBuilder.state(State.PLAYING);
        }
        return responseBuilder.build();
    }

    public void playDealer() {
        while (dealerHand.evaluate() < 17) {
            dealCardToDealer();
        }
    }

    public Response stand() {
        if (isNotPlaying()) {
            return invalidResponse().build();
        }
        playDealer();
        return handleTheHand();
    }

    // TODO: find better name
    private Response handleTheHand() {
        PlayerHand playerHand = player.currentHand();
        int playerHandValue = playerHand.evaluate();
        int dealerHandValue = dealerHand.evaluate();
        var responseBuilder = validResponseBuilder(playerHand);

        if (playerHandValue > 21) {
            player.adjustBankBy(-playerHand.getBet() * 2); // TODO: make another method like winBet, loseBet
            responseBuilder.state(State.BUST).result(Result.LOSE);
        } else if (playerHandValue == dealerHandValue) {
            responseBuilder.result(Result.DRAW);
            if (playerHandValue == 21)
                responseBuilder.state(State.BLACKJACK);
        } else if (dealerHandValue > 21) {
            player.adjustBankBy(playerHand.getBet() * 2);
            responseBuilder.state(State.BUST).result(Result.WIN);
        } else if (dealerHandValue < playerHandValue) {
            player.adjustBankBy(playerHand.getBet() * 2);
            responseBuilder.state(State.BIGGER).result(Result.WIN);
        } else {
            player.adjustBankBy(-playerHand.getBet() * 2);
            responseBuilder.state(State.BIGGER).result(Result.LOSE);
        }

        playerHand.deactivate();
        clearPlayedCards(); // if no more hands
        return responseBuilder.build();
    }

    private void clearPlayedCards() {
        if (!isNotPlaying())
            return;
        tray.addCards(player.clearHands());
        tray.addCards(dealerHand.clearHand());
    }

    // TODO: move this method to the Response class?
    private Response.Builder invalidResponse() {
        return new Response.Builder()
            .round(round)
            .valid(false);
    }

    private Response.Builder validResponseBuilder(PlayerHand hand) {
        return new Response.Builder()
            .valid(true)
            .round(round)
            .playerCards(hand.getCards())
            .dealerCards(dealerHand.getCards())
            .playerEvaluation(hand.evaluate())
            .dealerEvaluation(dealerHand.evaluate());
    }

    public Response doubleDown() {
        return invalidResponse().build();
    }

    public Response surrender() {
        return invalidResponse().build();
    }

    public Response split() {
        return invalidResponse().build();
    }

    public static boolean insertCard(List<Card> cards, Card cardToInsert, int index) {
        if (cards.size() == 52 ||
                cards.stream().anyMatch(card -> card == cardToInsert))
            return false;

        cards.add(index, cardToInsert);
        return true;
    }
}
