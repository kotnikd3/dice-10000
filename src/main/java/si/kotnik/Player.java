package si.kotnik;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Player implements IPlayer {
    private int score = 0;
    private int roundScore = 0;
    private boolean turn = false;
    private List<Choice> pickedChoices = new ArrayList<>();

    private String name;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public void throwDices(IDices dices, int n) { dices.shuffle(n); }

    /**
     * Add score from player's picked choices to player's round score.
     * Check about finishing the round or if he did not choose anything.
     * Assume player is honest and fair (he can evaluate round even if other players go on a coffee break).
     */
    @Override
    public void evaluateRound(int minScore) {
        int diceScore = pickedChoices.stream().mapToInt(Choice::getScore).sum();
        roundScore += diceScore;

        // If player choose to finish or exit the game or he didn't choose anything
        if (choiceFinishRound() || choiceExitGame() || pickedChoices.isEmpty()) {
            if (roundScore >= minScore)
                score += roundScore;
            setTurn(false);
            // We don't want to clear picked choices if he choose to exit the game
            if (!choiceExitGame())
                reset();
        }
    }

    private boolean choiceFinishRound() { return pickedChoices.stream().anyMatch(c -> c.finishRound()); }

    @Override
    public void setTurn(boolean turn) { this.turn = turn; }

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
    @Override
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

    @Override
    public int getScore() { return score; }

    @Override
    public String getName() { return name; }

    @Override
    public int getRoundScore() { return roundScore; }

    protected List<Choice> getPickedChoices() { return pickedChoices; }

    @Override
    public int getNumberOfUsedDices() { return pickedChoices.stream().mapToInt(Choice::getNumberOfDices).sum(); }

    @Override
    public boolean isTurn() { return turn; }

    @Override
    public boolean choiceExitGame() { return pickedChoices.stream().anyMatch(c -> c.exitGame()); }

    /**
     * For testing purposes
     */
    protected void setPickedChoices(List<Choice> pickedChoices) { this.pickedChoices = pickedChoices; }
}