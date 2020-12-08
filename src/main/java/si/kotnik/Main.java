
/**
 * Copyleft 2020 by Denis Kotnik. Free to use.
 * Dice game for multiple players.
 * Created for the purpose of practicing writing 'clean code' and SOLID principles.
 */

package si.kotnik;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Game game = new Game(Arrays.asList(
                new Player("Denis"),
                new Player("Dejan"),
                new Player("Nastija"),
                new Player("Sandra")));

        game.start();
    }
}
