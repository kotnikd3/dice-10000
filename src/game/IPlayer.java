package game;

import java.io.IOException;
import java.util.List;

public interface IPlayer {

    void throwDices(IDices dices, int n);
    void evaluateRound(int minScore);
    void setTurn(boolean turn);
    void pickChoices(List<Choice> availableChoices, Input input) throws IOException;
    boolean choiceExitGame();

    boolean isTurn();
    int getNumberOfUsedDices();
    String getName();
    int getRoundScore();
    int getScore();
}
