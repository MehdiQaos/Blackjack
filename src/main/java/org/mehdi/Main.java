package org.mehdi;

import org.mehdi.game.BlackJack;
import org.mehdi.game.Deck;
import org.mehdi.game.Player;
import org.mehdi.game.Suit;
import org.mehdi.game.utils.Response;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        test2();
// //        pioche test
//        Deck deck = new Deck();
//        var piq = deck.pioche();
//        System.out.println(piq.first.size());
//        System.out.println(piq.second);
//        test();
//        var cards = nextCardsList(new Card(1, 2));
//        shuffleCards(cards);
//        for (var card: cards) {
//            System.out.println(card);
//        }

//        int bank = 1000;
//        String name = "Mehdi";
//        Player player = new Player(bank, name);
//        BlackJack game = new BlackJack(player);
//        while (true) {
//
//        }
    }
    public static void test2() {
        int bank = 1000;
        String name = "mehdi";
        Player player = new Player(bank, name);
        BlackJack blackJack = new BlackJack(player);
        Response response = blackJack.startRound(50);
        System.out.println(response);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("give choice (1.hit, 2.stand): ");
            int input = scanner.nextInt();
            switch (input) {
                case 1 -> {
                    response = blackJack.hit();
                    System.out.println(response);
                }
                case 2 -> {
                    response = blackJack.stand();
                    System.out.println(response);
                }
                case 3 -> {
                    response = blackJack.startRound(50);
                    System.out.println(response);
                }
            }
        }
    }

//    public static List<Card> nextCardsList(Card startingCard) {
//        int number = startingCard.getNumber();
//        int color = startingCard.getColor();
//        List<Card> cards = new ArrayList<>();
//
//        for (int j = color; j <= Card.MAX_COLOR; ++j) {
//            for (int i = number + 1; i <= Card.MAX_NUMBER; ++i) {
//                cards.add(new Card(i, j));
//            }
//            number = 0;
//        }
//
//        return cards;
//    }
//
//    public static List<Card> createCards() {
//        Card firstCard = new Card(1, 1);
//        List<Card> cards = nextCardsList(firstCard);
//        cards.add(0, firstCard);
//        return cards;
//    }
//
//    public static Card popIthCard(List<Card> cards, int i) {
//        if (i < 0 || i >= cards.size())
//            return null;
//        return cards.remove(i);
//    }
//
//    public static Card popRandomCard(List<Card> cards) {
//        Random random = new Random();
//        int maxRange = cards.size();
//        int index = random.nextInt(maxRange);
//        return popIthCard(cards, index);
//    }
//
//    public static void shuffleCards(List<Card> cards) {
//        for (int i = 0; i < cards.size(); ++i) {
//            Card card = popRandomCard(cards);
//            cards.add(0, card);
//        }
//    }

    private static void test() {
//        var cards = BlackJack.nextCardsList(new Card(12, 3));
//        var allCards = BlackJack.createCards();
//        BlackJack.shuffleCards(allCards);
//        for (var card: allCards) {
//            System.out.println(card);
//        }
//        System.out.println(allCards.size());
//        System.out.println(BlackJack.popIthCard(allCards, 51));
//        System.out.println(BlackJack.popRandomCard(allCards));

        Deck deck = new Deck();
        var drawedCards = deck.drawMultipleCards(4);
//        for (var card: drawedCards) {
//            System.out.println(card);
//        }
        for (var card: deck.cards) {
            System.out.println(card);
        }
    }
}