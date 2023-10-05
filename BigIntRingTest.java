import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class BigIntRingTest {
    @Test
    void sumBigInt(){
        BigIntRing r = new BigIntRing();
        assertEquals(new BigInteger("1234567890123456789012345678901234567891"), r.sum(new BigInteger("1234567890123456789012345678901234567890"), BigInteger.ONE));
    }

    @Test
    void sumnullBigInt(){
        BigIntRing r = new BigIntRing();
        assertThrows(NullPointerException.class, () -> r.sum(null, BigInteger.ONE));
    }

    @Test
    void productBigInt(){
        BigIntRing r = new BigIntRing();
        assertEquals(new BigInteger("1234567890123456789012345678901234567890"), r.product(new BigInteger("1234567890123456789012345678901234567890"), BigInteger.ONE));
    }

    @Test
    void productnullBigInt(){
        BigIntRing r = new BigIntRing();
        assertThrows(NullPointerException.class, () -> r.product(null, BigInteger.ONE));
    }

    @Test
    void identityBigInt(){
        BigIntRing r = new BigIntRing();
        assertEquals(BigInteger.ONE, r.identity());
    }

    @Test
    void zeroBigInt(){
        BigIntRing r = new BigIntRing();
        assertEquals(BigInteger.ZERO, r.zero());
    }

}