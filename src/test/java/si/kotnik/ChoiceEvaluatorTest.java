package si.kotnik;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChoiceEvaluatorTest {

    @Test
    void straight() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
            add(6);
        }};
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.STRAIGHT));
    }

    @Test
    void finish() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(9);
        }};
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.FINISH_ROUND));
    }

    @Test
    void oneTimeOne() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
        }};
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertAll(() -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.ONE_TIME)),
                () -> assertEquals(100, choices.stream().mapToInt(Choice::getScore).sum()));
    }

    @Test
    void twoTimesOne() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(1);
        }};
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertAll(() -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.ONE_TIME)),
                () -> assertEquals(4, choices.size()),
                () -> assertEquals(300, choices.stream().mapToInt(Choice::getScore).sum()));
    }
    @Test
    void oneTimeFive() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(5);
        }};
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertAll(() -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.ONE_TIME)),
                () -> assertEquals(50, choices.stream().mapToInt(Choice::getScore).sum()));
    }

    @Test
    void twoTimesFive() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(5);
            add(5);
        }};
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertAll(() -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.ONE_TIME)),
                () -> assertEquals(4, choices.size()),
                () -> assertEquals(150, choices.stream().mapToInt(Choice::getScore).sum()));
    }

    @Test
    void sixTimesOne() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(1);
            add(1);
            add(1);
            add(1);
            add(1);
        }};
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertAll(() -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.SIX_TIMES)),
                () -> assertEquals(3, choices.size()),
                () -> assertEquals(10000, choices.stream().mapToInt(Choice::getScore).sum()));
    }

    @Test
    void sixTimesFive() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(5);
            add(5);
            add(5);
            add(5);
            add(5);
            add(5);
        }};
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertAll(() -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.SIX_TIMES)),
                () -> assertEquals(3, choices.size()),
                () -> assertEquals(5000, choices.stream().mapToInt(Choice::getScore).sum()));
    }

    @Test
    void threeTimesOne() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(1);
            add(1);
        }};
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertAll(() -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.THREE_TIMES)),
                () -> assertEquals(3, choices.size()),
                () -> assertEquals(1000, choices.stream().mapToInt(Choice::getScore).sum()));
    }

    @Test
    void threeTimesFive() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(5);
            add(5);
            add(5);
        }};
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertAll(() -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.THREE_TIMES)),
                () -> assertEquals(3, choices.size()),
                () -> assertEquals(500, choices.stream().mapToInt(Choice::getScore).sum()));
    }

    @Test
    void threeTimesOnePlusThreeTimesFive() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(1);
            add(1);
            add(5);
            add(5);
            add(5);
        }};
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertAll(() -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.THREE_TIMES)),
                () -> assertEquals(4, choices.size()),
                () -> assertEquals(1500, choices.stream().mapToInt(Choice::getScore).sum()));
    }

    @Test
    void threeTimesOnePlusRandom() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(1);
            add(1);
            add(2);
            add(3);
            add(5);
        }};
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertAll(() -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.THREE_TIMES)),
                () -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.ONE_TIME)),
                () -> assertEquals(4, choices.size()),
                () -> assertEquals(1050, choices.stream().mapToInt(Choice::getScore).sum()));
    }

    @Test
    void threeTimesOnePlusTwoTimes() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(1);
            add(1);
            add(2);
            add(5);
            add(5);
        }};
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertAll(() -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.THREE_TIMES)),
                () -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.TWO_TIMES)),
                () -> assertEquals(5, choices.size()),
                () -> assertEquals(1150, choices.stream().mapToInt(Choice::getScore).sum()));
    }

    @Test
    void empty() {
        List<Integer> list = new ArrayList<Integer>();
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertAll(() -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.NEXT_ROUND)),
                () -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.FINISH_ROUND)),
                () -> assertEquals(3, choices.size()));
    }

    @Test
    void fourTimesOnePlusTwoTimes() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(1);
            add(1);
            add(1);
            add(5);
            add(5);
        }};
        DicesSingleton dices = new DicesSingleton(list);
        List<Choice> choices = ChoiceEvaluator.makeChoices(dices);

        assertAll(() -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.THREE_TIMES)),
                () -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.ONE_TIME)),
                () -> assertTrue(choices.stream().anyMatch(c -> c.getType() == Choice.Type.TWO_TIMES)),
                () -> assertEquals(6, choices.size()),
                () -> assertEquals(1250, choices.stream().mapToInt(Choice::getScore).sum()));
    }

    @Test
    void pickedExclusiveChoices() {
        List<Choice> exclusiveChoices = new ArrayList<Choice>() {{
            add(new Choice(100, 1, 1, Choice.Type.ONE_TIME));
            add(new Choice(200, 2, 1, Choice.Type.TWO_TIMES));
        }};
        List<Choice> inclusiveChoices = new ArrayList<Choice>() {{
            add(new Choice(100, 1, 1, Choice.Type.ONE_TIME));
            add(new Choice(100, 2, 5, Choice.Type.TWO_TIMES));
        }};
        assertAll(() -> assertTrue(ChoiceEvaluator.areChoicesExclusive(exclusiveChoices)),
                () -> assertFalse(ChoiceEvaluator.areChoicesExclusive(inclusiveChoices)));
    }
}