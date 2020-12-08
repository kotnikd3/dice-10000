package si.kotnik;

import java.util.List;

public interface IDices {
    void shuffle(int n);
    List<Integer> getNumbers();
}
