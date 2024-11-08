import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class FibonacciForkJoin extends RecursiveTask<Integer> {
    private int n;
    private int resultat;
    public FibonacciForkJoin(int n) {
        this.n = n;
    }
    private int calculElementaire(int n){
        if (n <= 1){
            return n;
        }
        else{
            return calculElementaire(n-1) + calculElementaire(n - 2);
        }
    }

     @Override
    protected Integer compute() {
        if (n <= 20) {
            resultat = calculElementaire(n);
            return resultat;

        }
        FibonacciForkJoin f1 = new FibonacciForkJoin(n - 1);
        f1.fork();
        FibonacciForkJoin f2 = new FibonacciForkJoin(n - 2);
        f2.fork();
        return f2.join() + f1.join();
    } 

    public static void main(String[] args) {
        
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        System.out.println(Runtime.getRuntime().availableProcessors());
        long depart = System.currentTimeMillis();
        int resultatFinal = pool.invoke(new FibonacciForkJoin(45));
        System.out.println("Résultat final : " + resultatFinal);

        long fin = System.currentTimeMillis();
        System.out.println("Time : " + (fin - depart) + " ms");
    }
}
