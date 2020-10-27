package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Player {
    private int score = 0;
    private int roundScore = 0;
    private boolean turn = false;
    private String name;
    private List<Choice> pickedChoices = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void throwDices(DicesSingleton dices, int n) {
        dices.shuffle(n);
    }

    /**
     * Add score from player's picked choices to player's round score.
     * Check about finishing the round.
     * Assume player is honest and fair (he can evaluate round even if other players go on a coffee break).
     */
    public void evaluateRound(int minScore) {
        int diceScore = pickedChoices.stream().mapToInt(Choice::getScore).sum();
        roundScore += diceScore;

        // If player choosed to finish or he didn't choose anything
        if (choiceFinishRound() || pickedChoices.isEmpty()) {
            if (roundScore >= minScore)
                score += roundScore;
            setTurn(false);
            reset();
        }
    }

    private boolean choiceFinishRound() { return pickedChoices.stream().anyMatch(c -> c.finishRound()); }

    public void setTurn(boolean turn) { this.turn = turn; }

    /**
     * For testing purposes as well.
     */
    protected void reset() {
        resetRoundScore();
        clearPickedChoices();
    }

    private void resetRoundScore() { roundScore = 0; }

    private void clearPickedChoices() { pickedChoices.clear(); }

    /**
     * Make a subset of given choices.
     * @param availableChoices list of all given choices player can choose from
     */
    public void pickChoices(List<Choice> availableChoices, Input input) throws IOException {
        clearPickedChoices();

        if (input.isValid()) {
            List<Integer> indexes = input.getIndexes();

            indexes.stream().forEach(index -> {
                if (index >= 0 && index < availableChoices.size())
                    pickedChoices.add(availableChoices.get(index));
            });

            if (ChoiceEvaluator.areChoicesExclusive(pickedChoices)) {
                throw new IOException("You can't choose exclusive choices!");
            }
        } else {
            throw new IOException("Invalid input!");
        }
    }

    public int getScore() { return score; }

    public String getName() { return name; }

    public int getRoundScore() { return roundScore; }

    public List<Choice> getPickedChoices() { return pickedChoices; }

    public int getNumberOfUsedDices() { return pickedChoices.stream().mapToInt(Choice::getNumberOfDices).sum(); }

    public boolean isTurn() { return turn; }

    public boolean choiceExitGame() { return pickedChoices.stream().anyMatch(c -> c.exitGame()); }

    /**
     * For testing purposes only.
     */
    protected void setPickedChoices(List<Choice> pickedChoices) { this.pickedChoices = pickedChoices; }
}