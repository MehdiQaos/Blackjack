package org.mehdi.gameEngine.models;

public class DealerHand extends Hand {
    public int evaluate17() {
        int score = cards.stream()
                         .mapToInt(card -> card.getRank().getValue())
                         .sum();
        int numAces = numberOfAces();
        while (score != 21 && score > 17 && numAces > 0) {
            score -= 10;
            numAces--;
        }

        return score;
    }

    @Override
    public int evaluate() { // hit on soft
        int score = cards.stream()
                         .mapToInt(card -> card.getRank().getValue())
                         .sum();
        int numAces = numberOfAces();
        while (numAces-- > 0)
            score -= 10;
        return score;
    }
}
