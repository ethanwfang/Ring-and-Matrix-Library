import java.util.*;
import java.lang.*;

public final class Polynomial<T> implements Iterable<T>{
    private final List<T> coefficients;
    // Final classes prevent inheritance and make things immutable
    private Polynomial(List<T> coefficients){
        this.coefficients = coefficients;
    }

    public List<T> getCoefficients(){
        //First copy coefficients, and then make sure that it is mutable to return. use arraylist
        return coefficients;
    }

    public static <S> Polynomial<S> from(List<S> coefficients){
        Objects.requireNonNull(coefficients, "coefficients is null");
        return new Polynomial<>(List.copyOf(coefficients));
    }

    @Override
    public Iterator<T> iterator(){
        return coefficients.iterator();
    }

    public ListIterator<T> listIterator(int index){
        if(index < 0 || index >= coefficients.size()) throw new IndexOutOfBoundsException("index out of bounds");
        return coefficients.listIterator(index);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Polynomial<?> other = (Polynomial<?>) obj;
        return Objects.equals(coefficients, other.coefficients);
    }


    public Polynomial<T> plus(Polynomial<T> other, Ring<T> ring){
        Objects.requireNonNull(other, "other is null");
        Objects.requireNonNull(ring, "ring is null");

        int maxLength = Math.max(coefficients.size(), other.coefficients.size());
        List<T> resultCoefficients = new ArrayList<>(maxLength);
        for (int i = 0; i < coefficients.size(); i++) {
            T sum = ring.sum(coefficients.get(i), i < other.coefficients.size() ? other.coefficients.get(i) : ring.zero());
            //System.out.println("Iteration: " + i + " Coefficients: " + coefficients.get(i) + ", " + other.coefficients.get(i) + ". Sum: " + sum);
            resultCoefficients.add(i, sum);
        }
        if(coefficients.size() != maxLength){
            for(int j = coefficients.size(); j < other.coefficients.size(); j++){
                resultCoefficients.add(j, other.coefficients.get(j));
            }
        }
        return Polynomial.from(resultCoefficients);
    }

    public Polynomial<T> times(Polynomial<T> other, Ring<T> ring) { // Use listiterator
        Objects.requireNonNull(other, "other is null");
        Objects.requireNonNull(ring, "ring is null");
        int resultLength = coefficients.size() + other.coefficients.size() - 1;
        if (resultLength < 0){
            throw new IndexOutOfBoundsException("lists are empty");
        }
        List<T> resultCoefficients = new ArrayList<>(resultLength);

        for (int i = 0; i < resultLength; i++) {
            T product = ring.zero();
            for (int j = 0; j <= i; j++) {
                if (j < coefficients.size() && (i - j) < other.coefficients.size()) {
                    T coef1 = coefficients.get(j);
                    T coef2 = other.coefficients.get(i - j);
                    T termProduct = ring.product(coef1, coef2);
                    product = ring.sum(product, termProduct);
                }
            }
            resultCoefficients.add(product);
        }

        return Polynomial.from(resultCoefficients);
    }

    @Override
    public String toString() {
        return coefficients.toString();
    }
}
