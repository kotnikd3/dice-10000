package game;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the heart of the program, business logic. A bit of an abstract term.
 */
class Game {
    private final int MAX_DICES = 6;
    private final int FINISHED_SCORE = 10000;
    private final int MIN_ROUND_SCORE = 350;
    private final DicesSingleton DICES = DicesSingleton.getInstance(MAX_DICES);

    private int numberOfDices = MAX_DICES;
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
        int usedDices = currentPlayer.getPickedChoices().stream().mapToInt(Choice::getNumberOfDices).sum();
        // If this is first round or player already used all of the dices, he can continue to play
        if (usedDices == 0 || usedDices == MAX_DICES)
            numberOfDices = MAX_DICES;
        else
            numberOfDices -= usedDices;
    }

    public void makeAvailableChoices() {
        availableChoices = ChoiceEvaluator.makeChoices(DICES);
    }

    public Player getNextPlayer() {
        int indexOfCurrentPlayer = players.indexOf(currentPlayer) + 1;
        if (indexOfCurrentPlayer >= players.size())
            indexOfCurrentPlayer = 0;

        currentPlayer = players.get(indexOfCurrentPlayer);
        currentPlayer.setTurnAndReset(true);

        return currentPlayer;
    }

    // TODO: functions should do one thing only?!
    /**
     * Add score from picked choices to player's round score.
     * Check if player choosed to finish the game or finish the round.
     */
    public void evaluateRound() {
        int diceScore = currentPlayer.getPickedChoices().stream().mapToInt(Choice::getScore).sum();
        currentPlayer.addToRoundScore(diceScore);

        if (currentPlayer.getPickedChoices().stream().anyMatch(c -> c.exitGame())) {
            exitGame();
        }

        if (currentPlayer.getPickedChoices().stream().anyMatch(c -> c.finishRound())) {
            finishRound();
        }
    }

    // TODO: use exception instead?
    private void exitGame() {
        System.out.println("\nFinishing game ...");
        System.exit(0);
    }

    /**
     * If player's round score is more than MIN_ROUND_SCORE, add it to his (general) score and set his turn to false.
     */
    private void finishRound() {
        int roundScore = currentPlayer.getRoundScore();
        if (roundScore >= MIN_ROUND_SCORE)
            currentPlayer.addRoundScoreToScore();
        currentPlayer.setTurnAndReset(false);
    }

    /**
     * @return scores for each player, formated like a nice score table
     */
    public String getInfo() {
        StringBuilder sb = new StringBuilder("-----------------------\n");
        String cursor = "";
        for(Player p : players) {
            if (p.isTurn())
                cursor = ">>";
            sb.append(cursor + p.getName() + "\t\tScore: " + p.getScore() + "\n");
            cursor = "";
        }
        sb.append("-----------------------");
        return sb.toString();
    }

    public int getNumberOfDices() {
        return numberOfDices;
    }

    public List<Choice> getAvailableChoices() { return availableChoices; }

    public DicesSingleton getDICES() { return DICES; }

    public boolean isFinished() {
        return players.stream().anyMatch(p -> p.getScore() >= FINISHED_SCORE);
    }
}