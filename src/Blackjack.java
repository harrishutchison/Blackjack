import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Blackjack {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final Deck deck = new Deck();

        try {
            opening(scanner);
            game(scanner, deck);
        } catch (IOException e) {
        } catch (InterruptedException e) {
        }

        scanner.close();
    }

    private static void opening(final Scanner scanner) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println(
                "Welcome to Blackjack. Do you want to read the rules for the European ruleset?\n\t" + ANSI_BLUE + "1. "
                        + ANSI_RESET + "Yes.\n\t" + ANSI_BLUE + "2. " + ANSI_RESET + "No, deal me in! [DEFAULT]");
        final String line = scanner.nextLine();
        if (line.equals("1") || line.equals("1.")) {
            rules();
        }
    }

    private static void rules() {

    }

    private static void game(final Scanner scanner, final Deck deck) throws IOException, InterruptedException {
        int playerCount = 0, dealerCount = 0;
        String playerCards = "", dealerCards = "";

        deck.shuffle();

        boolean isPlayerTurn = true;

        for (int i = 0; i < 3; i++) {
            final Card x = deck.deal();
            if (isPlayerTurn) {
                if (x.getRank() == -1) {
                    playerCount += Output.ace(x, playerCount, scanner);
                } else {
                    playerCount += x.getRank();
                }
                playerCards += x.getCard();
            } else {
                if (x.getRank() == -1) {
                    dealerCount += 11; // As it is dealers first card, no reason for it to be 1
                } else {
                    dealerCount += x.getRank();
                }
                dealerCards += x.getCard();
            }
            isPlayerTurn = !isPlayerTurn;
        }

        Output.printCounts(playerCards, dealerCards, playerCount, dealerCount);
        if (Output.checkForBlackjack(playerCards, playerCount, scanner)) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            main(null);
        }

        /**
         * Player
         */

        System.out.println(
                "Do you want to " + ANSI_BLUE + "H" + ANSI_RESET + "it or " + ANSI_BLUE + "S" + ANSI_RESET + "tick?");
        String choice = "";
        if (scanner.hasNextLine()) {
            choice = scanner.nextLine();
        }
        System.out.println("\n");
        while (!(choice.toLowerCase().equals("s"))) { // Waiting for stick
            if (choice.toLowerCase().equals("h")) {
                final Card x = deck.deal();
                if (x.getRank() == -1 && playerCount <= 10) {
                    playerCount += Output.ace(x, playerCount, scanner);
                } else if (x.getRank() == -1 && playerCount > 10) {
                    playerCount += 1;
                } else {
                    playerCount += x.getRank();
                }
                playerCards += x.getCard();

                Output.printCounts(playerCards, playerCount, true);

                if (Output.checkForGameOver(playerCards, playerCount, scanner)) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    main(null);
                }
            }
            System.out.println("Do you want to " + ANSI_BLUE + "H" + ANSI_RESET + "it or " + ANSI_BLUE + "S"
                    + ANSI_RESET + "tick?");
            if (scanner.hasNextLine()) {
                choice = scanner.nextLine();
            }
            System.out.println("\n");
        }

        /**
         * Dealer
         */
        
        System.out.println("Dealer is hitting");

        TimeUnit.SECONDS.sleep(1);
        boolean dealerDone = false;

        while (!dealerDone) {
            final Card x = deck.deal();
            if (x.getRank() == -1) {
                if ((dealerCount + 11) >= 17 && (dealerCount + 11) <= 21) {
                    dealerCount += 11;
                } else {
                    dealerCount += 1; // If dealer already has an Ace, then taking 11 will mean the dealer is bust
                }
            } else {
                dealerCount += x.getRank();
            }
            dealerCards += x.getCard();
            Output.printCounts(dealerCards, dealerCount, false);
            System.out.println("\n");

            if (dealerCount >= 17) {
                dealerDone = true;
            } else {
                System.out.println("Dealer is hitting");
            }

            TimeUnit.SECONDS.sleep(1);
        }

        if (Output.winner(playerCards, dealerCards, playerCount, dealerCount, scanner)) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            main(null);
        }
    }

}
