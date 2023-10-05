import java.util.Objects;

class IntegerRing implements Ring<Integer>{
    public Integer zero(){
        /*
            Returns additive identity of Integer
         */
        return 0;
    }
    public Integer identity(){
        /*
            Returns multiplicative identity of integer
         */
        return 1;
    }
    public Integer sum(Integer x, Integer y){
        /*
            Returns sum of 2 Integer types

         */
        Objects.requireNonNull(x, "x is null");
        Objects.requireNonNull(y, "y is null");
        return x+y;
    }
    public Integer product(Integer x, Integer y){
        /*
            Returns product of 2 Integer types
         */
        Objects.requireNonNull(x, "x is null");
        Objects.requireNonNull(y, "y is null");
        return x*y;
    }
}