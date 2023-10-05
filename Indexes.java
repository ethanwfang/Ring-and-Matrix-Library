import java.lang.Comparable;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Indexes(int row, int col) implements Comparable<Indexes>{

    public <S> S value(MatrixMap<S> matrix){
        Indexes i = matrix.size();
        return matrix.value(i);
    }

    public <S> S value(S[][] matrix) {
        if (row >= 0 && row < matrix.length && col >= 0 && col < matrix[0].length) {
            return matrix[row][col];
        } else {
            throw new IndexOutOfBoundsException("Indexes are out of bounds for the matrix.");
        }
    }

    public boolean areDiagonal(){
        return row == col;
    }

    // Generates a stream of indices from 'from' to 'to' inclusive.
    public static Stream<Indexes> stream(Indexes from, Indexes to) {
        return Stream.iterate(from,
                i -> i.row() < to.row() || (i.row() == to.row() && i.col() <= to.col()),
                i -> i.col() == to.col() ? new Indexes(i.row() + 1, from.col()) : new Indexes(i.row(), i.col() + 1));
    }



    // Generates a stream of indices from (0, 0) to 'size' inclusive.
    public static Stream<Indexes> stream(Indexes size) {
        return stream(new Indexes(0, 0), size);
    }

    // Generates a stream of indices from (0, 0) to (rows - 1, columns - 1) inclusive.
    public static Stream<Indexes> stream(int rows, int columns) {
        return stream(new Indexes(rows - 1, columns - 1));
    }

    @Override
    public int compareTo(Indexes compared) {
        // return 1 if "this" is greater than "compared"
        int rowComparison = Integer.compare(row, compared.row);
        return (rowComparison == 0) ? Integer.compare(col, compared.col): rowComparison;
    }

    @Override
    public String toString(){
        return "Indexes[row=" + row + ", col=" + col + "]";
    }


}


