package ing.psychicpoker;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class PsychicTest {

    @Test
    public void analyze() {
        assertEquals("Hand: TH JH QC QD QS Deck: QH KH AH 2S 6S Best hand: straight-flush",
                Psychic.analyze("TH JH QC QD QS QH KH AH 2S 6S"));
        assertEquals("Hand: 2H 2S 3H 3S 3C Deck: 2D 3D 6C 9C TH Best hand: four-of-a-kind",
                Psychic.analyze("2H 2S 3H 3S 3C 2D 3D 6C 9C TH"));
        assertEquals("Hand: 2H 2S 3H 3S 3C Deck: 2D 9C 3D 6C TH Best hand: full-house",
                Psychic.analyze("2H 2S 3H 3S 3C 2D 9C 3D 6C TH"));
        assertEquals("Hand: 2H AD 5H AC 7H Deck: AH 6H 9H 4H 3C Best hand: flush",
                Psychic.analyze("2H AD 5H AC 7H AH 6H 9H 4H 3C"));
        assertEquals("Hand: AC 2D 9C 3S KD Deck: 5S 4D KS AS 4C Best hand: straight",
                Psychic.analyze("AC 2D 9C 3S KD 5S 4D KS AS 4C"));
        assertEquals("Hand: KS AH 2H 3C 4H Deck: KC 2C TC 2D AS Best hand: three-of-a-kind",
                Psychic.analyze("KS AH 2H 3C 4H KC 2C TC 2D AS"));
        assertEquals("Hand: AH 2C 9S AD 3C Deck: QH KS JS JD KD Best hand: two-pairs",
                Psychic.analyze("AH 2C 9S AD 3C QH KS JS JD KD"));
        assertEquals("Hand: 6C 9C 8C 2D 7C Deck: 2H TC 4C 9S AH Best hand: one-pair",
                Psychic.analyze("6C 9C 8C 2D 7C 2H TC 4C 9S AH"));
        assertEquals("Hand: 3D 5S 2H QD TD Deck: 6S KH 9H AD QH Best hand: highest-card",
                Psychic.analyze("3D 5S 2H QD TD 6S KH 9H AD QH"));
    }

    @Test
    public void getAllHands() {
        List<Hand> hands = Psychic.getAllHands("TH JH QC QD QS", "QH KH AH 2S 6S");
        assertEquals(1 << Hand.HAND_SIZE, hands.size());
        assertTrue(hands.contains(Hand.valueOf("TH JH QC QD QS")));
        assertTrue(hands.contains(Hand.valueOf("TH JH QC QD QS")));
        assertTrue(hands.contains(Hand.valueOf("QH KH AH 2S QS")));
        assertTrue(hands.contains(Hand.valueOf("QH KH AH 2S 6S")));
    }
}
