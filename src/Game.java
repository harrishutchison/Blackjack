import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;

public class Game {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    public static void dealCards(final Deck deck, final StringBuilder playerCards, final StringBuilder dealerCards,
            final AtomicInteger playerCount, final AtomicInteger dealerCount, final Scanner scanner) {
        boolean isPlayerTurn = true;

        for (int i = 0; i < 3; i++) {
            final Card x = deck.deal();
            if (isPlayerTurn) {
                if (x.getRank() == -1) {
                    if (playerCount.get() != 11) {
                        playerCount.addAndGet(Output.ace(x, playerCount, scanner));
                    } else {
                        playerCount.addAndGet(1);
                    }
                } else {
                    playerCount.addAndGet(x.getRank());
                }
                playerCards.append(x.getCard());
            } else {
                if (x.getRank() == -1) {
                    dealerCount.addAndGet(11); // As it is dealers first card, no reason for it to be 1
                } else {
                    dealerCount.addAndGet(x.getRank());
                }
                dealerCards.append(x.getCard());
            }
            isPlayerTurn = !isPlayerTurn;
        }
    }

    public static void playerTurn(final Deck deck, final StringBuilder playerCards, final AtomicInteger playerCount,
            final Scanner scanner) throws IOException, InterruptedException {
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
                if (x.getRank() == -1 && playerCount.get() <= 10) {
                    playerCount.addAndGet(Output.ace(x, playerCount, scanner));
                } else if (x.getRank() == -1 && (playerCount.get() > 10)) {
                    playerCount.addAndGet(1);
                } else {
                    playerCount.addAndGet(x.getRank());
                }
                playerCards.append(x.getCard());

                Output.printCounts(playerCards, playerCount, true);

                if (Output.checkForGameOver(playerCards, playerCount, scanner)) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    Blackjack.main(null);
                }
            }

            System.out.println("Do you want to " + ANSI_BLUE + "H" + ANSI_RESET + "it or " + ANSI_BLUE + "S"
                    + ANSI_RESET + "tand?");
            if (scanner.hasNextLine()) {
                choice = scanner.nextLine();
            }
            System.out.println("\n");
        }
    }

    public static void dealerTurn(final Deck deck, final StringBuilder dealerCards, final AtomicInteger dealerCount,
            final Scanner scanner) throws IOException, InterruptedException {
        System.out.println("Dealer is hitting");

        TimeUnit.SECONDS.sleep(1);
        boolean dealerDone = false;

        while (!dealerDone) {
            final Card x = deck.deal();
            if (x.getRank() == -1) {
                if ((dealerCount.get() + 11) >= 17 && (dealerCount.get() + 11) <= 21) {
                    dealerCount.addAndGet(11);
                } else {
                    dealerCount.addAndGet(1); // If dealer already has an Ace, then taking 11 will mean
                                              // the dealer is bust
                }
            } else {
                dealerCount.addAndGet(x.getRank());
            }
            dealerCards.append(x.getCard());
            Output.printCounts(dealerCards, dealerCount, false);
            System.out.println("\n");

            if (dealerCount.get() >= 17) {
                dealerDone = true;
            } else {
                System.out.println("Dealer is hitting");
            }

            TimeUnit.SECONDS.sleep(1);
        }
    }

}