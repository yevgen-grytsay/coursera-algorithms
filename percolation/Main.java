/**
 * Created by yevgen on 04.02.2017.
 */
public class Main {
    public static void main(String[] args) {
        Percolation p = new Percolation(2);
        System.out.println(p.percolates());

        p.open(1, 1);
        System.out.println(p.percolates());

        p.open(2, 2);
        System.out.println(String.format("Percolates: %s, isFull(1, 1): %s", p.percolates(), p.isFull(2, 2)));

        p.open(1, 2);
        System.out.println(String.format("Percolates: %s, isFull(1, 1): %s", p.percolates(), p.isFull(2, 2)));
    }
}
