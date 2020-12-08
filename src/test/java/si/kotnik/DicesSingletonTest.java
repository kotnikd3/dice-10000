package si.kotnik;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DicesSingletonTest {
    private IDices dicesInstance = DicesSingleton.getInstance(6);
    private List<Integer> dicesNumbers = dicesInstance.getNumbers();

    @Test
    void getInstance() {
        assertNotEquals(null, dicesInstance);
    }

    @Test
    void shuffle() {
        List<Integer> dicesNumbersCopy = new ArrayList<>(dicesNumbers);
        dicesInstance.shuffle(6);

        assertNotEquals(dicesNumbers, dicesNumbersCopy);
    }

    @Test
    @DisplayName("Make sure of right use of singleton pattern")
    void sameInstance() {
        IDices dicesInstance2 = DicesSingleton.getInstance(123);
        List<Integer> dicesNumbers2 = dicesInstance2.getNumbers();

        assertAll(() -> assertEquals(dicesInstance, dicesInstance2),
                  () -> assertEquals(dicesNumbers, dicesNumbers2));
    }

    @Test
    @DisplayName("Make sure that we can delete the list")
    void delete() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(1);
            add(1);
            add(1);
            add(2);
            add(2);
        }};
        IDices dices = new DicesSingleton(list);
        List<Integer> copyList = new ArrayList<>(dices.getNumbers());
        dices.getNumbers().removeAll(Arrays.asList(1));

        assertNotEquals(copyList, dices.getNumbers());
    }
}