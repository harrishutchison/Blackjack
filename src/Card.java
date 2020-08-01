public class Card {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";

    public static final int CLUBS = 0;
    public static final int DIAMONDS = 1;
    public static final int HEARTS = 2;
    public static final int SPADES = 3;

    private static final String[] SUIT = { "c", "d", "h", "s" };
    private static final String[] RANK = { "_", "_", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A" }; //_ so that [4] = rank 4
    
    private final byte cardSuit;
    private final byte cardRank;

    /**
     * Constructor
     * 
     * @param suit is the suit of the card; club (0), diamond (1), heart (2) or
     *             spade (3)
     * @param rank is the rank of the card; Two (2) to Ace (1 or 14) as the index
     */
    public Card(final int suit, final int rank) {
        cardSuit = (byte) suit;
        cardRank = (byte) rank;
    }

    /**
     * Getters
     */
    public int getSuit() {
        return cardSuit;
    }

    public int getRank() {
        if (cardRank == 14) {
            return -1;
        } else if (cardRank > 10 && cardRank < 14) {
            return 10;
        }
        return cardRank;
    }

    public String getStringSuit() {
        return SUIT[cardSuit];
    }

    public String getStringRank() {
        return RANK[cardRank];
    }

    /**
     * @return the card as a string, eg. "Kh" = King of hearts
     */
    public String getCard() {
        String c = RANK[cardRank] + SUIT[cardSuit] + ANSI_RESET;

        switch(c.charAt(1)) {
            case 'c':
                c = ANSI_GREEN_BACKGROUND + c;
                break;
            case 'd':
                c = ANSI_BLUE_BACKGROUND + c;
                break;
            case 'h':
                c = ANSI_RED_BACKGROUND + c;
                break;
            case 's':
                c = ANSI_BLACK_BACKGROUND + c;
                break;
            default:

        }

        return c;
    }

}
