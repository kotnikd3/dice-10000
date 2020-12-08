package si.kotnik;

public class Console implements Output {

    @Override
    public void println(String msg) { System.out.println(msg); }
    @Override
    public void print(String msg) { System.out.print(msg); }
}
