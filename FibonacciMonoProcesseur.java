public class FibonacciMonoProcesseur {
    private int number;
    private int resultat;
    public FibonacciMonoProcesseur(int n){
        number = n;
    }

    private int calculElementaire(int n){
        if(n <= 1){
            return n;
        }
        else{
            return calculElementaire(n - 1) + calculElementaire(n - 2);
        }
    }
    public void compute(){
        resultat = calculElementaire(number);
    }
    public int resultat(){
        return resultat;
    }
    public static void main(String[] args) {
        long depart = System.currentTimeMillis();
        FibonacciMonoProcesseur f = new FibonacciMonoProcesseur(45);
        f.compute();
        int resultatFinal = f.resultat();
        System.out.println("Résultat final : " + resultatFinal);

        long fin = System.currentTimeMillis();
        System.out.println("Time : " + (fin - depart) + " ms");
    }

}