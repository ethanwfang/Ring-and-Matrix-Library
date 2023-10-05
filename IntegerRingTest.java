import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerRingTest {
    @Test
    void twoPlusTwoShouldEqualFourInteger(){
        IntegerRing r = new IntegerRing();
        assertEquals(4, r.sum(2, 2));
    }
    //When testing null, we need to use assertThrows
    @Test
    void sumNullCaseInteger(){
        IntegerRing r = new IntegerRing();
        assertThrows(NullPointerException.class, () -> r.sum(null, 2));
    }

    @Test
    void twotimestwoisfourProductInteger(){
        IntegerRing r = new IntegerRing();
        assertEquals(4, r.product(2,2));
    }

    @Test
    void productNullCaseInteger(){
        IntegerRing r = new IntegerRing();
        assertThrows(NullPointerException.class, () -> r.product(null, 2));
    }

    @Test
    void identityInteger(){
        IntegerRing r = new IntegerRing();
        assertEquals(1, r.identity());
    }

    @Test
    void zeroInteger(){
        IntegerRing r = new IntegerRing();
        assertEquals(0, r.zero());
    }
}