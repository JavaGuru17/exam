package race_condition_problem;

//todo - simulate the race condition problem here
// and the solution with commenting previous state.
public class RaceConditionProblem {
    public static int counter = 0;

    public static void main(String[] args) {
        Thread thread1 = new Thread(()-> {
            for (int i = 0; i < 100000; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + counter++);
            }
        });
        Thread thread2 = new Thread(()-> {
            for (int i = 0; i < 100000; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + counter++);
            }
        });
        thread1.start();
        thread2.start();

        System.out.println(counter);
    }
}
