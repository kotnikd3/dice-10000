package si.kotnik;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player = new Player("player");

    @Test
    void setTurn() {
        player.setTurn(true);
        assertTrue(player.isTurn());
    }

    @Test
    void throwDices() {
        DicesSingleton dicesInstance = DicesSingleton.getInstance(6);

        List<Integer> dicesNumbers = dicesInstance.getNumbers();
        List<Integer> dicesNumbersCopy = new ArrayList<>(dicesNumbers);
        player.throwDices(dicesInstance, 6);

        assertNotEquals(dicesNumbers, dicesNumbersCopy);
    }

    @Test
    void pickChoicesScore() {
        List<Choice> choices = new ArrayList<Choice>() {{
            add(new Choice(0, 0, 0, Choice.Type.FINISH_ROUND)); // Index: 0
            add(new Choice(100, 1, 1, Choice.Type.ONE_TIME)); // Index: 1
            add(new Choice(300, 3, 3, Choice.Type.THREE_TIMES)); // Index: 2
            add(new Choice(100, 2, 5, Choice.Type.TWO_TIMES)); // Index: 3
        }};

        KeyboardInput input = new KeyboardInput();
        input.setInput("0 1 2");
        try {
            player.pickChoices(choices, input);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        player.evaluateRound(Game.MIN_ROUND_SCORE);

        assertAll(() -> assertEquals(0, player.getRoundScore()),
                () -> assertEquals(false, player.isTurn()),
                () -> assertEquals(400, player.getScore()));
    }

    @Test
    void pickChoicesNotValidInput() {
        List<Choice> choices = new ArrayList<Choice>() {{
            add(new Choice(0, 0, 0, Choice.Type.FINISH_ROUND)); // Index: 0
            add(new Choice(100, 1, 1, Choice.Type.ONE_TIME)); // Index: 1
            add(new Choice(300, 3, 3, Choice.Type.THREE_TIMES)); // Index: 2
            add(new Choice(100, 2, 5, Choice.Type.TWO_TIMES)); // Index: 3
        }};

        KeyboardInput input = new KeyboardInput();
        input.setInput("fekfppow");

        assertThrows(IOException.class, () -> {
            player.pickChoices(choices, input);
        });
    }

    @Test
    void pickChoicesExclusiveInput() {
        List<Choice> choices = new ArrayList<Choice>() {{
            add(new Choice(100, 1, 1, Choice.Type.ONE_TIME)); // Index: 0
            add(new Choice(100, 2, 1, Choice.Type.TWO_TIMES)); // Index: 1
        }};

        KeyboardInput input = new KeyboardInput();
        input.setInput("0 1");

        assertThrows(IOException.class, () -> {
            player.pickChoices(choices, input);
        });
    }

    @Test
    void setPickedChoices() {
        player.reset();
        List<Choice> choices = new ArrayList<Choice>() {{
            add(new Choice(100, 1, 1, Choice.Type.ONE_TIME));
            add(new Choice(200, 2, 1, Choice.Type.TWO_TIMES));
            add(new Choice(0, 0, 0, Choice.Type.FINISH_ROUND));
        }};
        player.setPickedChoices(choices);

        List<Choice> pickedChoices = player.getPickedChoices();
        assertEquals(choices, pickedChoices);
    }

    @Test
    void exitGameChoice() {
        player.reset();
        List<Choice> choices = new ArrayList<Choice>() {{
            add(new Choice(0,0,0, Choice.Type.EXIT_GAME));
        }};
        player.setPickedChoices(choices);
        assertTrue(player.choiceExitGame());
    }

    @Test
    void evaluateRound() {
        player.reset();
        List<Choice> choices = new ArrayList<Choice>() {{
            add(new Choice(100, 1, 1, Choice.Type.ONE_TIME));
            add(new Choice(200, 2, 1, Choice.Type.TWO_TIMES));
            add(new Choice(200, 2, 1, Choice.Type.TWO_TIMES));
            add(new Choice(0, 0, 0, Choice.Type.FINISH_ROUND));
        }};
        player.setPickedChoices(choices);
        player.evaluateRound(Game.MIN_ROUND_SCORE);

        Player player2 = new Player("player2");
        choices = new ArrayList<Choice>() {{
            add(new Choice(100, 1, 1, Choice.Type.ONE_TIME));
            add(new Choice(200, 2, 1, Choice.Type.TWO_TIMES));
        }};
        player2.setPickedChoices(choices);
        player2.evaluateRound(Game.MIN_ROUND_SCORE);

        assertAll(() -> assertEquals(0, player.getRoundScore()),
                () -> assertEquals(500, player.getScore()),
                () -> assertEquals(300, player2.getRoundScore()),
                () -> assertEquals(0, player2.getScore()));
    }

    @Test
    void reset() {
        player.reset();
        List<Choice> choices = new ArrayList<Choice>() {{
            add(new Choice(100, 1, 1, Choice.Type.ONE_TIME));
            add(new Choice(200, 2, 1, Choice.Type.TWO_TIMES));
        }};
        player.setPickedChoices(choices);
        player.evaluateRound(Game.MIN_ROUND_SCORE);
        player.reset();

        assertAll(() -> assertEquals(0, player.getRoundScore()),
                () -> assertEquals(0, player.getScore()));
    }
}