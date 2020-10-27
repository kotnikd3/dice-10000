package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Game as an abstract term.
 */
class Game {
    protected final static int MIN_ROUND_SCORE = 350;

    private final int MAX_DICES = 6;
    private final int FINISHED_SCORE = 10000;
    private final DicesSingleton DICES = DicesSingleton.getInstance(MAX_DICES);

    private int numberOfSpareDices = MAX_DICES;
    private List<Choice> availableChoices = new ArrayList<>(MAX_DICES);

    private List<Player> players;
    private Player currentPlayer;

    public Game(List<Player> players) {
        this.players = players;
        currentPlayer = players.get(0);
    }

    public void nextRound() {
        clearAvailableChoices();
        setNumberOfDices();
    }

    private void clearAvailableChoices() {
        availableChoices.clear();
    }

    private void setNumberOfDices() {
        int numberOfUsedDices = currentPlayer.getNumberOfUsedDices();
        // If this is first round or player already used all of the dices, he can continue to play
        if (numberOfUsedDices == 0 || numberOfUsedDices == MAX_DICES)
            numberOfSpareDices = MAX_DICES;
        else
            numberOfSpareDices -= numberOfUsedDices;
    }

    public void makeAvailableChoices() {
        availableChoices = ChoiceEvaluator.makeChoices(DICES);
    }

    public Player getNextPlayer() {
        int indexOfCurrentPlayer = players.indexOf(currentPlayer) + 1;
        if (indexOfCurrentPlayer >= players.size())
            indexOfCurrentPlayer = 0;

        currentPlayer = players.get(indexOfCurrentPlayer);
        currentPlayer.setTurn(true);

        return currentPlayer;
    }

    public int getNumberOfSpareDices() {
        return numberOfSpareDices;
    }

    public List<Choice> getAvailableChoices() { return availableChoices; }

    public DicesSingleton getDICES() { return DICES; }

    public boolean isFinished() { return players.stream().anyMatch(p -> p.getScore() >= FINISHED_SCORE); }

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
        for(Player p : players) {
            sb.append((p.isTurn() ? ">>" : "") + p.getName() + "\t\tScore: " + p.getScore() + "\n");
        }
        sb.append(line);
        return sb.toString();
    }
}