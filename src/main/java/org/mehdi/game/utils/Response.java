package org.mehdi.game.utils;

import org.mehdi.game.Card;
import org.mehdi.game.enumerations.Result;
import org.mehdi.game.enumerations.State;

import java.util.List;

public class Response {
    public final boolean valid;
    public final int round;
    public final Result result;
    public final State state;
    public final int playerEvaluation;
    public final int dealerEvaluation;
    public final List<Card> playerCards;
    public final List<Card> dealerCards;

    public Response(Builder builder) {
        this.valid = builder.valid;
        this.round = builder.round;
        this.state = builder.state;
        this.result = builder.result;
        this.playerEvaluation = builder.playerEvaluation;
        this.dealerEvaluation = builder.dealerEvaluation;
        this.playerCards = builder.playerCards;
        this.dealerCards = builder.dealerCards;
    }

    public boolean isValid() {
        return valid;
    }

    public int getRound() {
        return round;
    }

    public State getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Response{" +
                "valid=" + valid +
                ", round=" + round +
                ", result=" + result +
                ", state=" + state +
                ", playerVal=" + playerEvaluation +
                ", dealerVal=" + dealerEvaluation +
                ", playerCards=" + playerCards +
                ", dealerCards=" + dealerCards +
                '}';
    }

    public static class Builder {
        private boolean valid;
        private int round;
        private State state;
        private Result result;
        private int playerEvaluation;
        private int dealerEvaluation;
        private List<Card> playerCards;
        private List<Card> dealerCards;

        public Builder valid(boolean valid) {
            this.valid = valid;
            return this;
        }

        public Builder round(int round) {
            this.round = round;
            return this;
        }

        public Builder state(State state) {
            this.state = state;
            return this;
        }

        public Builder result(Result result) {
            this.result = result;
            return this;
        }

        public Builder playerCards(List<Card> cards) {
            this.playerCards = cards;
            return this;
        }

        public Builder dealerCards(List<Card> cards) {
            this.dealerCards = cards;
            return this;
        }

        public Builder playerEvaluation(int eval) {
            this.playerEvaluation = eval;
            return this;
        }

        public Builder dealerEvaluation(int eval) {
            this.dealerEvaluation = eval;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
