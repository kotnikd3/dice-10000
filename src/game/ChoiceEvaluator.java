package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChoiceEvaluator {

    /**
     * Make a list of all available choices from integers in the object Dices.
     * @param dices integers in the object Dices
     * @return List of all the available choices player can later choose from
     */
    public static List<Choice> makeChoices(DicesSingleton dices) {
        List<Choice> list = new ArrayList<>();

        // We always have an option to finish the game or to finish the round
        list.add(new Choice(0,0,0, Choice.Type.EXIT_GAME));
        list.add(new Choice(0,0,0, Choice.Type.FINISH_ROUND));

        // If player used all the dices, he can continue to play
        if (dices.getNumbers().isEmpty())
            list.add(new Choice(0,0,0, Choice.Type.NEXT_ROUND));

        // Check for the straight
        if (dices.getNumbers().containsAll(Arrays.asList(1,2,3,4,5,6))) {
            list.add(new Choice(2000, 6, 0, Choice.Type.STRAIGHT));
            return list;
        }
        else {
            // Check for 6x or 3x, 4x or 5x same number
            for (int number = 1; number < 7; number++) {
                if (Collections.frequency(dices.getNumbers(), number) == 6) {
                    list.add(new Choice((number == 1) ? 10000 : 1000 * number, 6, number, Choice.Type.SIX_TIMES));
                    return list;
                } else if (Collections.frequency(dices.getNumbers(), number) >= 3) {
                    list.add(new Choice((number == 1) ? 1000 : 100 * number, 3, number, Choice.Type.THREE_TIMES));
                    // Remove three times so we can evaluate the rest of the dices, for example 4x number 1 ... we can evaluate then 1 as well
                    dices.getNumbers().remove(new Integer(number));
                    dices.getNumbers().remove(new Integer(number));
                    dices.getNumbers().remove(new Integer(number));
                }
            }
        }

        // Check for 2x or 1x number 1
        if (Collections.frequency(dices.getNumbers(), 1) == 2) {
            list.add(new Choice(200, 2, 1,Choice.Type.TWO_TIMES));
            list.add(new Choice(100, 1, 1, Choice.Type.ONE_TIME));
        } else if (Collections.frequency(dices.getNumbers(), 1) == 1) {
            list.add(new Choice(100, 1, 1, Choice.Type.ONE_TIME));
        }
        // Check for 2x or 1x number 5
        if (Collections.frequency(dices.getNumbers(), 5) == 2) {
            list.add(new Choice(100, 2, 5, Choice.Type.TWO_TIMES));
            list.add(new Choice(50, 1, 5, Choice.Type.ONE_TIME));
        } else if (Collections.frequency(dices.getNumbers(), 5) == 1) {
            list.add(new Choice(50, 1, 5, Choice.Type.ONE_TIME));
        }

        return list;
    }

    /**
     * Check if given list of choices contains choices that are exclusive to be chosen by a player.
     */
    public static boolean areChoicesExclusive(List<Choice> pickedChoices) {
        boolean number_1_ONE_TIME = pickedChoices.stream().anyMatch(p -> p.diceNumber == 1 && p.type == Choice.Type.ONE_TIME);
        boolean number_1_TWO_TIMES = pickedChoices.stream().anyMatch(p -> p.diceNumber == 1 && p.type == Choice.Type.TWO_TIMES);
        boolean number_5_ONE_TIME = pickedChoices.stream().anyMatch(p -> p.diceNumber == 5 && p.type == Choice.Type.ONE_TIME);
        boolean number_5_TWO_TIMES = pickedChoices.stream().anyMatch(p -> p.diceNumber == 5 && p.type == Choice.Type.TWO_TIMES);

        return (number_1_ONE_TIME && number_1_TWO_TIMES) || (number_5_ONE_TIME && number_5_TWO_TIMES);
    }
}
