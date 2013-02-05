package ing.psychicpoker;

import com.google.common.collect.*;

import java.io.IOException;
import java.util.*;

public class Psychic {

    public static String analyze(String str) {
        String handStr = str.substring(0, 14);
        String deckStr = str.substring(15, 29);

        Hand bestHand = Ordering.natural().max(getAllHands(handStr, deckStr));

        return String.format("Hand: %s Deck: %s Best hand: %s", handStr, deckStr, bestHand.getCategory());
    }

    public static List<Hand> getAllHands(String handStr, String deckStr) {
        Set<Card> handCards = ImmutableSet.copyOf(Hand.stringToCards(handStr));
        List<Card> deckCards = ImmutableList.copyOf(Hand.stringToCards(deckStr));

        List<Hand> hands = new ArrayList<Hand>();
        for (Set<Card> combination : Sets.powerSet(handCards)) {
            List<Card> cards = new ArrayList<Card>(Hand.HAND_SIZE);
            cards.addAll(combination);
            cards.addAll(deckCards.subList(0, Hand.HAND_SIZE - combination.size()));
            Hand hand = new Hand(cards);
            hands.add(hand);
        }

        return hands;
    }
}
