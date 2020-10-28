package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Business rules. The hearth of the solution.
 */
class Game {
    protected final static int MIN_ROUND_SCORE = 350;

    private final int FINISHED_SCORE = 10000;
    private final int MAX_DICES = 6;
    private final IDices DICES = DicesSingleton.getInstance(MAX_DICES);

    private int numberOfSpareDices = MAX_DICES;
    private List<Choice> availableChoices = new ArrayList<>(MAX_DICES);

    private Input input = new KeyboardInput();
    private Output output = new Console();

    private List<IPlayer> players;
    private IPlayer currentPlayer;

    public Game(List<IPlayer> players) {
        this.players = players;
        currentPlayer = players.get(0);
    }

    /**
     * Main method to start the game with. Here is the main flow of the game dice 10000.
     */
    public void start() {
        output.println("Game has been started!");
        while (!isFinished()) {
            currentPlayer = getNextPlayer();
            output.println(getScoreBoard());

            while (currentPlayer.isTurn()) {
                nextRound();
                currentPlayer.throwDices(getDices(), getNumberOfSpareDices());
                output.println(getDicesAndRoundScoreBoard());

                makeAvailableChoices();
                output.println(getAvailableChoicesBoard());

                input.reset();
                while(!input.isValid()) {
                    output.print("Input #: ");
                    try {
                        input.get();
                        currentPlayer.pickChoices(getAvailableChoices(), input);
                    } catch (IOException e) {
                        output.println(e.getMessage());
                        input.reset();
                    }
                }
                currentPlayer.evaluateRound(MIN_ROUND_SCORE);

                if (currentPlayer.choiceExitGame()) {
                    output.println("\nFinishing game ...\nTHE FINAL SCORES:");
                    output.println(getScoreBoard());
                    return;
                }
            }
        }
    }

    public boolean isFinished() { return players.stream().anyMatch(p -> p.getScore() >= FINISHED_SCORE); }

    public IPlayer getNextPlayer() {
        int indexOfCurrentPlayer = players.indexOf(currentPlayer) + 1;
        if (indexOfCurrentPlayer >= players.size())
            indexOfCurrentPlayer = 0;

        currentPlayer = players.get(indexOfCurrentPlayer);
        currentPlayer.setTurn(true);

        return currentPlayer;
    }

    public void nextRound() {
        clearAvailableChoices();
        setNumberOfDices();
    }

    private void clearAvailableChoices() {
        availableChoices.clear();
    }

    /**
     * How much dices can player throw depends on how much dices player used in the previous round.
     */
    private void setNumberOfDices() {
        int numberOfUsedDices = currentPlayer.getNumberOfUsedDices();
        // If this is first round or player already used all of the dices, he can continue to play
        if (numberOfUsedDices == 0 || numberOfUsedDices == MAX_DICES)
            numberOfSpareDices = MAX_DICES;
        else
            numberOfSpareDices -= numberOfUsedDices;
    }

    public int getNumberOfSpareDices() { return numberOfSpareDices; }

    public void makeAvailableChoices() { availableChoices = ChoiceEvaluator.makeChoices(DICES); }

    public List<Choice> getAvailableChoices() { return availableChoices; }

    public IDices getDices() { return DICES; }

    /**
     * @return dices and round score for current player
     */
    public String getDicesAndRoundScoreBoard() {
        StringBuilder sb = new StringBuilder();
        sb.append(DICES.getNumbers().toString());
        sb.append("\t\t");
        sb.append(currentPlayer.getName() + "'s round score: ");
        sb.append(currentPlayer.getRoundScore());
        return sb.toString();
    }

    /**
     * @return all available choices formatted as each choice in a new line
     */
    public String getAvailableChoicesBoard() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Choice c : availableChoices) {
            sb.append("#" + i++ + ": " + c.toString() + "\n");
        }
        return sb.toString();
    }

    /**
     * @return scores for each player, formatted like a nice score table
     */
    public String getScoreBoard() {
        String line = "-----------------------";
        StringBuilder sb = new StringBuilder(line + "\n");
        for(IPlayer p : players) {
            sb.append((p.isTurn() ? ">>" : "") + p.getName() + "\t\tScore: " + p.getScore() + "\n");
        }
        sb.append(line);
        return sb.toString();
    }
}