import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public final class PolynomialRing<T> implements Ring<Polynomial<T>>{
    private final Ring<T> base;

    public static <T> PolynomialRing<T> instance(Ring<T> base){
        return new PolynomialRing<>(base);
    }

    private PolynomialRing(Ring<T> base){
        this.base = base;
    }

    public Polynomial<T> zero() {
        return Polynomial.from(new ArrayList<>());
    }

    public Polynomial<T> identity() {
        return Polynomial.from(Collections.singletonList(base.identity()));
    }

    public Polynomial<T> sum(Polynomial<T> x, Polynomial<T> y) {
        Objects.requireNonNull(x, "x is null");
        Objects.requireNonNull(y, "y is null");
        return x.plus(y, base);
    }

    public Polynomial<T> product(Polynomial<T> x, Polynomial<T> y) {
        Objects.requireNonNull(x, "x is null");
        Objects.requireNonNull(y, "y is null");
        return x.times(y, base);
    }
}