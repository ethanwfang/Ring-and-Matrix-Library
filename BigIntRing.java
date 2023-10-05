import java.math.BigInteger;
import java.util.Objects;

class BigIntRing implements Ring<BigInteger>{
    public BigInteger zero(){
        /*
            Returns additive identity of BigInteger
         */
        return BigInteger.ZERO;
    }
    public BigInteger identity(){
        /*
            Returns multiplicative identity of BigInteger
         */
        return BigInteger.ONE;
    }
    public BigInteger sum(BigInteger x, BigInteger y){
        /*
            Returns sum of 2 BigInteger variables
         */
        Objects.requireNonNull(x, "x is null");
        Objects.requireNonNull(y, "y is null");
        return x.add(y);
    }
    public BigInteger product(BigInteger x, BigInteger y){
        /*
            Returns product of 2 BigInteger variables.
         */
        Objects.requireNonNull(x, "x is null");
        Objects.requireNonNull(y, "y is null");
        return x.multiply(y);
    }
}
