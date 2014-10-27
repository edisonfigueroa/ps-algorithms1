
public class Subset {

    public static void main(String[] args) {

        int k = Integer.valueOf(args[0]);

        String[] inputItems = StdIn.readAllStrings();

        Deque<String> dequeue = new Deque<String>();

        for (int i = 1; i <= k; i++) {
            int r = StdRandom.uniform(0, inputItems.length - i + 1);
            // swap element at r with last element
            dequeue.addFirst(inputItems[r]);
            StdOut.println(inputItems[r]);
            inputItems[r] = inputItems[inputItems.length - i];
        }
    }

}
