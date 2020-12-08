package si.kotnik;

/**
 * Choice is like some sort of a rule or option, which player can choose to gain points.
 */

class Choice {
    public enum Type {
        EXIT_GAME, FINISH_ROUND, NEXT_ROUND, STRAIGHT, SIX_TIMES, THREE_TIMES, TWO_TIMES, ONE_TIME;
    }

    protected int score;
    protected int numberOfDices;
    protected int diceNumber;
    protected Type type;

    public Choice(int score, int numberOfDices, int diceNumber, Type type) {
        this.score = score;
        this.numberOfDices = numberOfDices;
        this.diceNumber = diceNumber;
        this.type = type;
    }

    public int getScore() {
        return score;
    }
    public int getNumberOfDices() { return numberOfDices; }
    public Type getType() {
        return type;
    }

    public boolean finishRound() { return type == Type.FINISH_ROUND; }
    public boolean exitGame() { return type == Type.EXIT_GAME; }

    @Override
    public String toString() {
        if (type == Type.FINISH_ROUND || type == Type.NEXT_ROUND || type == Type.EXIT_GAME)
                return type.toString();
        return type + " " + diceNumber + " (" + numberOfDices + " dices, " + score + " points)";
    }
}