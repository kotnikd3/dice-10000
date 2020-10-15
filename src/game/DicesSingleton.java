package game;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Dices are represented as a list of integers.
 * Here we are using singleton pattern.
 */

class DicesSingleton {
    private static DicesSingleton dices = null;
    private static List<Integer> numbers;
    private static Random rd = new Random();

    private DicesSingleton(int initialCapacity) {
        numbers = new ArrayList<Integer>(initialCapacity);
    }

    /**
     * Constructor for testing purposes
     */
    protected DicesSingleton(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public static synchronized DicesSingleton getInstance(int initialCapacity) {
        if (dices == null) {
            dices = new DicesSingleton(initialCapacity);
            makeRandomNumbers(initialCapacity);
        }
        return dices;
    }

    /**
     * Imagine as throwing n dices.
     * @param n number of dices to be 'thrown'
     */
    public void shuffle(int n) {
        numbers.clear();
        makeRandomNumbers(n);
    }

    /**
     * Fill list with 'n' random integers on interval [1, 6].
     * @param n number of integers to be inserted into list
     */
    private static void makeRandomNumbers(int n) {
        IntStream.range(0, n).forEach(i -> numbers.add(rd.nextInt(6) + 1));
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}