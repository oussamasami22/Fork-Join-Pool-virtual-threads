import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

public class FibonacciExecutorService implements Callable<Integer> {
    private final int n;

    public FibonacciExecutorService(int n) {
        this.n = n;
    }

    @Override
    public Integer call() {
        if (n <= 1) {
            return n;
        }
        try {
            ExecutorService executor = Executors.newFixedThreadPool(2);
            Future<Integer> f1 = executor.submit(new FibonacciExecutorService(n - 1));
            Future<Integer> f2 = executor.submit(new FibonacciExecutorService(n - 2));
            return f1.get() + f2.get();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String[] args) {
        long depart = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        try {
            Future<Integer> result = executor.submit(new FibonacciExecutorService(45));
            System.out.println("Résultat final : " + result.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        long fin = System.currentTimeMillis();
        System.out.println("Time : " + (fin - depart) + " ms");
    }
}
