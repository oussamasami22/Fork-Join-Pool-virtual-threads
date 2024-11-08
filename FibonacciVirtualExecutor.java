// Need JAVA version 20

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

public class FibonacciVirtualExecutor implements Callable<Integer> {
    private final int n;

    public FibonacciVirtualExecutor(int n) {
        this.n = n;
    }

    @Override
    public Integer call() {
        if (n <= 1) {
            return n;
        }
        try {
            var executor = Executors.newVirtualThreadPerTaskExecutor();
            Future<Integer> f1 = executor.submit(new FibonacciVirtualExecutor(n - 1));
            Future<Integer> f2 = executor.submit(new FibonacciVirtualExecutor(n - 2));
            return f1.get() + f2.get();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String[] args) {
        long depart = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<Integer> result = executor.submit(new FibonacciVirtualExecutor(45));
            System.out.println("Résultat final : " + result.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        long fin = System.currentTimeMillis();
        System.out.println("Time : " + (fin - depart) + " ms");
    }
}
