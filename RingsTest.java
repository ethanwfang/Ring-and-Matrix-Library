import org.junit.jupiter.api.Test;
import java.util.List;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RingsTest {
    @Test
    void sumTest(){
        IntegerRing r = new IntegerRing();
        List<Integer> argsTest = Arrays.asList(3, 6, -2, 8);
        assertEquals(15, Rings.sum(argsTest, r));
    }

    @Test
    void productTest(){
        IntegerRing r = new IntegerRing();
        List<Integer> argsTest = Arrays.asList(3, 6, 1, 8);
        assertEquals(144, Rings.product(argsTest, r));
    }
    //test empty args and null parameters

    @Test
    void productTestEmpty(){
        IntegerRing r = new IntegerRing();
        List<Integer> argsTest = List.of();
        assertEquals(1, Rings.product(argsTest, r));
    }

    @Test
    void sumTestEmpty(){
        IntegerRing r = new IntegerRing();
        List<Integer> argsTest = List.of();
        assertEquals(0, Rings.sum(argsTest, r));
    }

    @Test
    void nullArgsTestSum(){
        IntegerRing r = new IntegerRing();
        assertThrows(NullPointerException.class, () -> Rings.sum(null, r));
    }

    @Test
    void nullArgsTestProduct(){
        IntegerRing r = new IntegerRing();
        assertThrows(NullPointerException.class, () -> Rings.product(null, r));
    }
}