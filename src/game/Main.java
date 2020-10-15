
/**
 * Copyleft 2020 by Denis Kotnik. Free to use.
 * Dice game for multiple players.
 * Created for the purpose of practicing writing 'clean code' and SOLID principles.
 */

package game;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(Arrays.asList(
                new Player("Denis"),
                new Player("Matej"),
                new Player("Dejan")));

        System.out.println("Game has been started!");

        while (!game.isFinished()) {
            Player currentPlayer = game.getNextPlayer();
            System.out.println(game.getInfo());

            while (currentPlayer.isTurn()) {
                game.nextRound();
                currentPlayer.throwDices(game.getDICES(), game.getNumberOfDices());

                System.out.println("Dices: " + game.getDICES().getNumbers().toString() + "\t\t" + currentPlayer.getName() + "'s round score: " + currentPlayer.getRoundScore());

                game.makeAvailableChoices();
                currentPlayer.pickChoices(game.getAvailableChoices());
                game.evaluateRound();
            }
        }
    }
}
