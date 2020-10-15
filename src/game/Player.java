package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Player {
    private int score = 0;
    private int roundScore = 0;
    private boolean turn = false;
    private String name;
    private List<Choice> pickedChoices = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void addRoundScoreToScore() {
        this.score += roundScore;
    }

    public void addToRoundScore(int diceScore) { this.roundScore += diceScore; }

    public void setTurnAndReset(boolean turn) {
        this.turn = turn;
        reset();
    }

    private void reset() {
        resetRoundScore();
        clearPickedChoices();
    }

    private void resetRoundScore() { roundScore = 0; }

    private void clearPickedChoices() { pickedChoices.clear(); }

    public void throwDices(DicesSingleton dices, int n) {
        dices.shuffle(n);
    }

    // TODO: daj v drug razred?
    /**
     * Make a subset of given choices.
     * @param choices list of all given choices player can choose from
     */
    public void pickChoices(List<Choice> choices) {
        clearPickedChoices();

        int i = 0;
        for (Choice c : choices) {
            System.out.println("#" + i + ": " + c.toString());
            i++;
        }

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Pick #: ");
        String choices_str = keyboard.nextLine();

        int [] pickedIndexes = Stream.of(choices_str.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        IntStream.of(pickedIndexes).forEach(element -> {
            if (element < choices.size() && element >= 0) {
                pickedChoices.add(choices.get(element));
            }
        });

        if (ChoiceEvaluator.areChoicesExclusive(pickedChoices)) {
            System.out.println("YOU CAN'T CHOOSE EXCLUSIVE CHOICES! Repeat with choosing:");
            pickChoices(choices);
        }
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public int getRoundScore() { return roundScore; }

    public List<Choice> getPickedChoices() { return pickedChoices; }

    public boolean isTurn() {
        return turn;
    }
}