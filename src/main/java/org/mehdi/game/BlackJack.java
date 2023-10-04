package org.mehdi.game;

import org.mehdi.game.enumerations.Result;
import org.mehdi.game.utils.Pair;
import org.mehdi.game.utils.Response;
import org.mehdi.game.enumerations.State;

import java.util.List;

public class BlackJack {
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
        // TODO: pioche
    }

    public boolean isNotPlaying() {
        return player.currentHand() == null;
    }

    public Response startRound(int bet) {
        if (!isNotPlaying()) {
            return invalidResponse();
        }
        clearPlayedCards();
        round++;
        PlayerHand playerHand = initialDeal(bet);
        if (playerHand.evaluate() == 21 || dealerHand.evaluate() == 21) {
            return handleFirstDealBlackJack(playerHand);
        }
        return validResponseBuilder(playerHand).state(State.PLAYING).build();
    }

    private Response handleFirstDealBlackJack(PlayerHand playerHand) {
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

    // TODO: should hitting and getting blackjack and winning, return state blackjack or bigger?
    public Response hit() {
        if (isNotPlaying()) {
            return invalidResponse();
        }
        Card dealedCard;
        PlayerHand currentHand;
        try {
            Pair<PlayerHand, Card> handAndCard = dealCardToPlayer();
            currentHand = handAndCard.first;
            dealedCard = handAndCard.second;
        } catch (IllegalStateException e) {
            return invalidResponse();
        }

        int handValue = currentHand.evaluate();
        Response.Builder responseBuilder = validResponseBuilder(currentHand);

        if (handValue > 21) {
            currentHand.deactivate();
            responseBuilder.state(State.BUST).result(Result.LOSE);
            clearPlayedCards();
        } else if (handValue == 21) {
            return handleTheThing();
        } else {
            responseBuilder.state(State.PLAYING);
        }
        return responseBuilder.build();
    }

    public int playDealer() {
        while (dealerHand.evaluate() < 21)
            dealCardToDealer();
        return dealerHand.evaluate();
    }

    public Response stand() {
        if (isNotPlaying()) {
            return invalidResponse();
        }
        return handleTheThing();
    }

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

    // TODO: find better name
    private Response handleTheThing() {
        PlayerHand currentHand = player.currentHand();
        currentHand.deactivate();
        int playerHandValue = currentHand.evaluate();
        int dealerHandValue = playDealer();

        var responseBuilder = validResponseBuilder(currentHand);
        if (dealerHandValue > 21) {
            player.adjustBankBy(currentHand.getBet() * 2);
            responseBuilder.state(State.BUST).result(Result.WIN);
        } else if (dealerHandValue < playerHandValue) {
            player.adjustBankBy(currentHand.getBet() * 2);
            responseBuilder.state(State.BIGGER).result(Result.WIN);
        } else {
            player.adjustBankBy(-currentHand.getBet() * 2);
            responseBuilder.state(State.BIGGER).result(Result.LOSE);
        }

        clearPlayedCards();
        return responseBuilder.build();
    }

    private void clearPlayedCards() {
        if (!isNotPlaying())
            return;
        tray.addCards(player.clearHands());
        tray.addCards(dealerHand.clearHand());
    }

    // TODO: move this method to the Response class?
    private Response invalidResponse() {
        return new Response.Builder().round(round).valid(false).build();
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

    public void doubleDown() {
    }

    public void surrender() {
    }

    public void split() {
    }

    public static boolean insertCard(List<Card> cards, Card cardToInsert, int index) {
        if (cards.size() == 52 ||
                cards.stream().anyMatch(card -> card == cardToInsert))
            return false;

        cards.add(index, cardToInsert);
        return true;
    }
}
