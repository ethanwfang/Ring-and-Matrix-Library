import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IndexesTest {

    @Test
    public void testStream() {
        Indexes from = new Indexes(1, 1);
        Indexes to = new Indexes(3, 3);

        List<Indexes> result = Indexes.stream(from, to)
                .collect(Collectors.toList());

        List<Indexes> expected = List.of(
                new Indexes(1, 1), new Indexes(1, 2), new Indexes(1, 3),
                new Indexes(2, 1), new Indexes(2, 2), new Indexes(2, 3),
                new Indexes(3, 1), new Indexes(3, 2), new Indexes(3, 3)
        );

        assertEquals(expected, result);
    }

}