import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Blackjack {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final Deck deck = new Deck();

        try {
            opening(scanner);
            game(scanner, deck);
        } catch (final IOException e) {
        } catch (final InterruptedException e) {
        }

        scanner.close();
    }

    private static void game(final Scanner scanner, final Deck deck) throws IOException, InterruptedException {
        final AtomicInteger playerCount = new AtomicInteger(0);
        final AtomicInteger dealerCount = new AtomicInteger(0);
        final StringBuilder playerCards = new StringBuilder();
        final StringBuilder dealerCards = new StringBuilder();

        deck.shuffle();
        Game.dealCards(deck, playerCards, dealerCards, playerCount, dealerCount, scanner);

        Output.printCounts(playerCards, dealerCards, playerCount, dealerCount);
        if (Output.checkForBlackjack(playerCards, playerCount, scanner)) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            main(null);
        }

        Game.playerTurn(deck, playerCards, playerCount, scanner);
        Game.dealerTurn(deck, dealerCards, dealerCount, scanner);

        if (Output.winner(playerCards, dealerCards, playerCount, dealerCount, scanner)) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            main(null);
        } else {
            System.out.println("Thanks for playing!");
            System.exit(0);
        }
    }

    private static void opening(final Scanner scanner) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println(
                "Welcome to Blackjack. Do you want to read the rules for the European ruleset?\n\t" + ANSI_BLUE + "1. "
                        + ANSI_RESET + "Yes.\n\t" + ANSI_BLUE + "2. " + ANSI_RESET + "No, deal me in! [DEFAULT]");
        final String line = scanner.nextLine();
        if (line.equals("1") || line.equals("1.")) {
            rules(scanner);
        }
    }

    private static void rules(final Scanner scanner) {
        System.out.println("\n" + ANSI_WHITE_BACKGROUND + ANSI_BLACK + "Basic Blackjack Rules:" + ANSI_RESET);
        System.out.println("\t- The goal of blackjack is to beat the dealer's hand without going over 21");
        System.out.println("\t- Face cards are worth 10. Aces are worth 1 or 11, whichever makes a better hand");
        System.out.println("\t- You start with 2 cards, and the dealer starts with 1");
        System.out.println("\t- To 'Hit' is to ask for another card. To 'Stand' is to hold your total and end your turn");
        System.out.println("\t- If you go over 21 you bust, and the dealer wins regardless of the dealer's hand");
        System.out.println("\t- If you are dealt 21 with your first 2 cards (Ace and 10), you win regardless of the dealer's hand");
        System.out.println("\t- Once you decide to 'Stand', the dealer will hit until their total becomes 17 or higher at which point they will 'Stand'");
        System.out.println("\t- If the dealer lands within that 17 to 21 zone, whoever has the higher total wins - and a draw if they are both equal");
        System.out.println("Press any key to continue.");
        scanner.nextLine();
    }

}
