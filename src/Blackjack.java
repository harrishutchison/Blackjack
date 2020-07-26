public class Blackjack {

    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println(deck.printDeck());

        deck.shuffle(100);
        System.out.println(deck.printDeck());

        for (int i = 0; i < 10; i++) {
            System.out.println(deck.deal().getCard());
        }
    }

}
