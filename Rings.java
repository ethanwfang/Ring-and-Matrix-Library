import java.util.List;
import java.util.function.BinaryOperator;

public class Rings {
    public static <T> T reduce(List<T> args, T zero, BinaryOperator<T> accumulator){
        /*
            Utilizes stream.reduce method to apply "accumulator" to List args.
         */
        return args.stream().reduce(zero, accumulator);
    }

    public static <T> T sum(List<T> args, Ring<T> ring){
        /*
            Utilizes reduce method to sum all elements in args.
         */
        return reduce(args, ring.zero(), ring::sum);
    }

    public static <T> T product(List<T> args, Ring<T> ring){
        /*
            Utilizes reduce method to produce product of all elements in args.
         */
        return reduce(args, ring.identity(), ring::product);
    }
}