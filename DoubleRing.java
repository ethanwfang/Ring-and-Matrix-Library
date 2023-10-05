import java.util.Objects;

class DoubleRing implements Ring<Double>{
    public Double zero(){
        /*
            Returns additive identity of Double
         */
        return 0.0;
    }
    public Double identity(){
        /*
            Returns multiplicative identity of double
         */
        return 1.0;
    }
    public Double sum(Double x, Double y){
        /*
            Returns sum of 2 double variables.
         */
        Objects.requireNonNull(x, "x is null");
        Objects.requireNonNull(y, "y is null");
        return x+y;
    }


    public Double product(Double x, Double y){
        /*
            Returns product identity of 2 double variables.
         */
        Objects.requireNonNull(x, "x is null");
        Objects.requireNonNull(y, "y is null");
        return x*y;
    }
}