import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoubleRingTest {
    // DOUBLE TESTS
    @Test
    void twoPlusTwoShouldEqualFourDouble(){
        DoubleRing r = new DoubleRing();
        assertEquals(4.0, r.sum(2.0, 2.0));
    }
    //When testing null, we need to use assertThrows
    @Test
    void sumNullCaseDouble(){
        DoubleRing r = new DoubleRing();
        assertThrows(NullPointerException.class, () -> r.sum(null, 2.0));
    }

    @Test
    void twotimestwoisfourProductDouble(){
        DoubleRing r = new DoubleRing();
        assertEquals(4.0, r.product(2.0,2.0));
    }

    @Test
    void productNullCaseDouble(){
        DoubleRing r = new DoubleRing();
        assertThrows(NullPointerException.class, () -> r.product(null, 2.0));
    }

    @Test
    void identityDouble(){
        DoubleRing r = new DoubleRing();
        assertEquals(1.0, r.identity());
    }

    @Test
    void zeroDouble(){
        DoubleRing r = new DoubleRing();
        assertEquals(0.0, r.zero());
    }
}