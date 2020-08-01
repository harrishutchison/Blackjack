public class Deck {

    public static final int NUM_OF_CARDS = 52;

    private Card[] deckOfCards;
    private int currentCard;

    public Deck() {
        deckOfCards = new Card[NUM_OF_CARDS];

        int i = 0;
        for (int suit = Card.CLUBS; suit <= Card.SPADES; suit++) {
            for (int rank = 2; rank <= 14; rank++) {
                deckOfCards[i++] = new Card(suit, rank);
            }
        }
        currentCard = 0;
    }

    /**
     * Shuffling by finding two random cards in the deck and swapping them
     * 
     * @param n is the number of times this is repeated
     */
    public void shuffle() {
        int i, j;

        for (int k = 0; k < 100; k++) {
            i = (int) (NUM_OF_CARDS * Math.random());
            j = (int) (NUM_OF_CARDS * Math.random());

            Card temp = deckOfCards[i];
            deckOfCards[i] = deckOfCards[j];
            deckOfCards[j] = temp;
        }
        currentCard = 0;
    }

    public Card deal() {
        if (currentCard < NUM_OF_CARDS) {
            return deckOfCards[currentCard++];
        } else {
            System.out.println("Deck empty");
            return null;
        }
    }

    public String printDeck() {
        String s = "";
        int k = 0;

        for (int i = Card.CLUBS; i <= Card.SPADES; i++) {
            for (int j = 2; j <= 14; j++) {
                s += deckOfCards[k++].getCard() + " ";
            }
            s += "\n";
        }
        return s;
    }

}
