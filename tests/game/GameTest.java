package game;

import com.sun.org.glassfish.gmbal.Description;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void nextRound() {
        Game game = new Game(Arrays.asList(
                new Player("Denis"),
                new Player("Matej"),
                new Player("Dejan")));

        game.makeAvailableChoices();
        List<Choice> availableChoices = new ArrayList<>(game.getAvailableChoices());
        game.nextRound();

        assertAll(() -> assertEquals(6, game.getNumberOfDices()),
                () -> assertTrue(game.getAvailableChoices().isEmpty()),
                () -> assertNotEquals(availableChoices, game.getAvailableChoices()));
    }

    @Test
    void makeAvailableChoices() {
        Game game = new Game(Arrays.asList(
                new Player("Denis"),
                new Player("Matej"),
                new Player("Dejan")));

        List<Choice> availableChoicesEmpty = new ArrayList<>(game.getAvailableChoices());
        game.makeAvailableChoices();

        assertAll(() -> assertTrue(availableChoicesEmpty.isEmpty()),
                () -> assertFalse(game.getAvailableChoices().isEmpty()));
    }

    @Test
    void getDICES() {
        Game game = new Game(Arrays.asList(
                new Player("Denis"),
                new Player("Matej"),
                new Player("Dejan")));

        DicesSingleton dices = game.getDICES();
        assertTrue(dices.getNumbers() instanceof List);
    }

    @Test
    @Description("Lets assume none of the players doesn't yet have 10000 points")
    void isFinished() {
        Game game = new Game(Arrays.asList(
                new Player("Denis"),
                new Player("Matej"),
                new Player("Dejan")));
        assertFalse(game.isFinished());
    }

    @Test
    void getNextPlayer() {
        Game game = new Game(Arrays.asList(
                new Player("Denis"),
                new Player("Matej"),
                new Player("Dejan")));
        Player player1 = game.getNextPlayer();
        Player player2 = game.getNextPlayer();

        assertNotEquals(player1, player2);
    }

    @Test
    void evaluatePickedChoices() {
        // TODO: napisi test
    }

    @Test
    void getInfo() {
        Game game = new Game(Arrays.asList(
                new Player("Denis"),
                new Player("Matej"),
                new Player("Dejan")));
        assertTrue(game.getInfo() instanceof String);
    }
}