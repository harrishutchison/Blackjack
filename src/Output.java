import java.util.Scanner;

public class Output {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private static final int BLACKJACK = 21;

    public static int ace(final Card x, final int playerCount, final Scanner scanner) {
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

    public static void printCounts(final String playerCards, final String dealerCards, final int playerCount,
            final int dealerCount) {
        System.out.println("Dealers cards: " + dealerCards + " (" + dealerCount + ") \n\n");
        System.out.println("Players cards: " + playerCards + " (" + playerCount + ")");
    }

    public static void printCounts(final String cards, final int count, boolean isPlayer) {
        if (isPlayer) {
            System.out.println("Players cards: " + cards + " (" + count + ")");
        } else {
            System.out.println("Dealers cards: " + cards + " (" + count + ")");
        }
    }

    public static boolean checkForBlackjack(final String playerCards, final int playerCount, final Scanner scanner) {
        if (playerCount == BLACKJACK) {
            System.out.println(ANSI_GREEN_BACKGROUND + "BLACKJACK!" + ANSI_RESET + " Your cards: " + playerCards
                    + ", do you want to play again?\n\t" + ANSI_BLUE + "1. " + ANSI_RESET + "Yes.\n\t" + ANSI_BLUE
                    + "2. " + ANSI_RESET + "No. [DEFAULT]");
            final String input = scanner.nextLine();
            if (input.equals("1") || input.equals("1.")) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkForGameOver(final String playerCards, final int playerCount, final Scanner scanner) {
        if (playerCount > BLACKJACK) {
            System.out.println("");
            System.out.println(ANSI_RED_BACKGROUND + "BUST!" + ANSI_RESET + " Your cards are " + playerCards + " ("
                    + playerCount + "), do you want to play again?\n\t" + ANSI_BLUE + "1. " + ANSI_RESET + "Yes.\n\t"
                    + ANSI_BLUE + "2. " + ANSI_RESET + "No. [DEFAULT]");
            final String input = scanner.nextLine();
            if (input.equals("1") || input.equals("1.")) {
                return true;
            }
        }
        return false;
    }

    public static boolean winner(final String playerCards, final String dealerCards, final int playerCount,
            final int dealerCount, final Scanner scanner) {
        if (dealerCount > 21) {
            System.out.println(ANSI_GREEN_BACKGROUND + "DEALER BUST!" + ANSI_RESET + " Dealer has " + dealerCards + " ("
                    + dealerCount + "), do you want to play again?\n\t" + ANSI_BLUE + "1. " + ANSI_RESET + "Yes.\n\t"
                    + ANSI_BLUE + "2. " + ANSI_RESET + "No. [DEFAULT]");
            return another(scanner);
        }

        if (dealerCount == playerCount) {
            System.out.println(ANSI_WHITE_BACKGROUND + ANSI_BLACK + "DRAW!" + ANSI_RESET
                    + " You and the dealer both have " + dealerCount + " with the hands " + playerCards + " and "
                    + dealerCards + ", do you want to play again?\n\t" + ANSI_BLUE + "1. " + ANSI_RESET + "Yes.\n\t"
                    + ANSI_BLUE + "2. " + ANSI_RESET + "No. [DEFAULT]");
            return another(scanner);
        }

        if (dealerCount > playerCount) {
            System.out.println(ANSI_RED_BACKGROUND + "DEALER WINS!" + ANSI_RESET + " Dealer has " + dealerCards + " ("
                    + dealerCount + ") and you have " + playerCards + " (" + playerCount
                    + "), do you want to play again?\n\t" + ANSI_BLUE + "1. " + ANSI_RESET + "Yes.\n\t" + ANSI_BLUE
                    + "2. " + ANSI_RESET + "No. [DEFAULT]");
            return another(scanner);
        } else {
            System.out.println(ANSI_GREEN_BACKGROUND + "YOU WIN!" + ANSI_RESET + " You have " + playerCards + " ("
                    + playerCount + ") and the dealer has " + dealerCards + " (" + dealerCount
                    + "), do you want to play again?\n\t" + ANSI_BLUE + "1. " + ANSI_RESET + "Yes.\n\t" + ANSI_BLUE
                    + "2. " + ANSI_RESET + "No. [DEFAULT]");
            return another(scanner);
        }
    }

    public static boolean another(Scanner scanner) {
        final String input = scanner.nextLine();
        if (input.equals("1") || input.equals("1.")) {
            return true;
        }
        return false;
    }

}