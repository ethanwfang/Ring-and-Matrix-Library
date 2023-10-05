import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public final class MatrixMap<T> {
    private final Map<Indexes, T> matrix;
    private final Indexes size;

    private MatrixMap(Map<Indexes, T> m, Indexes size){
        this.matrix = m;
        this.size = size;
    }


    public static <S> MatrixMap<S> identity(int size, S zero, S identity) {
        Objects.requireNonNull(zero);
        Objects.requireNonNull(identity);
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, size);
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, size);
        return instance(size, size, index -> (index.areDiagonal()) ? identity : zero);
    }

    public static <S> MatrixMap<S> from(S[][] matrixArray) {
        Objects.requireNonNull(matrixArray);
        int rows = matrixArray.length;
        int columns = (rows > 0) ? matrixArray[0].length : -1;
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, rows);
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, columns);
        return instance(rows, columns, (Indexes in) -> in.value(matrixArray));
    }

    public static <S> MatrixMap<S> instance(int rows, int columns, Function<Indexes, S> valueMapper) {
        Objects.requireNonNull(valueMapper);
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, rows);
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, columns);
        Map<Indexes, S> m = new HashMap<>();
        Indexes size = new Indexes(rows - 1, columns - 1);
        // Could've definitely used streams here
        for (int row = 0; row <= size.row(); row++) {
            for (int col = 0; col <= size.col(); col++) {
                Indexes index = new Indexes(row, col);
                m.put(index, valueMapper.apply(index));
            }
        }

        return new MatrixMap<>(Map.copyOf(m), size);
    }

    public static <S> MatrixMap<S> instance(Indexes size, Function<Indexes, S> valueMapper) {
        Objects.requireNonNull(size);
        Objects.requireNonNull(valueMapper);
        return instance(size.row() + 1, size.col() + 1, valueMapper);
    }

    public static <S> MatrixMap<S> constant(int size, S value) {
        Objects.requireNonNull(value);
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, size);
        return instance(size, size, index -> value);
    }

    public Indexes getSize(){
        return size;
    }

    public Indexes size(){
        // For methods that have to call size multiple times, this way is very time consuming.
        // I considered using a private variable to hold the size to fix this, possibly for future assignments.
        Indexes tracker = new Indexes(0,0);
        for (Map.Entry<Indexes, T> entry : matrix.entrySet()) {
            Indexes location = entry.getKey();
            if(location.compareTo(tracker) > 0) tracker = location;
        }
        return tracker;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MatrixMap [\n");

        for (Map.Entry<Indexes, T> entry : matrix.entrySet()) {
            Indexes indexes = entry.getKey();
            T value = entry.getValue();
            sb.append("  ").append(indexes).append(" -> ").append(value).append("\n");
        }

        sb.append("]");

        return sb.toString();
    }

    public T value(Indexes index){
        Objects.requireNonNull(index);
        return matrix.get(index);
    }

    public T value(int row, int col){
        return matrix.get(new Indexes(row, col));
    }

    public <S> S value(MatrixMap<S> matrixx, Indexes index){
        Objects.requireNonNull(matrixx);
        Objects.requireNonNull(index);
        return matrixx.value(index);
    }

    public MatrixMap<T> plus(MatrixMap<T> other, BinaryOperator<T> plus){
        Objects.requireNonNull(other);
        Objects.requireNonNull(plus);
        Indexes commonSize = InconsistentSizeException.requireMatchingSize(this, other);
        Map<Indexes, T> resultMatrix = new HashMap<>();

        Indexes from = new Indexes(0, 0);
        Indexes.stream(from, commonSize)
                .forEach(index -> {
                    T thisValue = value(index);
                    T otherValue = other.value(index);
                    T sum = plus.apply(thisValue, otherValue);
                    resultMatrix.put(index, sum);
                });

        return new MatrixMap<>(resultMatrix, commonSize);
    }

    public MatrixMap<T> times(MatrixMap<T> other, Ring<T> ring) {
        // Defined only for square matrices
        Objects.requireNonNull(other);
        Objects.requireNonNull(ring);
        NonSquareException.requireDiagonal(other.getSize());
        NonSquareException.requireDiagonal(this.getSize());
        InconsistentSizeException.requireMatchingSize(this, other);

        int rowsA = this.getSize().row() + 1;
        int colsA = this.getSize().col() + 1;
        int colsB = other.getSize().col() + 1;

        Map<Indexes, T> result = new HashMap<>();

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
                Indexes currentIndex = new Indexes(i, j);
                T sum = ring.zero();

                for (int k = 0; k < colsA; k++) {
                    Indexes rowIndex = new Indexes(i, k);
                    Indexes colIndex = new Indexes(k, j);

                    T product = ring.product(this.value(rowIndex), other.value(colIndex));
                    sum = ring.sum(sum, product);
                }

                result.put(currentIndex, sum);
            }
        }
        return new MatrixMap<>(result, new Indexes(rowsA - 1, colsB - 1));
    }

    /*
    public MatrixMap<T> times(MatrixMap<T> other, Ring<T> ring){
        // Defined only for square matrices
        Objects.requireNonNull(other);
        Objects.requireNonNull(ring);
        NonSquareException.requireDiagonal(other.getSize());
        NonSquareException.requireDiagonal(this.getSize());
        InconsistentSizeException.requireMatchingSize(this, other);
        Map<Indexes, T> result = new HashMap<>();

        for (int i = 0; i <= size.row()-1; i++) {
            for (int j = 0; j <= size.col(); j++) {
                Indexes currentIndex = new Indexes(i, j);
                T sum = ring.zero();

                for (int k = 0; k <= size.col()-1; k++) {
                    Indexes rowIndex = new Indexes(i, k);
                    Indexes colIndex = new Indexes(k, j);

                    T product = ring.product(this.value(rowIndex), other.value(colIndex));
                    sum = ring.sum(sum, product);
                }

                result.put(currentIndex, sum);
            }
        }
        return new MatrixMap<>(result, this.getSize());
    }

     */

    private static MatrixMap<Integer> createMatrix(int rows, int cols) {
        System.out.println("Enter the values for the matrix:");

        MatrixMap<Integer> matrix = MatrixMap.instance(rows, cols, index -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter value at row " + (index.row() + 1) + ", column " + (index.col() + 1) + ": ");
            return scanner.nextInt();
        });

        return matrix;
    }

    public static class NonSquareException extends IllegalArgumentException{
        private final Indexes indexes;

        public NonSquareException(Indexes setIndex){
            indexes = setIndex;
        }

        public Indexes getIndex(){
            return indexes;
        }

        public static Indexes requireDiagonal(Indexes index){
            Objects.requireNonNull(index);
            if(index.row() == index.col()) return index;
            throw new IllegalStateException("Indexes are not on the diagonal",
                    new NonSquareException(index));
        }
    }

    public static class InvalidLengthException extends IllegalArgumentException {
        @Serial
        private static final long serialVersionUID = 22;
        public enum Cause {
            ROW, COLUMN
        }

        private final Cause cause;
        private final int length;

        public InvalidLengthException(Cause cause, int length) {
            super();
            this.cause = cause;
            this.length = length;
        }

        public static int requireNonEmpty(Cause cause, int length){
            if(length > 0) return length;
            throw new IllegalArgumentException(new InvalidLengthException(cause, length));
        }

        public Cause getCauseType() {
            return cause;
        }

        public int getLength() {
            return length;
        }

        @Override
        public String getMessage() {
            return "Invalid length for " + cause.toString() + ": " + length;
        }
    }

    public static class InconsistentSizeException extends IllegalArgumentException{
        private final Indexes thisIndexes;
        private final Indexes otherIndexes;

        public InconsistentSizeException(Indexes thisI, Indexes otherI){
            thisIndexes = thisI;
            otherIndexes = otherI;
        }

        public Indexes getThisIndexes(){
            return thisIndexes;
        }

        public Indexes getOtherIndexes(){
            return otherIndexes;
        }

        public static <T> Indexes requireMatchingSize(MatrixMap<T> thisMatrix, MatrixMap<T> otherMatrix){
            Objects.requireNonNull(thisMatrix);
            Objects.requireNonNull(otherMatrix);
            if(thisMatrix.getSize().equals(otherMatrix.size())) return thisMatrix.getSize();
            throw new IllegalArgumentException("Sizes do not match",
                    new InconsistentSizeException(thisMatrix.getSize(), otherMatrix.getSize()));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Matrix Addition and Multiplication Program");
        System.out.println("----------------------------------------");

        System.out.print("Enter the number of rows for Matrix A: ");
        int rowsA = scanner.nextInt();
        System.out.print("Enter the number of columns for Matrix A: ");
        int colsA = scanner.nextInt();

        System.out.print("Enter the number of rows for Matrix B: ");
        int rowsB = scanner.nextInt();
        System.out.print("Enter the number of columns for Matrix B: ");
        int colsB = scanner.nextInt();

        MatrixMap<Integer> matrixA = createMatrix(rowsA, colsA);

        MatrixMap<Integer> matrixB = createMatrix(rowsB, colsB);

        while (true) {
            System.out.println("\nChoose operation:");
            System.out.println("1. Matrix Addition");
            System.out.println("2. Matrix Multiplication");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    try {
                        MatrixMap<Integer> sumMatrix = matrixA.plus(matrixB, (a, b) -> a + b);
                        System.out.println("Result of Matrix Addition:");
                        System.out.println(sumMatrix);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Matrix Addition Error: Sizes do not match - " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        MatrixMap<Integer> productMatrix = matrixA.times(matrixB, new IntegerRing());
                        System.out.println("Result of Matrix Multiplication:");
                    } catch (IllegalArgumentException e) {
                        System.err.println("Matrix Multiplication Error: Sizes do not match - " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }
}
