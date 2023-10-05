import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialRingTest {
    BigIntRing b = new BigIntRing();
    PolynomialRing<BigInteger> p = PolynomialRing.instance(b);
    Polynomial<BigInteger> polyEx1 = Polynomial.from(List.of(BigInteger.ONE, BigInteger.TWO, BigInteger.ZERO, BigInteger.ONE));
    Polynomial<BigInteger> polyEx2 = Polynomial.from(List.of(BigInteger.TEN, BigInteger.valueOf(5), BigInteger.ONE, BigInteger.ONE, BigInteger.ONE));

    @Test
    void identityPoly(){
        assertEquals(Polynomial.from(Collections.singletonList(b.identity())), p.identity());
    }

    @Test
    void sumPoly(){
        Polynomial<BigInteger> sumResult = p.sum(polyEx1, polyEx2);
        Polynomial<BigInteger> expectedSum = Polynomial.from(List.of(BigInteger.valueOf(11), BigInteger.valueOf(7), BigInteger.ONE, BigInteger.TWO, BigInteger.ONE));
        assertEquals(expectedSum, sumResult);
    }

    @Test
    void sumNull(){
        assertThrows(NullPointerException.class, () -> p.sum(null, polyEx1));
    }

    @Test
    void productPoly(){
        Polynomial<BigInteger> sumResult = p.product(polyEx1, polyEx2);
        Polynomial<BigInteger> expectedProduct = Polynomial.from(List.of(BigInteger.TEN, BigInteger.valueOf(25), BigInteger.valueOf(11), BigInteger.valueOf(2), BigInteger.ZERO));
        assertEquals(expectedProduct, sumResult);
    }

    @Test
    void productNull(){
        assertThrows(NullPointerException.class, () -> p.product(null, polyEx1));
    }

    @Test
    void testFrom() {
        List<Integer> coefficientsList = List.of(1, 2, 3);
        Polynomial<Integer> polynomial = Polynomial.from(coefficientsList);

        // Create an expected Polynomial based on the coefficientsList
        Polynomial<Integer> expectedPolynomial = Polynomial.from(coefficientsList);

        assertEquals(expectedPolynomial, polynomial);
    }

    @Test
    void testIterate() {
        List<Integer> coefficientsList = List.of(1, 2, 3);
        Polynomial<Integer> polynomial = Polynomial.from(coefficientsList);

        Iterator<Integer> iterator = polynomial.iterator();

        List<Integer> retrievedCoefficients = new ArrayList<>();
        while (iterator.hasNext()) {
            retrievedCoefficients.add(iterator.next());
        }

        assertEquals(coefficientsList, retrievedCoefficients);
    }

    @Test
    void testListIterator() {
        List<Integer> coefficientsList = List.of(1, 2, 3);
        Polynomial<Integer> polynomial = Polynomial.from(coefficientsList);

        ListIterator<Integer> listIterator = polynomial.listIterator(1); // Start from index 1

        List<Integer> retrievedCoefficients = new ArrayList<>();
        while (listIterator.hasNext()) {
            retrievedCoefficients.add(listIterator.next());
        }

        assertEquals(coefficientsList.subList(1, coefficientsList.size()), retrievedCoefficients);
    }

    @Test
    void testEqualsEqualPolynomials() {
        List<Integer> coefficientsList = List.of(1, 2, 3);
        Polynomial<Integer> polynomial1 = Polynomial.from(coefficientsList);
        Polynomial<Integer> polynomial2 = Polynomial.from(coefficientsList);

        assertEquals(polynomial1, polynomial2);
        assertEquals(polynomial2, polynomial1);
    }

    @Test
    void testEqualsNonEqualPolynomials() {
        Polynomial<Integer> polynomial1 = Polynomial.from(List.of(1, 2, 3));
        Polynomial<Integer> polynomial2 = Polynomial.from(List.of(1, 2, 4)); // Different coefficients

        assertNotEquals(polynomial1, polynomial2);
        assertNotEquals(polynomial2, polynomial1);
    }

    @Test
    void testEqualsWithDifferentTypes() {
        Polynomial<Integer> polynomial = Polynomial.from(List.of(1, 2, 3));
        Integer integer = 42; // Different object type

        assertNotEquals(polynomial, integer);
    }

    @Test
    void testEqualsSelfEquality() {
        Polynomial<Integer> polynomial = Polynomial.from(List.of(1, 2, 3));
        assertEquals(polynomial, polynomial);
    }

    // DOUBLE

    DoubleRing d = new DoubleRing();
    PolynomialRing<Double> polyDouble = PolynomialRing.instance(d);
    Polynomial<Double> d1 = Polynomial.from(List.of(1.0, 2.0, 3.0));
    Polynomial<Double> d2 = Polynomial.from(List.of(3.0, 4.0));


    @Test
    void identityDoublePoly(){
        assertEquals(Polynomial.from(Collections.singletonList(d.identity())), polyDouble.identity());
    }

    @Test
    void sumDoublePoly(){
        Polynomial<Double> sumResult = polyDouble.sum(d1, d2);
        Polynomial<Double> expectedSum = Polynomial.from(List.of(4.0, 6.0, 3.0));
        assertEquals(expectedSum, sumResult);
    }

    @Test
    void productDoublePoly(){
        Polynomial<Double> product = polyDouble.product(d1, d2);
        Polynomial<Double> expectedProduct = Polynomial.from(List.of(3.0,10.0, 17.0, 12.0));
        assertEquals(product, expectedProduct);
    }

    // Integer




}