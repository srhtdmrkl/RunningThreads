import java.util.ArrayList;

public class RunningThreads extends Thread {

    private ArrayList<Integer> numbers;
    private ArrayList<Integer> evenNumbers;
    private ArrayList<Integer> oddNumbers;

    public RunningThreads(ArrayList<Integer> numbers, ArrayList<Integer> evenNumbers, ArrayList<Integer> oddNumbers) {
        this.numbers = numbers;
        this.evenNumbers = evenNumbers;
        this.oddNumbers = oddNumbers;
    }

    @Override
    public void run() {
        for (Integer number : numbers) {
            if(number % 2 == 0) {
                synchronized(evenNumbers) {
                    evenNumbers.add(number);
                }
            } else {
                synchronized(oddNumbers){
                    oddNumbers.add(number);
                }
            }
        }
    }

    
    public static void main(String[] args) throws Exception {
        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        //Add numbers from 1 to 10000 to the ArrayList
        for (int i = 1; i <= 10000; i++) {
            numbers.add(i);
        }

        //Split into 4 equal parts and assign each to different threads
        int chunkSize = numbers.size() / 4;
        RunningThreads[] threads = new RunningThreads[4];

        for (int i = 0; i<4;i++ ){
            int startIndex = i * chunkSize;
            int endIndex = startIndex + chunkSize;
            ArrayList<Integer> chunk = new ArrayList<>(numbers.subList(startIndex, endIndex));
            threads[i] = new RunningThreads(chunk, evenNumbers, oddNumbers);
            threads[i].start();
        }

        // Wait for threads to finish their work
        for (RunningThreads thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Print even numbers
        System.out.print("\nEven numbers: ");
        for (Integer number : evenNumbers) {
            System.out.print(number + ",");
        }

        //Print odd numbers
        System.out.print("\nOdd numbers: ");
        for (Integer number : oddNumbers) {
            System.out.print(number + ",");
        }
    }
}
