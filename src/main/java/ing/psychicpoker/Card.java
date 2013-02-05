package ing.psychicpoker;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import static com.google.common.base.Preconditions.checkArgument;

public class Card implements Comparable<Card> {

    enum FaceValue {

        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        NINE("9"),
        TEN("T"),
        JACK("J"),
        QUEEN("Q"),
        KING("K"),
        ACE("A");

        private final String label;

        FaceValue(String label) {
            this.label = label;
        }

        public static FaceValue valueOfLabel(String label) {
            for(FaceValue faceValue: values()) {
                if(faceValue.label.equals(label)) {
                    return faceValue;
                }
            }
            throw new IllegalArgumentException();
        }

        public String getLabel() {
            return label;
        }
    };

    enum Suit { D, C, H, S; }

    private static Card[][] instances = new Card[FaceValue.values().length][Suit.values().length];

    static {
        for(FaceValue faceValue: FaceValue.values()) {
            for(Suit suit: Suit.values()) {
                instances[faceValue.ordinal()][suit.ordinal()] = new Card(faceValue, suit);
            }
        }
    }

    public static Card valueOf(String str) {
        checkArgument(str.length() == 2, "Invalid str length");

        FaceValue faceValue = FaceValue.valueOfLabel(str.substring(0, 1));
        Suit suit = Suit.valueOf(str.substring(1, 2));

        return instances[faceValue.ordinal()][suit.ordinal()];
    }

    private FaceValue faceValue;
    private Suit suit;

    public Card(FaceValue faceValue, Suit suit) {
        this.faceValue = faceValue;
        this.suit = suit;
    }

    public FaceValue getFaceValue() {
        return faceValue;
    }

    public Suit getSuit() {
        return suit;
    }

    public int compareTo(Card card) {
        return ComparisonChain.start().
                compare(this.faceValue, card.faceValue).
                compare(this.suit, card.suit).
                result();
    }

    public String toString() {
        return faceValue.getLabel() + suit;
    }

}