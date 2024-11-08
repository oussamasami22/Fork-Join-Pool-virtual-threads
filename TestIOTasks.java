import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestIOTasks {
    private static final Random RANDOM = new Random();

    public static Integer random() {
        return RANDOM.nextInt(10);
    }

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        long sum = 0;

        // Create a fixed thread pool, size adjusted based on available processors
        int poolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        // Array of CompletableFutures for each task
        CompletableFuture<Long>[] futures = new CompletableFuture[1000];

        for (int j = 0; j < 1000; j++) {
            // Asynchronous task with a delay of 5ms between computations
            futures[j] = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(5); // Delay each task independently
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return (long) random();
            }, executor);
        }

        // Calculate the total sum of all results
        sum = CompletableFuture.allOf(futures)
                .thenApply(v -> {
                    long total = 0;
                    for (CompletableFuture<Long> future : futures) {
                        total += future.join();
                    }
                    return total;
                }).join();

        executor.shutdown(); // Clean up the executor service

        long end = System.currentTimeMillis();
        System.out.println("Résultat final : " + sum);
        System.out.println("Time : " + (end - begin) + " ms");
    }
}
