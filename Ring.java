import java.math.BigInteger;
import java.util.List;
import java.util.function.BinaryOperator;
public interface Ring<T>{

    T zero();
    T identity();
    T sum(T x, T y);
    T product(T x, T y);
}




