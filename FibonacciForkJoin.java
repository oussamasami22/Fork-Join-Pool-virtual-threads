import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class FibonacciForkJoin extends RecursiveTask<Integer> {
    private final int n;

    public FibonacciForkJoin(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        // If n is small enough, directly calculate the result
        if (n <= 1) {
            return n;
        }
        
        // Otherwise, split the task
        FibonacciForkJoin f1 = new FibonacciForkJoin(n - 1);
        f1.fork(); // Fork the first subtask to run asynchronously
        FibonacciForkJoin f2 = new FibonacciForkJoin(n - 2);
        
        // Compute the second subtask and join with the first
        return f2.compute() + f1.join();
    }

    public static void main(String[] args) {
        long depart = System.currentTimeMillis();
        
        ForkJoinPool pool = new ForkJoinPool();
        FibonacciForkJoin task = new FibonacciForkJoin(45);
        int resultatFinal = pool.invoke(task);
        
        System.out.println("Résultat final : " + resultatFinal);
        
        long fin = System.currentTimeMillis();
        System.out.println("Time : " + (fin - depart) + " ms");
    }
}
