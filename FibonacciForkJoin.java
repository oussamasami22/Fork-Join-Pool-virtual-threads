import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class FibonacciForkJoin extends RecursiveTask<Integer> {
    private final int n;

    public FibonacciForkJoin(int n) {
        this.n = n;
    }
    private int calculElementaire(int n){
        if (n <= 1){
            return n;
        }
        else{
            return calculElementaire(n - 1) + calculElementaire(n - 2);
        }
    }

     /* @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }
        FibonacciForkJoin f1 = new FibonacciForkJoin(n - 1);
        f1.fork();
        FibonacciForkJoin f2 = new FibonacciForkJoin(n - 2);
        return f2.compute() + f1.join();
    } */

    public static void main(String[] args) {
        long depart = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        int resultatFinal = pool.invoke(new FibonacciForkJoin(45));
        System.out.println("Résultat final : " + resultatFinal);

        long fin = System.currentTimeMillis();
        System.out.println("Time : " + (fin - depart) + " ms");
    }
}
