package game;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player = new Player("player");

    @Test
    void setTurn() {
        player.setTurnAndReset(true);
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
    @Disabled("Ignore for now")
    void makeDecision() {
        List<Choice> choices = new ArrayList<Choice>() {{
            add(new Choice(100, 1, 1, Choice.Type.ONE_TIME));
            add(new Choice(200, 2, 1, Choice.Type.TWO_TIMES));
            add(new Choice(0, 0, 0, Choice.Type.FINISH_ROUND));
        }};

        player.pickChoices(choices);
    }

    @Test
    void addRoundScoreToScore() {
        player.setTurnAndReset(true);
        player.addToRoundScore(100);
        player.addToRoundScore(100);
        player.addRoundScoreToScore();
        assertEquals(200, player.getScore());
    }

    @Test
    void reset() {
        Player player2 = new Player("player");
        player2.addToRoundScore(100);
        player2.setTurnAndReset(true);
        player2.addRoundScoreToScore();
        assertAll(() -> assertEquals(0, player2.getScore()),
                () -> assertEquals(0, player2.getRoundScore()));
    }
}