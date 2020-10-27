package game;

import java.util.List;

public interface Input {
    void get();
    boolean isValid();
    void reset();
    List<Integer> getIndexes();
}
