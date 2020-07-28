import java.io.IOException;
import java.util.Scanner;

public class Blackjack {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private static final int BLACKJACK = 21;

    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final Deck deck = new Deck();

        opening(scanner);
        try {
            game(scanner, deck);
        }
        catch (IOException e) {}
        catch (InterruptedException e) {}

        scanner.close();
    }

    private static void opening(final Scanner scanner) {
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
                    playerCount += ace(x, playerCount, scanner);
                } else {
                    playerCount += x.getRank();
                }
                playerCards += x.getCard();
            } else {
                if (x.getRank() == -1) {
                    dealerCount += 11; // As dealers first card, no reason for it to be 1
                } else {
                    dealerCount += x.getRank();
                }
                dealerCards += x.getCard();
            }
            isPlayerTurn = !isPlayerTurn;
        }

        printCounts(playerCards, dealerCards, playerCount, dealerCount);
        if (checkForBlackjack(playerCards, playerCount, scanner)) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            main(null);
        }

        System.out.println("Do you want to " + ANSI_BLUE + "H" + ANSI_RESET + "it or " + ANSI_BLUE + "S" + ANSI_RESET + "tick?");
        String choice = scanner.nextLine();
        while (choice.toLowerCase().equals("h")) {
            final Card x = deck.deal();
            if (x.getRank() == -1 && playerCount <= 10) {
                playerCount += ace(x, playerCount, scanner);
            } else if (x.getRank() == -1 && playerCount > 10) {
                playerCount += 1;
            } else {
                playerCount += x.getRank();
            }
            playerCards += x.getCard();

            printCounts(playerCards, playerCount);

            if (checkForGameOver(playerCards, playerCount, scanner)) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                main(null);
            }

            System.out.println("Do you want to " + ANSI_BLUE + "H" + ANSI_RESET + "it or " + ANSI_BLUE + "S" + ANSI_RESET + "tick?");
            choice = scanner.nextLine();
        }

    }

    private static int ace(final Card x, final int playerCount, final Scanner scanner) {
        System.out.println("You have been dealt the " + x.getCard() + ", you currently have " + playerCount
                + ", do you want " + ANSI_BLUE + "11 " + ANSI_RESET + "or " + ANSI_BLUE + "1" + ANSI_RESET + "?");
        String input = scanner.nextLine();
        while (!input.equals("11") && !input.equals("1")) {
            System.out.println("Invalid input. You have been dealt the " + x.getCard() + ", you currently have "
                    + playerCount + ", do you want " + ANSI_BLUE + "11 " + ANSI_RESET + "or " + ANSI_BLUE + "1"
                    + ANSI_RESET + "?");
            input = scanner.nextLine();
        }
        final int choice = Integer.parseInt(input);
        return choice;
    }

    private static void printCounts(final String playerCards, final String dealerCards, final int playerCount,
            final int dealerCount) {
        System.out.println("Dealers cards: " + dealerCards + " (" + dealerCount + ") \n\n");
        System.out.println("Players cards: " + playerCards + " (" + playerCount + ")");
    }

    private static void printCounts(final String playerCards, final int playerCount) {
        System.out.println("Players cards: " + playerCards + " (" + playerCount + ")");
    }

    private static boolean checkForBlackjack(final String playerCards, final int playerCount, final Scanner scanner) {
        if (playerCount == BLACKJACK) {
            System.out.println("BLACKJACK! Your cards: " + playerCards + ", do you want to play again?\n\t" + ANSI_BLUE
                    + "1. " + ANSI_RESET + "Yes.\n\t" + ANSI_BLUE + "2. " + ANSI_RESET + "No. [DEFAULT]");
            final String input = scanner.nextLine();
            if (input.equals("1") || input.equals("1.")) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkForGameOver(final String playerCards, final int playerCount, final Scanner scanner) {
        if (playerCount > BLACKJACK) {
            System.out.println("");
            System.out.println("BUST! Your cards are " + playerCards + " (" + playerCount
                    + "), do you want to play again?\n\t" + ANSI_BLUE + "1. " + ANSI_RESET + "Yes.\n\t" + ANSI_BLUE
                    + "2. " + ANSI_RESET + "No. [DEFAULT]");
            final String input = scanner.nextLine();
            if (input.equals("1") || input.equals("1.")) {
                return true;
            }
        }
        return false;
    }

}
