package si.kotnik;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KeyboardInputTest {
    private KeyboardInput input = new KeyboardInput();

    @Test
    void get() {
        input.setInput("test");
        assertEquals("test", input.getInput());
    }

    @Test
    void isNotValid() {
        input.setInput("0 1 2 x 4");
        assertFalse(input.isValid());
    }

    @Test
    void isValid() {
        input.setInput("0 1 2 3 4");
        assertTrue(input.isValid());
    }

    @Test
    void reset() {
        input.setInput("0 1 2");
        input.reset();
        assertAll(() -> assertFalse(input.isValid()),
                () -> assertEquals("", input.getInput()));
    }

    @Test
    void getIndexes() {
        input.setInput("0 1 2");
        List<Integer> indexes = input.getIndexes();
        List<Integer> indexesStatic = Arrays.asList(0, 1, 2);
        List<Integer> indexesStatic2 = Arrays.asList(3, 4, 5);

        assertAll(() ->assertEquals(indexes, indexesStatic),
                () -> assertNotEquals(indexes, indexesStatic2));
    }
}