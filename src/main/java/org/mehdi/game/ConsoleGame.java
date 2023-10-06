package org.mehdi.game;

import org.mehdi.gameEngine.BlackJack;
import org.mehdi.gameEngine.IBlackJack;
import org.mehdi.gameEngine.enumerations.Rank;
import org.mehdi.gameEngine.enumerations.Result;
import org.mehdi.gameEngine.models.Card;
import org.mehdi.gameEngine.models.Player;
import org.mehdi.gameEngine.utils.Pair;
import org.mehdi.gameEngine.utils.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleGame {
    private IBlackJack blackJack;
    private final Scanner scanner = new Scanner(System.in);
    private String playerName;
    private int bet;
    private boolean playing;

    public void run() {
        welcome();
        initGame();
        while (playing) {
            System.out.println("-----------------------------------------");
            System.out.println("Options:");
            System.out.println("1. New round");
            System.out.println("2. Quit");
            System.out.print("Please choose an option : ");
            String playerChoice = scanner.nextLine().trim().toLowerCase();

            switch (playerChoice) {
                case "1", "n" -> {
                    if (playRound()) {
                        System.out.println("Quiting..");
                        playing = false;
                    }
                }
                case "2", "q" -> {
                    System.out.println("Quiting..");
                    playing = false;
                }
                default -> {
                    System.out.println("Invalid choice. Please enter 1, 2.");
                }
            }
        }
    }

    private boolean playRound() {
        bet = takeBet(blackJack.getBank());
        Response response = blackJack.startRound(bet);
        printScreen(response);
        while (true) {
            System.out.println("------------------------------------------------");
            System.out.println("Options:");
            System.out.println("1. Hit");
            System.out.println("2. Stand");
            System.out.println("3. Quit");
            System.out.print("Please choose an option : ");
            String playerChoice = scanner.nextLine().trim().toLowerCase();
            switch (playerChoice) {
                case "1" -> {
                    if (handleHit())
                        return false;
                }
                case "2" -> {
                    if (handleStand())
                        return false;
                }
                case "3" -> {
                    return true;
                }
                default -> {
                    System.out.println("invalid input.");
                }
            }
        }
    }

    private void printScreen(Response response) {
        var result = response.result;
        // TODO: factor this
        System.out.printf("bank: %-40s bet: %s%n", blackJack.getBank(), bet);
        System.out.println(cardsToString(response.dealerCards));
        System.out.println("------------------------------------------------");
        System.out.println(cardsToString(response.playerCards));
        if (!result.equals(Result.NULL)) {
            System.out.println(response.state);
            System.out.println("You " + result.toString().toLowerCase());
        }
        if (!result.equals(Result.NULL))
            System.out.println("Dealer hand value: " + response.dealerEvaluation);
        System.out.println("Player hand value: " + response.playerEvaluation);
        System.out.println("Press Enter to continue...");
        scanner.nextLine();

    }

    private boolean handleHit() {
        Response response = blackJack.hit();
        printScreen(response);
        return response.result != Result.NULL;
    }

    private boolean handleStand() {
        Response response = blackJack.stand();
        printScreen(response);
        return response.result != Result.NULL;
    }

    private void initGame() {
        int bank;
        var info = takePlayerInfo();
        playerName = info.first;
        bank = info.second;
        Player player = new Player(bank, "mehdi");
        blackJack = new BlackJack(player);
        playing = true;
    }

    private int takeBet(int bank) {
        int bet = 0;
        while (true) {
            System.out.println("Enter bet amount: ");
            try {
                bet = Integer.parseInt(scanner.nextLine());
                if (bet > 0 && bet <= bank)
                    break;
                System.out.printf("bet amount should be positive and less than %d%n", bank);
            } catch (NumberFormatException e) {
                System.out.println("invalid input, please type an integer");
            }
        }
        return bet;
    }

    private Pair<String, Integer> takePlayerInfo() {
        int bank;
        String name;
        System.out.print("Enter your name: ");
        name = scanner.nextLine().trim();
        while (true) {
            System.out.print("Enter the bank amount (min bank 1000): ");
            try {
                bank = Integer.parseInt(scanner.nextLine());
                if (bank >= 1000)
                    break;
                System.out.println("invalid amount.");
            } catch (NumberFormatException e) {
                System.out.println("invalid input, please type an integer");
            }
        }
        return new Pair<>(name, bank);
    }

    private String cardsToString(List<Card> cards) {
        // TODO: use string builder 
        List<String> rows = new ArrayList<>();
        int cardWidth = 5;
        int gap = 1;
        String s = " " + "_".repeat(cardWidth) + " " + " ".repeat(gap);
        String cardTop = s.repeat(cards.size());
        rows.add(cardTop);
        s = "";
        for (var card: cards) {
            boolean cond = card.getRank().equals(Rank.TEN);
            s += "|" + card.getRank().getSymbol() + " ".repeat(cond? cardWidth - 2: cardWidth - 1) + "|" + " ".repeat(gap);
        }
        rows.add(s);
        s = "|" + " ".repeat(cardWidth) + "|" + " ".repeat(gap);
        s = s.repeat(cards.size());
        rows.add(s);
        s = "";
        for (var card: cards) {
            s += "|" + " ".repeat((cardWidth - 1) / 2) + card.getSuit().getSymbol() + " ".repeat((cardWidth - 1) / 2) + "|" + " ".repeat(gap);
        }
        rows.add(s);
        s = "|" + " ".repeat(cardWidth) + "|" + " ".repeat(gap);
        s = s.repeat(cards.size());
        rows.add(s);
        s = "";
        for (var card: cards) {
            boolean cond = card.getRank().equals(Rank.TEN);
            s += "|" + "_".repeat(cond? cardWidth - 2: cardWidth - 1) + card.getRank().getSymbol() + "|" + " ".repeat(gap);
        }
        rows.add(s);
        return String.join("\n", rows);
    }

    private void welcome() {
        String screen = """
                 _     _            _    _            _   \s
                | |   | |          | |  (_)          | |  \s
                | |__ | | __ _  ___| | ___  __ _  ___| | __
                | '_ \\| |/ _` |/ __| |/ / |/ _` |/ __| |/ /
                | |_) | | (_| | (__|   <| | (_| | (__|   <\s
                |_.__/|_|\\__,_|\\___|_|\\_\\ |\\__,_|\\___|_|\\_\\
                                       _/ |               \s
                                      |__/     \s
                """;
        System.out.println(screen);
    }
}
