package si.kotnik;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeyboardInput implements Input {
    private final String REGEX = "(\\d{1}\\s?)+"; // Examples: {1}, {1 2}, ...
    private final Pattern pattern = Pattern.compile(REGEX);

    private Scanner keyboard = new Scanner(System.in);
    private List<Integer> indexes = new ArrayList<>();
    private String input = "";

    public KeyboardInput() { }

    @Override
    public void get() { input = keyboard.nextLine().trim(); }

    @Override
    public boolean isValid() {
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    @Override
    public void reset() { input = ""; }

    @Override
    public List<Integer> getIndexes() {
        parse();
        return indexes;
    }

    private void parse() {
        indexes = Stream.of(input.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    /**
     * For testing purposes only.
     */
    protected void setInput(String input) { this.input = input; }
    protected String getInput() { return input; }
}
