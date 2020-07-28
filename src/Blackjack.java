import java.util.Scanner;

public class Blackjack {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();

        opening(scanner);
        deck.shuffle();
        game(scanner, deck);

        scanner.close();
    }

    private static void opening(Scanner scanner) {
        System.out.println("Welcome to Blackjack. Do you want to read the rules?\n\t" + ANSI_BLUE + "1. " + ANSI_RESET + "Yes.\n\t" + ANSI_BLUE + "2. " + ANSI_RESET + "No, deal me in!");
        String line = scanner.nextLine();
        if (line.equals("1") || line.equals("1.")) {
            rules();
        }
    }

    private static void rules() {

    }

    private static void game(Scanner scanner, Deck deck) {
        int playerCount = 0, dealerCount = 0;
        String playerCards = "", dealerCards = "";
        boolean isPlayerTurn = true;

        for (int i = 0; i < 3; i++) {
            Card x = deck.deal();
            if (isPlayerTurn) {
                if (x.getRank() == -1) {
                    playerCount += ace(x, playerCount);
                } else {
                    playerCount += x.getRank();
                }
                playerCards += x.getCard();
            } else {

            }
            isPlayerTurn = !isPlayerTurn;
        }

        System.out.println("Player cards: " + playerCards + " (" + playerCount + ")");

    }

    private static int ace(Card x, int playerCount) {
        System.out.println("You have been dealt the " + x.getCard() + ", you currently have " + playerCount + ", do you want " + ANSI_BLUE + "10 " + ANSI_RESET + "or " + ANSI_BLUE + "1" + ANSI_RESET + "?");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int choice = Integer.parseInt(input);
        scanner.close();
        return choice;
    }

}
