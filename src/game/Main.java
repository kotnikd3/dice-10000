
/**
 * Copyleft 2020 by Denis Kotnik. Free to use.
 * Dice game for multiple players.
 * Created for the purpose of practicing writing 'clean code' and SOLID principles.
 */

package game;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Input input = new KeyboardInput();
        Game game = new Game(Arrays.asList(
                new Player("Denis"),
                new Player("Dejan"),
                new Player("Nastija"),
                new Player("Sandra")));

        System.out.println("Game has been started!");

        while (!game.isFinished()) {
            Player currentPlayer = game.getNextPlayer();
            System.out.println(game.getScoreBoard());

            while (currentPlayer.isTurn()) {
                game.nextRound();
                currentPlayer.throwDices(game.getDICES(), game.getNumberOfSpareDices());
                System.out.println(game.getDicesAndRoundScoreBoard());

                game.makeAvailableChoices();
                System.out.println(game.getAvailableChoicesBoard());

                input.reset();
                while(!input.isValid()) {
                    System.out.print("Input #: ");
                    try {
                        input.get();
                        currentPlayer.pickChoices(game.getAvailableChoices(), input);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        input.reset();
                    }
                }
                currentPlayer.evaluateRound(Game.MIN_ROUND_SCORE);

                if (currentPlayer.choiceExitGame()) {
                    System.out.println("\nFinishing game ...");
                    System.exit(0);
                }
            }
        }
    }
}
