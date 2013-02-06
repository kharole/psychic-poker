package ing.psychicpoker;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import org.junit.Test;

import static org.junit.Assert.*;

public class HandTest {

    @Test(expected = IllegalArgumentException.class)
    public void buildHandTooShort() {
        Hand.valueOf("2C 3D");
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildHandIncorrectCard() {
        Hand.valueOf("1C");
    }

    @Test
    public void isFlush() {
        assertTrue(Hand.valueOf("2C 3C 4C 5C 6C").isFlush());
        assertFalse(Hand.valueOf("2C 3D 4C 5C 6C").isFlush());
        assertFalse(Hand.valueOf("2C 3D 4C 5S 6C").isFlush());
    }

    @Test
    public void isStraight() {
        assertFalse(Hand.valueOf("2C 2D 4C 5C 6C").isStraight());
        assertFalse(Hand.valueOf("TC JD QC 2C AD").isStraight());

        assertTrue(Hand.valueOf("TC JD QC KC AD").isStraight());
        assertTrue(Hand.valueOf("2C 3C 4C 5C 6C").isStraight());

        assertTrue(Hand.valueOf("2C 3C 4C 5C AC").isStraight());
    }

    @Test
    public void handCategory() {
        assertEquals(Hand.Category.STRAIGHT_FLUSH, Hand.valueOf("TH JH QH KH AH").getCategory());
        assertEquals(Hand.Category.STRAIGHT_FLUSH, Hand.valueOf("6H 5H 4H 3H 2H").getCategory());
        assertEquals(Hand.Category.FOUR_OF_A_KIND, Hand.valueOf("3H 3S 3C 2D 3D").getCategory());
        assertEquals(Hand.Category.FULL_HOUSE, Hand.valueOf("2H 2S 3H 3S 3C").getCategory());
        assertEquals(Hand.Category.FLUSH, Hand.valueOf("AH 6H 9H 5H 7H").getCategory());
        assertEquals(Hand.Category.STRAIGHT, Hand.valueOf("AC 2D 3S 5S 4D").getCategory());
        assertEquals(Hand.Category.STRAIGHT, Hand.valueOf("TH JH QH KD AH").getCategory());
        assertEquals(Hand.Category.THREE_OF_A_KIND, Hand.valueOf("KC 2C TC 2D 2H").getCategory());
        assertEquals(Hand.Category.TWO_PAIRS, Hand.valueOf("QH KS JS JD KD").getCategory());
        assertEquals(Hand.Category.ONE_PAIR, Hand.valueOf("2H TC 4C 9S 9C").getCategory());
        assertEquals(Hand.Category.HIGHEST_CARD, Hand.valueOf("6S KH 9H AD QH").getCategory());
    }

    @Test
    public void groupByKind() {
        assertEquals(ImmutableList.of(3, 2), Ints.asList(Hand.valueOf("2C 5D 2S 5C 5H").groupByKind()));
        assertEquals(ImmutableList.of(3, 1, 1), Ints.asList(Hand.valueOf("2C KH 2D 2S AC").groupByKind()));
        assertEquals(ImmutableList.of(1, 1, 1, 1, 1), Ints.asList(Hand.valueOf("2C 3C 4C 5C 6C").groupByKind()));
    }
}
