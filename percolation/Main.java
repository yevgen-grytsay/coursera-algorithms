/**
 * Created by yevgen on 04.02.2017.
 */
public class Main {
    public static void main(String[] args) {
        Percolation p = new Percolation(2);
        System.out.println(p.percolates());

        p.open(0, 0);
        System.out.println(p.percolates());

        p.open(1, 1);
        System.out.println(p.percolates());

        p.open(0, 1);
        System.out.println(p.percolates());
    }
}
