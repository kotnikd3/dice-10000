package game;

public class Console implements Output {

    @Override
    public void println(String msg) {
        System.out.println(msg);
    }
    public void print(String msg) { System.out.print(msg); }
}
