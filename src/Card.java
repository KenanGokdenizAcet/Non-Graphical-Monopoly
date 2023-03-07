import java.util.ArrayList;

public class Card {
    public static ArrayList<ChanceCard> chanceCardList = new ArrayList<>(); // list of chance cards
    public static ArrayList<CommunityChestCard> communityChestCardList = new ArrayList<>(); // list of community chest cards

    private final String directive;

    public Card(String directive) {
        this.directive = directive;
    }

    public String getDirective() {
        return directive;
    }

}

class ChanceCard extends Card {
    private static int orderOfCard = -1; // it is for the know the next card place that will be drawn

    public ChanceCard(String directive) {
        super(directive);
    }

    public static int getOrderOfCard() {
        return orderOfCard;
    }

    public static void setOrderOfCard(int orderOfCard) {
        ChanceCard.orderOfCard = orderOfCard;
    }

    @Override
    public String toString() {
        return this.getDirective();
    }

    public static String drawCard() {
        int order = getOrderOfCard();
        if (order == chanceCardList.size()) { // if all cards are drawn, next card will be first card in the deck
            setOrderOfCard(-1);
        }

        order ++;

        setOrderOfCard(order);
        return chanceCardList.get(order).toString();
    }

}

class CommunityChestCard extends Card {

    private static int orderOfCard = -1;

    public CommunityChestCard(String directive) {
        super(directive);
    }

    public static int getOrderOfCard() {
        return orderOfCard;
    }

    public static void setOrderOfCard(int orderOfCard) {
        CommunityChestCard.orderOfCard = orderOfCard;
    }

    @Override
    public String toString() {
        return this.getDirective();
    }

    public static String drawCard() { // if all cards are drawn, next card will be first card in the deck
        int order = getOrderOfCard();
        if (order == communityChestCardList.size()) {
            setOrderOfCard(-1);
        }

        order ++;

        setOrderOfCard(order);
        return communityChestCardList.get(order).toString();
    }

}
