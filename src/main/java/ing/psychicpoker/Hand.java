package ing.psychicpoker;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import com.google.common.primitives.Ints;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class Hand implements Comparable<Hand> {

    public static final int HAND_SIZE = 5;

    enum Category {

        HIGHEST_CARD,
        ONE_PAIR,
        TWO_PAIRS,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        STRAIGHT_FLUSH;

        public String toString() {
            return name().replace('_', '-').toLowerCase();
        }
    }

    private Card[] cards;
    private Category category;

    public static Card[] stringToCards(String str) {
        List<Card> cards = new ArrayList<Card>(HAND_SIZE);
        for (String s: Splitter.on(' ').split(str)){
            cards.add(Card.valueOf(s));
        }
        return cards.toArray(new Card[] {});
    }

    public static Hand valueOf(String str) {
        return new Hand(stringToCards(str));
    }

    public Hand(List<Card> cards) {
        this(cards.toArray(new Card[] {}));
    }

    public Hand(Card... cards) {
        checkArgument(cards.length == HAND_SIZE, "Invalid cards number");
        this.cards = Arrays.copyOf(cards, HAND_SIZE);
        Arrays.sort(this.cards);
    }

    public boolean isFlush() {
        Set<Card.Suit> suits = new HashSet<Card.Suit>();
        for (Card card: cards) {
            suits.add(card.getSuit());
        }
        return suits.size() == 1;
    }

    public boolean isStraight() {
        return isAceHighStraight() || isAceLowStraight();
    }

    private boolean isAceHighStraight() {
        for(int i=0; i<cards.length; i++) {
            if ((cards[i].getFaceValue().ordinal() - i) != cards[0].getFaceValue().ordinal())
                return false;
        }
        return true;
    }

    private boolean isAceLowStraight() {
        return cards[4].getFaceValue() == Card.FaceValue.ACE &&
                cards[0].getFaceValue() == Card.FaceValue.TWO &&
                cards[1].getFaceValue() == Card.FaceValue.THREE &&
                cards[2].getFaceValue() == Card.FaceValue.FOUR &&
                cards[3].getFaceValue() == Card.FaceValue.FIVE;

    }

    public int[] groupByKind() {
        Multiset<Card.FaceValue> faceValues = TreeMultiset.create();
        for (Card card: cards) {
            faceValues.add(card.getFaceValue());
        }
        List<Integer> result = Lists.newArrayList();
        for(Multiset.Entry<Card.FaceValue> e: faceValues.entrySet()) {
            result.add(e.getCount());
        }
        return Ints.toArray(Ordering.natural().reverse().sortedCopy(result));
    }

    public Category getCategory() {
        if(category == null)
            category = evaluateCategory();
        return category;
    }

    private Category evaluateCategory() {
        if(isStraight() && isFlush()) {
            return Category.STRAIGHT_FLUSH;
        } else {
            int[] kindGroups = groupByKind();
            checkState(kindGroups.length >= 2, "There must be least two groups");
            if (kindGroups[0] == 4) {
                return Category.FOUR_OF_A_KIND;
            } else if (kindGroups[0] == 3 && kindGroups[1] == 2) {
                return Category.FULL_HOUSE;
            } else if(isFlush()) {
                return Category.FLUSH;
            } else if(isStraight()) {
                return Category.STRAIGHT;
            } else if(kindGroups[0] == 3) {
                return Category.THREE_OF_A_KIND;
            } else if(kindGroups[0] == 2 && kindGroups[1] == 2) {
                return Category.TWO_PAIRS;
            } else if(kindGroups[0] == 2) {
                return Category.ONE_PAIR;
            } else {
                return Category.HIGHEST_CARD;
            }
        }
    }

    public Card[] getCards() {
        return Arrays.copyOf(cards, HAND_SIZE);
    }

    public boolean equals(Object o) {
        if(!(o instanceof Hand))
            return false;
        Hand hand = (Hand)o;
        return Arrays.equals(this.cards, hand.cards);
    }

    public int hashCode() {
        return Arrays.hashCode(this.cards);
    }

    public String toString() {
        return Joiner.on(' ').join(cards);
    }

    @Override
    public int compareTo(Hand hand) {
        return this.getCategory().compareTo(hand.getCategory());
    }
}
