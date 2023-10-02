package org.mehdi.game.utils;

public class Response {
    private final boolean valid;
    private final int round;
    private final int state;

    public Response(Builder builder) {
        this.valid = builder.valid;
        this.round = builder.round;
        this.state = builder.state;
    }

    public boolean isValid() {
        return valid;
    }

    public int getRound() {
        return round;
    }

    public int getState() {
        return state;
    }

    public static class Builder {
        private boolean valid;
        private int round;
        private int state;

        public Builder valid(boolean valid) {
            this.valid = valid;
            return this;
        }

        public Builder round(int round) {
            this.round = round;
            return this;
        }

        public Builder state(int state) {
            this.state = state;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
