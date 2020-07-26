public class Card {

    public static final int CLUBS = 0;
    public static final int DIAMONDS = 1;
    public static final int HEARTS = 2;
    public static final int SPADES = 3;

    private static final String[] SUIT = {"c", "d", "h", "s"};
    private static final String[] RANK = {"_", "_", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"}; //_ so that [4] = rank 4
    
    private byte cardSuit;
    private byte cardRank;

    /**
     * Constructor
     * 
     * @param suit is the suit of the card; club (0), diamond (1), heart (2) or spade (3)
     * @param rank is the rank of the card; Two (2) to Ace (1 or 14) as the index
     */
    public Card(int suit, int rank) {
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
        return RANK[cardRank] + SUIT[cardSuit];
    }

}
